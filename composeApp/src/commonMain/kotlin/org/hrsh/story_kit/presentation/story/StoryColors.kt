package org.hrsh.story_kit.presentation.story

import androidx.compose.ui.graphics.Color

data class StoryColors(
    val miniature: Color = Color(128, 128, 128),
    val storyStroke: Color = Color(255, 255, 255),
    val storyTopBar: Color = Color(0, 0,0),
    val storyBackground: Color = Color(0, 0, 0),
    val storyBottomBar: Color = Color(0, 0,0),
    val favoritesPreview: Color = Color(211, 211, 211),
    val favoritesDialog: Color = Color(211, 211, 211),
    val upperBlackout: Boolean = true,
    val lowerBlackout: Boolean = true,
    val isLiked: Color = Color.Red,
    val isNotLiked: Color = Color.White,
    val isFavorited: Color = Color.Yellow,
    val isNotFavorited: Color = Color.White,
    val timeline : Color = Color.Gray
)
