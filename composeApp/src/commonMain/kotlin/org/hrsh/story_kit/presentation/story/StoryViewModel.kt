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

class StoryViewModel: ViewModel() {

    private val _storyList: MutableStateFlow<List<StoryItem>> = MutableStateFlow(emptyList())
    val storyList: StateFlow<List<StoryItem>> = _storyList.asStateFlow()

    private val _isShowStory: MutableState<Boolean> = mutableStateOf(false)
    val isShowStory: State<Boolean> = _isShowStory

    private val _selectStory: MutableState<StoryItem?> = mutableStateOf(null)
    val selectStory: State<StoryItem?> = _selectStory

    fun getStories(url: String) {
        viewModelScope.launch {
            // TODO
        }
    }

    fun addStory(story: StoryItem) {
        _storyList.update { it + story }
    }

    fun showStory() {
        println(_isShowStory.value)
        _isShowStory.value = !_isShowStory.value
    }

    fun selectStory(story: StoryItem) {
        println(story)
        _selectStory.value = story
    }

    fun unSelectStory() {
        _selectStory.value = null
    }
}