package org.hrsh.story_kit.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryManager
import org.hrsh.story_kit.domain.usecases.DeleteStoryUseCase
import org.hrsh.story_kit.domain.usecases.InsertStoryUseCase
import org.hrsh.story_kit.domain.usecases.SubscribeStoryUseCase
import org.hrsh.story_kit.domain.usecases.UpdateStoryUseCase

internal class StoryViewModel(
    private val subscribeStoryUseCase: SubscribeStoryUseCase,
    private val insertStoryUseCase: InsertStoryUseCase,
    private val updateStoryUseCase: UpdateStoryUseCase,
    private val deleteStoryUseCase: DeleteStoryUseCase
) : ViewModel(), StoryManager {

    private val _storyFlowList: MutableStateFlow<List<StoryItem>> = MutableStateFlow(emptyList())
    internal val storyFlowList: StateFlow<List<StoryItem>> = _storyFlowList.asStateFlow()

    private val _storyState: MutableStateFlow<StoryState> = MutableStateFlow(StoryState())
    internal val storyState: StateFlow<StoryState> = _storyState.asStateFlow()

    private val _storyView: MutableMap<Long, MutableStateFlow<Boolean>> = mutableMapOf()
    private val _storyLike: MutableMap<Long, MutableStateFlow<Boolean>> = mutableMapOf()
    private val _storySkip: MutableMap<Long, MutableStateFlow<Boolean>> = mutableMapOf()

    internal val selectStoryItem: StoryItem
        get() = if (!_storyState.value.showFavoriteStories)
            storyFlowList.value[_storyState.value.currentStory]
        else
            favoriteStoriesList[_storyState.value.currentStory]

    internal val favoriteStoriesList: List<StoryItem>
        get() = _storyFlowList.value.filter { story ->
            story.isFavorite
        }


    init {
        subscribeStories()
    }

    //<БД
    private fun subscribeStories() {
        viewModelScope.launch {
            subscribeStoryUseCase().collect { result ->
                _storyFlowList.update { result }
                //println(result.joinToString("\n"))
                initFirstStory()
            }
        }
    }

    override fun addStory(storyItem: StoryItem) {
        viewModelScope.launch {
            insertStoryUseCase(storyItem)
        }
    }

    override fun updateStory(storyItem: StoryItem) {
        viewModelScope.launch {
            updateStoryUseCase(storyItem)
        }
    }

    override fun deleteStory(storyItem: StoryItem) {
        viewModelScope.launch {
            deleteStoryUseCase(storyItem)
        }
    }
    //БД>

    //<subscribeStory
    override fun subscribeStoryView(id: Long): StateFlow<Boolean> {
        return _storyView.getOrPut(id) {
            MutableStateFlow(
                _storyFlowList.value.firstOrNull { it.id == id }?.isViewed ?: false
            )
        }
    }

    override fun subscribeStoryLike(id: Long): StateFlow<Boolean> {
        return _storyLike.getOrPut(id) {
            MutableStateFlow(
                _storyFlowList.value.firstOrNull { it.id == id }?.isLike ?: false
            )
        }
    }

    override fun subscribeStorySkip(id: Long): StateFlow<Boolean> {
        return _storySkip.getOrPut(id) { MutableStateFlow(false) }
    }
    //subscribeStory>

    //<storyEvent
    internal fun storyViewed(storyItem: StoryItem) {
        updateStory(storyItem.copy(isViewed = true))

        _storyView[storyItem.id]?.update { true }
    }

    internal fun storyLiked(storyItem: StoryItem) {
        updateStory(
            storyItem.copy(
                isLike = !storyItem.isLike,
                countLike = if (storyItem.isLike) storyItem.countLike - 1 else storyItem.countLike + 1
            )
        )

        _storyLike[storyItem.id]?.update { !storyItem.isLike }
    }

    internal fun storyFavorited(storyItem: StoryItem) {
        updateStory(
            storyItem.copy(
                isFavorite = !storyItem.isFavorite
            )
        )
    }
    //storyEvent>

    internal fun showStory() {
        _storyState.update { it.copy(isShowStory = true) }
    }

    private fun closeStory() {
        _storyState.update { it.copy(isShowStory = false) }
    }

    internal fun closeAllStory() {
        if (_storyState.value.isShowStory)
            closeStory()
        else if (_storyState.value.hasFirstStory)
            closeFirstStory()
        unSelectStory()
        if(_storyState.value.showFavoriteStories) {
            showFavoriteStories()
            saveCloseFavoriteStories()
        }
    }

    internal fun showFavoriteStories() {
        _storyState.update { it.copy(isShowFavoriteStories = true) }
    }

    internal fun closeFavoriteStories() {
        _storyState.update { it.copy(isShowFavoriteStories = false) }
    }

    internal fun saveShowFavoriteStories() {
        _storyState.update { it.copy(showFavoriteStories = true) }
    }

    internal fun saveCloseFavoriteStories() {
        _storyState.update { it.copy(showFavoriteStories = false) }
    }

    internal fun selectStory(story: StoryItem) {
        if (!_storyState.value.isShowFavoriteStories) {
            _storyState.update {
                it.copy(
                    currentStory = _storyFlowList.value.indexOf(story),
                    currentPage = _storyFlowList.value.map { 0 })
            }
        } else {
            _storyState.update {
                it.copy(
                    currentStory = favoriteStoriesList.indexOf(story),
                    currentPage = favoriteStoriesList.map { 0 })
            }
        }
    }

    internal fun unSelectStory() {
        _storyState.update {
            it.copy(currentStory = -1)
        }
    }

    internal fun prevPage() {
        if (_storyState.value.currentStory == -1) return

        if (_storyState.value.currentPage[_storyState.value.currentStory] > 0) {
            val newList = _storyState.value.currentPage.toMutableList()
            newList[_storyState.value.currentStory] -= 1
            _storyState.update { state ->
                state.copy(currentPage = newList)
            }
        } else if (!_storyState.value.hasFirstStory) {
            prevStory()
        }
    }

    internal fun nextPage() {
        if (_storyState.value.currentStory == -1) return

        if (_storyState.value.currentPage[_storyState.value.currentStory] < _storyFlowList.value[_storyState.value.currentStory].listPages.size - 1) {
            val newList = _storyState.value.currentPage.toMutableList()
            newList[_storyState.value.currentStory] += 1
            _storyState.update { state ->
                state.copy(currentPage = newList)
            }
        } else if (!_storyState.value.hasFirstStory) {
            nextStory()
        }
    }

    private fun prevStory() {
        if (_storyState.value.currentStory == -1) return

        if (_storyState.value.currentStory > 0) {
            _storyState.update { it.copy(currentStory = it.currentStory - 1) }
        }
    }

    private fun nextStory() {
        if (_storyState.value.currentStory == -1) return

        if (_storyState.value.currentStory < _storyFlowList.value.size - 1) {
            _storyState.update { it.copy(currentStory = it.currentStory + 1) }
        } else {
            closeAllStory()
        }
    }

    internal fun setStory(ind: Int) {
        _storyState.update { it.copy(currentStory = ind) }
    }

    private fun initFirstStory() {
        if (_storyState.value.currentStory == -1) {
            _storyState.update {
                it.copy(
                    currentStory = _storyFlowList.value.indexOf(_storyFlowList.value.firstOrNull { first -> first.isStartStory }),
                    currentPage = it.currentPage + 0
                )
            }
            if (_storyState.value.currentStory >= 0)
                _storyState.update { it.copy(hasFirstStory = true) }
        }
    }

    private fun closeFirstStory() {
        _storyState.update { it.copy(hasFirstStory = false) }
    }
}