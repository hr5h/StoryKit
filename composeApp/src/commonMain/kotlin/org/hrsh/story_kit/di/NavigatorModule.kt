package org.hrsh.story_kit.di

import androidx.compose.runtime.Composable

expect class Navigator() {
    @Composable
    fun navigateToDetail()
}