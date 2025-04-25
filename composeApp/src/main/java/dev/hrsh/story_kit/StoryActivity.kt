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
                }
            )
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.scale_in, R.anim.scale_out)
    }
}