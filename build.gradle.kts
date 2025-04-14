plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.ksp) apply false
    kotlin("plugin.serialization") version "1.5.0" apply false
    id("org.jetbrains.dokka") version "2.0.0"
    id("dev.testify") version "3.2.0" apply false
    alias(libs.plugins.kotlinAndroid) apply false
}

