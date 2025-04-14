import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.room)
    //snapshot
    //id("dev.testify")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
    id("maven-publish")
}

val libraryGroupId = "dev.hr5h"
val libraryArtifactId = "story_kit"
val libraryVersion = "0.3"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        withSourcesJar(publish = true)

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            //OkHttp
            implementation(libs.ktor.client.okhttp)
            //ExoPlayer
            implementation(libs.androidx.media3.exoplayer)
            implementation(libs.androidx.media3.exoplayer.dash)
            implementation(libs.androidx.media3.ui)
            //Koin
            implementation(libs.koin.android)

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        iosMain.dependencies {
            //Darwin
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            //Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.svg)
            //Ktor//Ktor+OkHttp/Darwin
            implementation(libs.coil.network.ktor3)
            implementation(libs.ktor.client.core)
            //Room+SQLite
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.sqlite)
            //Koin
            implementation(libs.koin.core)
            //Kotlin Serialization
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test-annotations-common"))
            implementation(libs.assertk)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
    }
}

android {
    namespace = "dev.hrsh.story_kit"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //snapshot
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4-android:1.6.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")

    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.androidx.ui.test.android)
    androidTestImplementation(libs.mockito.android)
    implementation(libs.mockito.core)
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    debugImplementation(compose.uiTooling)
    val composeBom = platform("androidx.compose:compose-bom:2023.10.00")
    implementation(composeBom)
    // Android Studio Preview support
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    //KSP
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

publishing {
    publications {
        withType<MavenPublication> {
            groupId = libraryGroupId
            artifactId = libraryArtifactId
            version = libraryVersion

            when (name) {
                "kotlinMultiplatform" -> artifactId = "$libraryArtifactId-multiplatform"
                "androidRelease" -> artifactId = "$libraryArtifactId-android"
                "ios" -> artifactId = "$libraryArtifactId-ios"
            }
        }
    }

    repositories {
        mavenLocal()

        maven {
            name = "BuildDir"
            url = uri(rootProject.layout.buildDirectory.dir("maven-repo"))
        }
    }
}