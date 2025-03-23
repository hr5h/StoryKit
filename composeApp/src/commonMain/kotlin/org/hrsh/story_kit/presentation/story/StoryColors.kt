package org.hrsh.story_kit.presentation.story

import androidx.compose.ui.graphics.Color

/**
 * Data class that defines colors for various components of the story UI.
 *
 * @property miniature The color of the miniature (thumbnail) that contains stories in the story ribbon.
 * @property storyStroke The color of the stroke (border) around a story in the ribbon.
 * @property storyTopBar The color of the top bar of a story.
 * @property storyBackground The background color of the main part of a story.
 * @property storyBottomBar The color of the bottom bar of a story.
 * @property favoritesPreview The color of the element in the story ribbon that contains favorite stories.
 * @property favoritesDialog The color of the dialog window that displays favorite stories.
 * @property upperBlackout Whether to enable darkening (blackout) for the upper part of a story.
 * @property lowerBlackout Whether to enable darkening (blackout) for the lower part of a story.
 * @property isLiked The color to indicate that the "like" button is pressed.
 * @property isNotLiked The color to indicate that the "like" button is not pressed.
 * @property isFavorited The color to indicate that the "favorite" button is pressed.
 * @property isNotFavorited The color to indicate that the "favorite" button is not pressed.
 * @property timeLine The color of the timeline (progress bar) for stories.
 * @property timeLineBackground The background color of the timeline.
 * @property buttonAnswer The color of the button on the questionnaire page.
 * @property colorResult The color of the result output for the questionnaire.
 */
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
    val timeLine : Color = Color.Gray,
    val timeLineBackground: Color = Color.White,
    val buttonAnswer: Color = Color.White,
    val colorResult: Color = Color.Gray,
)
