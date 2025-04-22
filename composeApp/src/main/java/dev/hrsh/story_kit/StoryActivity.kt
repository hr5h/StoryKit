package dev.hrsh.story_kit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.hrsh.story_kit.presentation.ShowStory

class StoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowStory(
                onClose = {
                    finish()
                    overridePendingTransition(0, 0)
                }
            )
        }
    }
}