package org.hrsh.story_kit.presentation.page

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.ktor.util.reflect.instanceOf
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem
import org.hrsh.story_kit.presentation.story.StoryColors
import kotlin.random.Random

@Composable
internal fun PageQuestion(
    itemQuestion: PageItem.Question,
    imageSize: Float,
    selectedStoryItem: StoryItem,
    onChose: (StoryItem, PageItem, Int) -> Unit,
    storyColors: StoryColors,
    pageQuestionLayout: PageQuestionLayout,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = imageSize
                scaleY = imageSize
            }
            .padding(5.dp)
    ) {
        AsyncImage(
            model = itemQuestion.imageUrl,
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(pageQuestionLayout.questionAlignment)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            text = itemQuestion.question,
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )

        val sumRatio = itemQuestion.listResults.sum().toFloat()
        val currentTime = remember { Animatable(initialValue = 0f) }
        val maxTime = 2f
        val groupedItems = remember {
            itemQuestion.listAnswers.mapIndexed { index, str ->
                Triple(
                    str,
                    index,
                    if (pageQuestionLayout.fixedButtonSize) 1 else Random.nextInt(3) + 1
                )
            }.chunked(pageQuestionLayout.buttonsInRow)
        }
        val groupedItemsCanvasSize = remember {
            (0 until itemQuestion.listAnswers.size).map { Pair(0, 0) }.toMutableList()
        }
        if (itemQuestion.indexSelected != -1) {
            LaunchedEffect(Unit) {
                currentTime.animateTo(
                    targetValue = maxTime,
                    animationSpec = tween(durationMillis = maxTime.toInt() * 1000),
                )
            }
        }
        Column(
            modifier = Modifier.align(pageQuestionLayout.answerAlignment)
        ) {
            groupedItems.forEach { group ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    group.forEach { item ->
                        Button(
                            modifier = Modifier
                                .weight(item.third.toFloat())
                                .padding(
                                    start = pageQuestionLayout.buttonPaddingStart.dp,
                                    top = pageQuestionLayout.buttonPaddingTop.dp,
                                    end = pageQuestionLayout.buttonPaddingEnd.dp,
                                    bottom = pageQuestionLayout.buttonPaddingBottom.dp
                                )
                                .onSizeChanged { size ->
                                    groupedItemsCanvasSize[item.second] = Pair(size.width / 2, size.height / 2)
                                },
                            shape = pageQuestionLayout.buttonShape,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = storyColors.buttonAnswer,
                                disabledBackgroundColor =
                                if (item.second == itemQuestion.indexSelected)
                                    storyColors.buttonAnswer.copy(alpha = 1.2f)
                                else
                                    storyColors.buttonAnswer.copy(alpha = 0.7f)
                            ),
                            onClick = { onChose(selectedStoryItem, itemQuestion, item.second) },
                            enabled = itemQuestion.indexSelected == -1
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (itemQuestion.indexSelected != -1) {
                                    val ratio = itemQuestion.listResults[item.second] / sumRatio
                                    val process = currentTime.value / maxTime
                                    val colorResult = storyColors.colorResult
                                    val density = LocalDensity.current
                                    val width = with(density) { groupedItemsCanvasSize[item.second].first.toDp() }
                                    val height = with(density) { groupedItemsCanvasSize[item.second].second.toDp() }
                                    Canvas(
                                        modifier = Modifier
                                            .width(width)
                                            .height(height)
                                    ) {
                                        val newWidth = size.width * ratio * process

                                        if (newWidth > 30) {
                                            drawRoundRect(
                                                color = colorResult.copy(alpha = 0.5f),
                                                size = size.copy(
                                                    height = size.height * 1.5f,
                                                    width = newWidth * 1.15f
                                                ),
                                                topLeft = Offset(x = -size.width * 0.075f, y = -size.height * 0.25f),
                                                cornerRadius = CornerRadius(x = 60f, y = 60f),
                                            )
                                        } else {
                                            drawArc(
                                                color = colorResult.copy(alpha = 0.5f),
                                                startAngle = 90f,
                                                sweepAngle = 180f,
                                                useCenter = false,
                                                size = size.copy(
                                                    height = size.height * 1.5f,
                                                    width = newWidth + 20f
                                                ),
                                                topLeft = Offset(x = -size.width * 0.075f, y = -size.height * 0.25f),
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = item.first,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}