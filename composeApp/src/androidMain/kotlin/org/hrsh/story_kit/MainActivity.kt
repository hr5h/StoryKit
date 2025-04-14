package org.hrsh.story_kit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.hrsh.story_kit.di.Koin
import org.hrsh.story_kit.presentation.story.StoryColors
import org.hrsh.story_kit.presentation.story.StoryKit
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Composable
fun StoryKit(colors: StoryColors = StoryColors()) {
    val context = LocalContext.current
    Koin.setupKoin {
        androidContext(context)
    }
    StoryKit(colors)
}