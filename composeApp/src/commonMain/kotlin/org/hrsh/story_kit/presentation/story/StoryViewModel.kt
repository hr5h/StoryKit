package org.hrsh.story_kit.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val storyFlowList: StateFlow<List<StoryItem>> = _storyFlowList.asStateFlow()

    private val _storyState: MutableStateFlow<StoryState> = MutableStateFlow(StoryState())
    val storyState: StateFlow<StoryState> = _storyState.asStateFlow()

    private val _storyView: MutableSharedFlow<Long> = MutableSharedFlow()
    private val _storyLike: MutableSharedFlow<Pair<Long, Boolean>> = MutableSharedFlow()
    private val _storyFavorite: MutableSharedFlow<Pair<Long, Boolean>> = MutableSharedFlow()
    private val _storySkip: MutableSharedFlow<Triple<Long, Int, Float>> = MutableSharedFlow()
    private val _storyAnswerChose: MutableSharedFlow<Triple<Long, Int, Int>> = MutableSharedFlow()
    private val _isPauseStory: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _favoriteStoriesList: MutableStateFlow<List<StoryItem>> =
        MutableStateFlow(emptyList())
    val favoriteStoriesList: StateFlow<List<StoryItem>> =
        _favoriteStoriesList.asStateFlow()

    val selectStoryItem: StoryItem
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
                _storyFlowList.update {
                    result.filter { story ->
                        story.isShowInMiniature
                    }
                }
                if (!_storyState.value.showFavoriteStories) {
                    _favoriteStoriesList.update {
                        result.filter { story ->
                            story.isFavorite
                        }
                    }.run {
                        if (_storyState.value.showFavoriteStories && _favoriteStoriesList.value.isEmpty()) {
                            unSelectStory()
                            closeFavoriteStories()
                            saveCloseFavoriteStories()
                        }
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
                if (!storyItem.isFavorite) {
                    deleteStoryUseCase(storyItem)
                } else {
                    updateStory(storyItem.copy(isShowInMiniature = false))
                }
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

    override fun subscribeStoryFavorite(): Flow<Pair<Long, Boolean>> {
        return _storyFavorite.asSharedFlow()
    }

    override fun subscribeStorySkip(): Flow<Triple<Long, Int, Float>> {
        return _storySkip.asSharedFlow()
    }

    override fun subscribeStoryQuestion(): Flow<Triple<Long, Int, Int>> {
        return _storyAnswerChose.asSharedFlow()
    }

    override fun subscribeStoryPause(): Flow<Boolean> {
        return _isPauseStory.asStateFlow()
    }
    //subscribeStory>

    //<storyEvent
    fun storyViewed(storyItem: StoryItem) {
        updateStory(storyItem.copy(isViewed = true))

        viewModelScope.launch {
            _storyView.emit(storyItem.id)
        }
    }

    fun storyLiked(storyItem: StoryItem) {
        if (!_storyState.value.showFavoriteStories) {
            updateStory(
                storyItem.copy(
                    isLike = !storyItem.isLike,
                    countLike = if (storyItem.isLike) storyItem.countLike - 1 else storyItem.countLike + 1
                )
            )
        } else {
            _favoriteStoriesList.update {
                val index = _favoriteStoriesList.value.indexOf(storyItem)
                val newList = _favoriteStoriesList.value.toMutableList()

                if (index != -1) {
                    newList[index] = storyItem.copy(
                        isLike = !storyItem.isLike,
                        countLike = if (storyItem.isLike) storyItem.countLike - 1 else storyItem.countLike + 1
                    )
                }

                newList
            }
        }


        viewModelScope.launch {
            _storyLike.emit(Pair(storyItem.id, !storyItem.isLike))
        }
    }

    fun storyFavorited(storyItem: StoryItem) {
        if (!_storyState.value.showFavoriteStories) {
            updateStory(
                storyItem.copy(
                    isFavorite = !storyItem.isFavorite
                )
            )
        } else {
            _favoriteStoriesList.update {
                val index = _favoriteStoriesList.value.indexOf(storyItem)
                val newList = _favoriteStoriesList.value.toMutableList()

                if (index != -1) {
                    newList[index] = storyItem.copy(
                        isFavorite = !storyItem.isFavorite
                    )
                }

                newList
            }
        }

        viewModelScope.launch {
            _storyFavorite.emit(Pair(storyItem.id, !storyItem.isFavorite))
        }
    }

    fun updateFavoriteStories() {
        _favoriteStoriesList.value.forEach { item ->
            updateStory(item)
            if (!item.isShowInMiniature) {
                deleteStory(item.id)
            }
        }
    }

    fun updateSelected(storyItem: StoryItem, pageItem: PageItem, value: Int) {
        var pageIndex: Int = -1
        val modifiedPagesList = storyItem.listPages.mapIndexed { index, item ->
            if (item is PageItem.Question && item.question == (pageItem as PageItem.Question).question) {
                pageIndex = index
                item.copy(indexSelected = if (item.indexSelected == value) -1 else value)
            } else {
                item
            }
        }

        if (!_storyState.value.showFavoriteStories) {
            updateStory(
                storyItem.copy(listPages = modifiedPagesList)
            )
        } else {
            _favoriteStoriesList.update {
                val index = _favoriteStoriesList.value.indexOf(storyItem)
                val newList = _favoriteStoriesList.value.toMutableList()

                if (index != -1) {
                    newList[index] = storyItem.copy(listPages = modifiedPagesList)
                }

                newList
            }
        }

        viewModelScope.launch {
            _storyAnswerChose.emit(Triple(storyItem.id, pageIndex, value))
        }
    }

    fun pauseStory(flag: Boolean) {
        _isPauseStory.update { flag }
    }

    fun storySkip(storyId: Long, pageId: Int, time: Float) {
        viewModelScope.launch {
            _storySkip.emit(Triple(storyId, pageId, time))
        }
    }
    //storyEvent>

    fun showStory() {
        _storyState.update { it.copy(isShowStory = true) }
    }

    private fun closeStory() {
        _storyState.update { it.copy(isShowStory = false) }
    }

    fun closeAllStory() {
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

    fun showFavoriteStories() {
        _storyState.update { it.copy(isShowFavoriteStories = true) }
    }

    fun closeFavoriteStories() {
        _storyState.update { it.copy(isShowFavoriteStories = false) }
    }

    fun saveShowFavoriteStories() {
        _storyState.update { it.copy(showFavoriteStories = true) }
    }

    fun saveCloseFavoriteStories() {
        _storyState.update { it.copy(showFavoriteStories = false) }
    }

    fun selectStory(story: StoryItem) {
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

    fun setStory(ind: Int) {
        _storyState.update { it.copy(currentStory = ind) }
    }

    fun prevPage() {
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

    fun nextPage() {
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

    override fun setColors(storyColors: StoryColors) {
        _storyState.update {
            it.copy(storyColors = storyColors)
        }
    }
}