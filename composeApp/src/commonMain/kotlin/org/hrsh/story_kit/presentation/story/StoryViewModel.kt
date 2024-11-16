package org.hrsh.story_kit.presentation.story

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StoryViewModel : ViewModel() {

    private val _storyList: MutableStateFlow<List<StoryItem>> = MutableStateFlow(emptyList())
    val storyList: StateFlow<List<StoryItem>> = _storyList.asStateFlow()

    private val _isShowStory: MutableState<Boolean> = mutableStateOf(false)
    val isShowStory: State<Boolean> = _isShowStory

    private val _storyState: MutableStateFlow<StoryState> =
        MutableStateFlow(StoryState())
    val storyState: StateFlow<StoryState> = _storyState

    private val _firstStoryState: MutableStateFlow<StoryState> =
        MutableStateFlow(StoryState())
    val firstStoryState: StateFlow<StoryState> = _firstStoryState

    fun getStories(url: String) {
        viewModelScope.launch {
            // TODO
            initFirstStory()
        }
    }

    fun addStory(story: StoryItem) {
        _storyList.update { it + story }
        initFirstStory()
    }

    fun showStory() {
        _isShowStory.value = !_isShowStory.value
    }

    fun selectStory(story: StoryItem) {
        _storyState.update {
            it.copy(currentStory = _storyList.value.indexOf(story), currentPage = _storyList.value.map { 0 })
        }
    }

    fun unSelectStory() {
        _storyState.update {
            it.copy(currentStory = null)
        }
    }

    fun prevPage() {
        if (_storyState.value.currentStory == null) return

        if (_storyState.value.currentPage[_storyState.value.currentStory!!] > 0) {
            val newList = _storyState.value.currentPage.toMutableList()
            newList[_storyState.value.currentStory!!] -= 1
            _storyState.update { state ->
                state.copy(currentPage = newList)
            }
        } else {
            prevStory()
        }
    }

    fun prevStory() {
        if (_storyState.value.currentStory == null) return

        if (_storyState.value.currentStory!! > 0) {
            _storyState.update { it.copy(currentStory = it.currentStory!! - 1) }
        }
    }

    fun nextPage() {
        if (_storyState.value.currentStory == null) return

        if (_storyState.value.currentPage[_storyState.value.currentStory!!] < _storyList.value[_storyState.value.currentStory!!].listPages.size - 1) {
            val newList = _storyState.value.currentPage.toMutableList()
            newList[_storyState.value.currentStory!!] += 1
            _storyState.update { state ->
                state.copy(currentPage = newList)
            }
        } else {
            nextStory()
        }
    }

    fun nextStory() {
        if (_storyState.value.currentStory == null) return

        if (_storyState.value.currentStory!! < _storyList.value.size - 1) {
            _storyState.update { it.copy(currentStory = it.currentStory!! + 1) }
        }
    }

    private fun initFirstStory() {
        if(_firstStoryState.value.currentStory == null || _firstStoryState.value.currentStory == -1)
            _firstStoryState.update { it.copy(currentStory = _storyList.value.indexOf(_storyList.value.firstOrNull { first -> first.showOnStart }), currentPage = it.currentPage + 0) }
    }

    fun prevPageFirstStory() {
        if (_firstStoryState.value.currentStory == null) return

        if (_firstStoryState.value.currentPage[_firstStoryState.value.currentStory!!] > 0) {
            val newList = _firstStoryState.value.currentPage.toMutableList()
            newList[_firstStoryState.value.currentStory!!] -= 1
            _firstStoryState.update { state ->
                state.copy(currentPage = newList)
            }
        }
    }

    fun nextPageFirstStory() {
        if (_firstStoryState.value.currentStory == null) return

        if (_firstStoryState.value.currentPage[_firstStoryState.value.currentStory!!] < _storyList.value[_firstStoryState.value.currentStory!!].listPages.size - 1) {
            val newList = _firstStoryState.value.currentPage.toMutableList()
            newList[_firstStoryState.value.currentStory!!] += 1
            _firstStoryState.update { state ->
                state.copy(currentPage = newList)
            }
        }
    }

    fun firstStoryClose() {
        _firstStoryState.update { it.copy(currentStory = null) }
    }
}