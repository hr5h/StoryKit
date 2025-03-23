package org.hrsh.story_kit.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryManager
import org.hrsh.story_kit.domain.usecases.DeleteAllStoryUseCase
import org.hrsh.story_kit.domain.usecases.DeleteStoryUseCase
import org.hrsh.story_kit.domain.usecases.InsertStoryUseCase
import org.hrsh.story_kit.domain.usecases.SubscribeStoryUseCase
import org.hrsh.story_kit.domain.usecases.UpdateStoryUseCase

internal class StoryViewModel(
    private val subscribeStoryUseCase: SubscribeStoryUseCase,
    private val insertStoryUseCase: InsertStoryUseCase,
    private val updateStoryUseCase: UpdateStoryUseCase,
    private val deleteStoryUseCase: DeleteStoryUseCase,
    private val deleteAllStoryUseCase: DeleteAllStoryUseCase
) : ViewModel(), StoryManager {

    private val _storyFlowList: MutableStateFlow<List<StoryItem>> = MutableStateFlow(emptyList())
    internal val storyFlowList: StateFlow<List<StoryItem>> = _storyFlowList.asStateFlow()

    private val _storyState: MutableStateFlow<StoryState> = MutableStateFlow(StoryState())
    internal val storyState: StateFlow<StoryState> = _storyState.asStateFlow()

    private val _storyView: MutableSharedFlow<Long> = MutableSharedFlow()
    private val _storyLike: MutableSharedFlow<Pair<Long, Boolean>> = MutableSharedFlow()
    private val _storySkip: MutableSharedFlow<Pair<Long, Boolean>> = MutableSharedFlow()
    private val _storyAnswerChose: MutableSharedFlow<Triple<Long, Int, Int>> = MutableSharedFlow()

    private val _favoriteStoriesList: MutableStateFlow<List<StoryItem>> =
        MutableStateFlow(emptyList())
    internal val favoriteStoriesList: StateFlow<List<StoryItem>> =
        _favoriteStoriesList.asStateFlow()

    internal val selectStoryItem: StoryItem
        get() = if (!_storyState.value.showFavoriteStories)
            storyFlowList.value[_storyState.value.currentStory]
        else
            favoriteStoriesList.value[_storyState.value.currentStory]

    init {
        subscribeStories()
    }

    //<БД
    private fun subscribeStories() {
        viewModelScope.launch {
            subscribeStoryUseCase().collect { result ->
                _storyFlowList.update { result }
                _favoriteStoriesList.update {
                    _storyFlowList.value.filter { story ->
                        story.isFavorite
                    }
                }
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

    override fun deleteStory(id: Long) {
        viewModelScope.launch {
            val storyItem = _storyFlowList.value.firstOrNull { it.id == id }
            if (storyItem != null) {
                deleteStoryUseCase(storyItem)
            }
        }
    }

    override fun deleteAllStory() {
        viewModelScope.launch {
            deleteAllStoryUseCase.invoke()
        }
    }
    //БД>

    //<subscribeStory
    override fun subscribeStoryView(): Flow<Long> {
        return _storyView.asSharedFlow()
    }

    override fun subscribeStoryLike(): Flow<Pair<Long, Boolean>> {
        return _storyLike.asSharedFlow()
    }

    override fun subscribeStorySkip(): Flow<Pair<Long, Boolean>> {
        return _storySkip.asSharedFlow()
    }

    override fun subscribeStoryQuestion(): Flow<Triple<Long, Int, Int>> {
        return _storyAnswerChose.asSharedFlow()
    }
    //subscribeStory>

    //<storyEvent
    internal fun storyViewed(storyItem: StoryItem) {
        updateStory(storyItem.copy(isViewed = true))

        viewModelScope.launch {
            _storyView.emit(storyItem.id)
        }
    }

    internal fun storyLiked(storyItem: StoryItem) {
        updateStory(
            storyItem.copy(
                isLike = !storyItem.isLike,
                countLike = if (storyItem.isLike) storyItem.countLike - 1 else storyItem.countLike + 1
            )
        )

        viewModelScope.launch {
            _storyLike.emit(Pair(storyItem.id, !storyItem.isLike))
        }
    }

    internal fun storyFavorited(storyItem: StoryItem) {
        updateStory(
            storyItem.copy(
                isFavorite = !storyItem.isFavorite
            )
        )
    }

    internal fun updateSelected(storyItem: StoryItem, pageItem: PageItem, value: Int) {
        var pageIndex: Int = -1
        val modifiedPagesList = storyItem.listPages.mapIndexed { index, item ->
            if (item is PageItem.Question && item.question == (pageItem as PageItem.Question).question) {
                pageIndex = index
                item.copy(indexSelected = if (item.indexSelected == value) -1 else value)
            } else {
                item
            }
        }
        updateStory(
            storyItem.copy(listPages = modifiedPagesList)
        )

        viewModelScope.launch {
            _storyAnswerChose.emit(Triple(storyItem.id, pageIndex, value))
        }
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
        if (_storyState.value.showFavoriteStories) {
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
                    currentStory = favoriteStoriesList.value.indexOf(story),
                    currentPage = favoriteStoriesList.value.map { 0 })
            }
        }
    }

    internal fun setStory(ind: Int) {
        _storyState.update { it.copy(currentStory = ind) }
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

    private fun unSelectStory() {
        _storyState.update {
            it.copy(currentStory = -1)
        }
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