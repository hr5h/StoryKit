# StoryKit - KMP implementation of stories

This project is an implementation of the functionality of adding and deleting stories(similar to stories in well-known social networks)

## Instalation
Since JCenter is closing, please use https://jitpack.io/ and release tags as the source of stable artifacts.

```bash
implementation 'com.github.hr5h:StoryKit:1.0.0'
```

## Usage

First, you need to add _storyManager_, that defines the operations for managing story items.

```bash
val storyManager = StoryKit.storyManager
MaterialTheme {
    val storyColors = StoryColors(
        miniature = Color(red = 11, green = 172, blue = 65),
        storyStroke = Color.Green,
        favoritesPreview = Color(144, 238, 144),
        favoritesDialog = Color(144, 238, 144),
        isLiked = Color(red = 11, green = 172, blue = 65),
        isFavorited = Color(red = 11, green = 172, blue = 65),
        timeline = Color(red = 11, green = 172, blue = 65)
    )
StoryKit(storyColors)
```
There are 3 types of stories, that you can use:
```
PageItem.Image(
    imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
    text = "Page 1"
)
PageItem.Question(
    imageUrl = "https://avatars.yandex.net/get-music-content/5234847/767e884c.a.16290016-1/m1000x1000?webp=false",
    question = "How do you rate our stories?",
    listAnswers = listOf("1", "2", "3", "4", "5+")
)
PageItem.Video(
    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    timeShow = 30f
)
```
And this is how you can add some stories:
```
storyManager.addStory(
    StoryItem(
        id = 100,
        imagePreview = "https://i01.fotocdn.net/s215/23442118aa73147b/public_pin_l/2920842511.jpg",
        listPages = listOf(
            PageItem.Image(
                imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                text = "Page 1"
            ),
            PageItem.Image(
                imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                text = "Page 2"
            ),
            PageItem.Image(
                imageUrl = "https://avatars.mds.yandex.net/i?id=325bcdf905e6685f354011427095fa3f_l-5233671-images-thumbs&n=13",
                text = "Page 3"
            ),
            PageItem.Question(
                imageUrl = "https://avatars.yandex.net/get-music-content/5234847/767e884c.a.16290016-1/m1000x1000?webp=false",
                question = "How do you rate our stories?",
                listAnswers = listOf("1", "2", "3", "4", "5+")
            )
        ),
    )
)
storyManager.addStory(
    StoryItem(
        id = 301,
        imagePreview = "https://vels76.ru/sites/default/files/znachok-videozapisi.jpg",
        listPages = listOf(
            PageItem.Video(
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                timeShow = 30f
            )
        ),
    )
)
```
Also you can update and delete stories:
```
fun updateStory(storyItem: StoryItem)
fun deleteStory(id: Long)
fun deleteAllStory()

CoroutineScope(Dispatchers.IO).launch {
    delay(2000)
    launch {
        storyManager.deleteAllStory()
    }
}

```
Finally, you can check the number of likes and views of stories:
```
CoroutineScope(Dispatchers.IO).launch {
    delay(2000)
    launch {
        storyManager.subscribeStoryLike().collect { (id, isLike) ->
            println("Story with id = $id, isLike: $isLike")
        }
    }
}

CoroutineScope(Dispatchers.IO).launch {
    delay(2000)
    launch {
        storyManager.subscribeStoryView().collect { id ->
            println("Story with id = $id has been viewed")
        }
    }
}

```


## Contributing

Please feel free to submit issues, fork the repository and send pull requests!

## License
This project is licensed under the terms of the MIT license.
