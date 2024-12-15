package org.hrsh.story_kit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlinx.serialization.json.Json
import org.hrsh.story_kit.di.Koin
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Koin.setupKoin {
            androidContext(applicationContext)
        }

        setContent {
            App()
        }
    }
}