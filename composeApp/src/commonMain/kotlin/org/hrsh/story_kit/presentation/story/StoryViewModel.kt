package org.hrsh.story_kit.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hrsh.story_kit.data.entities.StoryItemDb
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.usecases.DeleteStoryUseCase
import org.hrsh.story_kit.domain.usecases.InsertStoryUseCase
import org.hrsh.story_kit.domain.usecases.SubscribeStoryUseCase

class StoryViewModel(
    private val subscribeStoryUseCase: SubscribeStoryUseCase,
    private val insertStoryUseCase: InsertStoryUseCase,
    private val deleteStoryUseCase: DeleteStoryUseCase
) : ViewModel() {

    private val _storyList: MutableStateFlow<List<StoryItem>> = MutableStateFlow(emptyList())
    val storyList: StateFlow<List<StoryItem>> = _storyList.asStateFlow()

    private val _storyState: MutableStateFlow<StoryState> = MutableStateFlow(StoryState())
    val storyState: StateFlow<StoryState> = _storyState

    init {
        getStories()
    }

    private fun getStories() {
        viewModelScope.launch {
            println(subscribeStoryUseCase())
            initFirstStory()
        }
    }

    fun addStory(story: StoryItem) {
        _storyList.update { it + story }
        viewModelScope.launch {
            insertStoryUseCase(story)
        }
        initFirstStory()
    }

    fun showStory() {
        _storyState.update { it.copy(isShowStory = true) }
    }

    fun closeStory() {
        _storyState.update { it.copy(isShowStory = false) }
    }

    fun selectStory(story: StoryItem) {
        _storyState.update {
            it.copy(
                currentStory = _storyList.value.indexOf(story),
                currentPage = _storyList.value.map { 0 })
        }
    }

    fun unSelectStory() {
        _storyState.update {
            it.copy(currentStory = -1)
        }
    }

    fun prevPage() {
        if(_storyState.value.currentStory == -1) return

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
        if(_storyState.value.currentStory == -1) return

        if (_storyState.value.currentPage[_storyState.value.currentStory] < _storyList.value[_storyState.value.currentStory].listPages.size - 1) {
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
        if(_storyState.value.currentStory == -1) return

        if (_storyState.value.currentStory > 0) {
            _storyState.update { it.copy(currentStory = it.currentStory - 1) }
        }
    }

    private fun nextStory() {
        if(_storyState.value.currentStory == -1) return

        if (_storyState.value.currentStory < _storyList.value.size - 1) {
            _storyState.update { it.copy(currentStory = it.currentStory + 1) }
        }
    }

    fun setStory(ind: Int) {
        _storyState.update { it.copy(currentStory = ind) }
    }

    private fun initFirstStory() {
        if (_storyState.value.currentStory == -1) {
            _storyState.update {
                it.copy(
                    currentStory = _storyList.value.indexOf(_storyList.value.firstOrNull { first -> first.isStartStory }),
                    currentPage = it.currentPage + 0
                )
            }
            if (_storyState.value.currentStory >= 0)
                _storyState.update { it.copy(hasFirstStory = true) }
        }
    }

    fun closeFirstStory() {
        _storyState.update { it.copy(hasFirstStory = false) }
    }
}