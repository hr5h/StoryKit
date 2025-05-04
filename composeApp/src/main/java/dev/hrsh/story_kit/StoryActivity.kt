package dev.hrsh.story_kit

import android.graphics.Color
import android.os.Build
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

    override fun onStart() {
        super.onStart()

        navigationBarColor()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.scale_in, R.anim.scale_out)
    }

    private fun navigationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { _ ->
                window.navigationBarColor = Color.TRANSPARENT
                window.isNavigationBarContrastEnforced = false
            }
        }
    }
}