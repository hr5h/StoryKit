package org.hrsh.story_kit.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoryViewModel: ViewModel() {

    private val _storyList: MutableStateFlow<List<StoryItem>> = MutableStateFlow(emptyList())
    val storyList: StateFlow<List<StoryItem>> = _storyList.asStateFlow()

    fun getStories(url: String) {
        viewModelScope.launch {
            // TODO
        }
    }

}