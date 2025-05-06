package com.example.androidapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidapp.ui.theme.StoryKitTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.domain.interfaces.StoryManager
import org.hrsh.story_kit.presentation.story.StoryColors
import org.hrsh.story_kit.storyKit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoryKitTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        val logItems = remember { mutableStateListOf<String>() }
                        val trigger = remember { mutableIntStateOf(1) }
                        val listState = rememberLazyListState()
                        val coroutineScope = rememberCoroutineScope()

                        LaunchedEffect(logItems.size) {
                            if (logItems.isNotEmpty()) {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(logItems.size - 1)
                                }
                            }
                        }
                        val storyManager = storyKit()
                        StoryMiniature(logItems, trigger, storyManager)
                        Column(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                itemsIndexed(logItems) { index, item ->
                                    Text(
                                        text = item,
                                        modifier = Modifier.padding(16.dp),
                                        style = MaterialTheme.typography.bodyLarge
                                    )

                                    if (index < logItems.lastIndex) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            thickness = 1.dp,
                                            color = MaterialTheme.colorScheme.outlineVariant
                                        )
                                    }
                                }
                            }
                            Button(
                                onClick = { storyManager.deleteStory(100) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(30.dp),
                                colors = ButtonDefaults.buttonColors(Color.White)
                            ) {
                                Text(text = "Delete story", color = Color.Black)
                            }
                            Row(verticalAlignment = Alignment.Bottom) {
                                Button(
                                    onClick = { logItems.clear() },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(30.dp),
                                    colors = ButtonDefaults.buttonColors(Color.White)
                                ) {
                                    Text(text = "Clear log", color = Color.Black)
                                }
                                Button(
                                    onClick = { trigger.intValue *= -1; },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(30.dp),
                                    colors = ButtonDefaults.buttonColors(Color.White)
                                ) {
                                    Text(text = "Refresh stories", color = Color.Black)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun StoryMiniature(
    logItems: SnapshotStateList<String> = emptyList<String>() as SnapshotStateList<String>,
    trigger: MutableIntState = mutableIntStateOf(0),
    storyManager: StoryManager
) {
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
    LaunchedEffect(trigger.intValue) {
        logItems.add("Refreshing stories")
        storyManager.deleteAllStory()
        storyManager.addStory(
            //PageItemImage
            StoryItem(
                id = 100,
                imagePreview = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxS2MwXBAU7cRyllmo1bjMcQlOwkdjWDVSfQ&s",
                listPages = listOf(
                    PageItem.Image(
                        imageUrl = "https://avatars.mds.yandex.net/get-altay/3569559/2a00000181ec245ac9c1e4872b9c0299be35/orig",
                        //text = "«Премиальный» — вклад для клиентов, которые ценят неограниченные возможности"
                        text = "*Фактическая доходность — ставка с учетом капитализации (в процентах годовых), рассчитывается от начала срока действия договора вклада до конца каждого периода, указывается справочно. *Фактическая доходность — ставка с учетом капитализации (в процентах годовых), рассчитывается от начала срока действия договора вклада до конца каждого периода, указывается справочно. *Фактическая доходность — ставка с учетом капитализации (в процентах годовых), рассчитывается от начала срока действия договора вклада до конца каждого периода, указывается справочно."
                    ),
                    PageItem.Image(
                        imageUrl = "https://cbkg.ru/uploads/centr-invest-.jpg",
                        text = "Неограниченное пополнение в 1-й месяц"
                    ),
                    PageItem.Image(
                        imageUrl = "https://cs15.pikabu.ru/post_img/big/2024/06/19/9/1718811154170793954.jpg",
                        text = "Неограниченные сроком расходные операции без потери процентов до неснижаемого остаткаНеограниченные сроком расходные операции без потери процентов до неснижаемого остаткаНеограниченные сроком расходные операции без потери процентов до неснижаемого остатка"
                    ),
                    PageItem.Question(
                        imageUrl = "https://avatars.yandex.net/get-music-content/5234847/767e884c.a.16290016-1/m1000x1000?webp=false",
                        question = "Как вы оцениваете наши истории?",
                        listAnswers = listOf("1", "2", "3", "4", "5+"),
                        listResults = listOf(50, 30, 20, 10, 60),
                    )
                ),
            )
        )
        storyManager.addStory(
            StoryItem(
                id = 200,
                imagePreview = "https://img.freepik.com/free-vector/financial-stock-market-statics-graph-with-upward-growth_1017-53624.jpg?semt=ais_hybrid&w=740",
                listPages = listOf(
                    PageItem.Image(
                        imageUrl = "https://avatars.mds.yandex.net/get-altay/10385418/2a00000191bd422b85d45382e7e00f5b734d/XL",
                        text = "Ваши денежные средства под надежной защитой"
                    ),
                    PageItem.Image(
                        imageUrl = "https://avatars.mds.yandex.net/get-altay/9724410/2a00000189e716723d04ad6df615704bd295/L_height",
                        text = "Крупнейший частный банк на Юге России"
                    ),
                    PageItem.Question(
                        imageUrl = "https://avatars.yandex.net/get-music-content/5234847/767e884c.a.16290016-1/m1000x1000?webp=false",
                        question = "Как вы оцениваете наши истории2?",
                        listAnswers = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"),
                    )
                ),
            )
        )
        //PageItemVideo
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
    }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            launch {
                storyManager.subscribeStoryLike().collect { (id, isLike) ->
                    println("Story with id = $id, isLike: $isLike")
                    logItems.add("Story with id = $id, isLike: $isLike")
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            launch {
                storyManager.subscribeStoryFavorite().collect { (id, isFavorite) ->
                    println("Story with id = $id, isFavorite: $isFavorite")
                    logItems.add("Story with id = $id, isFavorite: $isFavorite")
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            launch {
                storyManager.subscribeStoryView().collect { id ->
                    println("Story with id = $id has been viewed")
                    logItems.add("Story with id = $id has been viewed")
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            launch {
                //storyManager.deleteAllStory()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            launch {
                storyManager.subscribeStoryQuestion().collect { (id, pageIndex, index) ->
                    println("In story with id = $id in page with index = $pageIndex: index of chosen answer is $index")
                    logItems.add("In story with id = $id in page with index = $pageIndex: index of chosen answer is $index")
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            launch {
                storyManager.subscribeStorySkip().collect { (id, pageIndex, index) ->
                    println("In story with id = $id in page with index = $pageIndex: skipTime = $index")
                    logItems.add("In story with id = $id in page with index = $pageIndex: skipTime = $index")
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            launch {
                storyManager.subscribeStoryPause().collect { item ->
                    println("story paused: $item")
                    logItems.add("story paused: $item")
                }
            }
        }
    }
}