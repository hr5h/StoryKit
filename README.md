# StoryKit - KMP implementation of stories
This project is an implementation of the functionality of adding and deleting stories(similar to stories in well-known social networks)

## Instalation
Since JCenter is closing, please use https://jitpack.io/ and release tags as the source of stable artifacts

```
implementation 'com.github.hr5h:StoryKit:1.0.0'
```

## Initializing StoryManager
### storyKit(): StoryManager
Initializes and returns the `StoryManager` object for working with stories
```
val storyManager = storyKit()
```
## Types of stories and pages
Story Manager supports various types of stories consisting of pages `PageItem` of different formats. Each `StoryItem` can contain a combination of pages with images, videos, questions, and other interactive elements
## StoryItem Structure
The main class representing the story:
* `id` A unique identifier for the story. It is used to access, add, delete, or modify the story in the database. This key is also utilized for receiving events related to this ID
* `imagePreview` A string that holds the URL or path to the image that is displayed in the stories feed
* `listPages` A list of `PageItem` that will be shown when the story is opened
* `isStartStory` A boolean flag indicating whether the story should be opened upon app launch
* `isLike` A boolean variable indicating whether the story has been liked
* `countLike` An integer representing the number of likes the story has received
* `isFavorite` A boolean variable indicating whether the story has been added to favorites
* `isViewed` A boolean variable indicating whether the story has been viewed
```
data class StoryItem(
    val id: Long,
    val imagePreview: String,
    val listPages: List<PageItem>,
    val toDate: String = "2035-01-01 00:00",
    val isStartStory: Boolean = false,
    val isLike: Boolean = false,
    val countLike: Int = 0,
    val isShowInMiniature: Boolean = true,
    val isFavorite: Boolean = false,
    val isViewed: Boolean = false
)
```
## Types of pages
### 1. Image (PageItem.Image)
Represents an image page item
* `imageUrl` A string representing the URL of the image that will be displayed as the main content
* `text` A string that contains text to be shown at the bottom of the image
* `timeShow` A float representing the duration (in seconds) for which the story will be displayed
```
PageItem.Image(
    imageUrl = "https://example.com/image.jpg",
    text = "Text",
    timeShow = 5f
)
```
### 2. Video (PageItem.Video)
Represents a video page item
* `videoUrl` A string representing the URL of the video that will be displayed
* `timeShow` A float representing the duration (in seconds) for which the video will be displayed
```
PageItem.Video(
    videoUrl = "https://example.com/video.mp4",
    timeShow = 30f
)
```
### 3. Survey (PageItem.Question)
Represents a question page item
* `imageUrl` A string representing the URL of the image associated with the question
* `question` A string containing the question that will be displayed
* `listAnswers` A list of strings representing the possible answers that can be selected
* `timeShow` A float representing the duration (in seconds) for which the question will be displayed
```
PageItem.Question(
    imageUrl = "https://example.com/poll.jpg",
    question = "How do you like our app?",
    listAnswers  = listOf("Excellent", "Good", "Bad"),
    listResults = listOf(70, 20, 10)
)
```
## Methods for working with stories
### addStory(storyItem: StoryItem, callback: (Result<Unit>) -> Unit = {})
Adds a new story item
* `storyItem` The story item to be added
* `callback` Callback that receives the result of the operation. The callback provides a `Result` object containing:
    - `Result.success` with `Unit` if the operation completed successfully
    - `Result.failure` with an `exception` if the operation failed
```
storyManager.addStory(newStory) { result ->
    result.onSuccess { 
        println("Story added successfully")
    }.onFailure { e ->
        println("Error adding story: ${e.message}")
    }
}
```
### updateStory(storyItem: StoryItem)
Updates an existing story
* `storyItem` The story item to be updated
```
storyManager.updateStory(updatedStory)
```
### deleteStory(id: Long)
Deletes the history by id
* `id` The unique identifier of the story item to be deleted
```
storyManager.deleteStory(101)
```
### deleteAllStory()
Deletes all the stories
```
storyManager.deleteAllStory()
```
## Event subscription methods
### subscribeStoryView(): Flow<Long>
Subscribes to history viewing events
* return A `Flow`, which outputs a long history ID when viewing the history
```
storyManager.subscribeStoryView().collect { storyId ->
        println("Story viewed: $storyId")
    }
```
### subscribeStoryLike(): Flow<Pair<Long, Boolean>>
Subscribes to changes in the likes of stories
* return A `Flow` that emits a Pair containing the story's ID and a Boolean indicating whether the story is liked
```
storyManager.subscribeStoryLike().collect { (id, isLiked) ->
    println("Story $id like status: $isLiked")
}
```
### subscribeStoryFavorite(): Flow<Pair<Long, Boolean>>
Subscribes to changes in the selected status of stories
* return A `Flow` that emits a Pair containing the story's ID and a Boolean indicating whether the story is favorite
```
storyManager.subscribeStoryFavorite().collect { (id, isFavorite) ->
    println("Story $id favorite status: $isFavorite")
}
```
### subscribeStorySkip(): Flow<Triple<Long, Int, Float>>
Subscribes to story page skipping events
* return A `Flow` that emits a `Triple` containing:
    - The id of the story that was skipped (`Long`)
    - The page index that was skipped (`Int`)
    - The playback progress at the moment of skipping (`Float` between 0f and 1f)
```
storyManager.subscribeStorySkip()
        .filter { (_, _, progress) -> progress < 0.3f }
        .collect { (id, pageIndex, progress) ->
            println("Skipped: story $id")
        }
```
### subscribeStoryQuestion(): Flow<Triple<Long, Int, Int>>
Subscribes to responses in story surveys
* return A `Flow` that emits a `Triple` containing:
    - The id of the story
    - The page number where the questionnaire is located
    - The index of the selected answer
```
storyManager.subscribeStoryQuestion().collect { (id, page, answer) ->
    println("Story $id page $page answer: $answer")
}
```
### subscribeStoryPause(): Flow<Boolean>
Subscribes to playback pause events
* return A `Flow` that emits a Boolean value indicating whether the story is currently paused:
    - `true` if story playback is paused
    - `false` if story playback is active/resumed
```
storyManager.subscribeStoryPause().collect { isPaused ->
    println("Playback ${if (isPaused) "paused" else "resumed"}")
}
```
## Common methods
### setColors(storyColors: StoryColors)
Sets the color scheme for stories
* `storyColors` The [StoryColors] object containing color values to be applied
```
storyManager.setColors(
        StoryColors(
            miniature = Color(red = 11, green = 172, blue = 65),
            storyStroke = Color.Green,
            favoritesPreview = Color(144, 238, 144),
            favoritesDialog = Color(144, 238, 144),
            isLiked = Color(red = 11, green = 172, blue = 65),
            isFavorite = Color(red = 11, green = 172, blue = 65),
            timeLine = Color(red = 11, green = 172, blue = 65),
            colorResult = Color.Green,
        )
    )
```

## Contributing
Please feel free to submit issues, fork the repository and send pull requests!