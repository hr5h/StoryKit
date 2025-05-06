package org.hrsh.story_kit.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.hrsh.story_kit.di.getFontScale
import org.hrsh.story_kit.di.getScreenHeightDp
import org.hrsh.story_kit.domain.entities.PageItem

@Composable
internal fun PageImage(
    itemImage: PageItem.Image,
    imageSize: Float,
    pageCount: Int,
    pageIndex: Int,
    lowerBlackout: Boolean
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
            model = itemImage.imageUrl,
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        val blackoutGradient = Brush.verticalGradient(
            0.05f to Color(0f, 0f, 0f, 0.5f),
            1.0f to Color.Transparent,
            startY = 0.0f,
            endY = 600.0f
        )
        val transparentGradient = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color.Transparent
            )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .background(if (lowerBlackout) blackoutGradient else transparentGradient)
        ) {
            val maxLinesCollapsed: Int = 5
            val screenHeightDp = getScreenHeightDp()
            val textVariabilityList =
                remember {
                    (0 until pageCount).map { Pair(false, false) }.toMutableStateList()
                }

            println("pageCount: $pageCount; pageIndex: $pageIndex; Current pair: ${textVariabilityList[pageIndex]}")

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(max = if (textVariabilityList[pageIndex].second) screenHeightDp * 0.6f else screenHeightDp * 0.25f)
                    .clickable(
                        enabled = textVariabilityList[pageIndex].second || textVariabilityList[pageIndex].first,
                        onClick = {
                            textVariabilityList[pageIndex] = Pair(
                                !textVariabilityList[pageIndex].first,
                                !textVariabilityList[pageIndex].second
                            )
                        }
                    ),
                userScrollEnabled = textVariabilityList[pageIndex].second
            ) {
                item {
                    Text(
                        text = itemImage.text,
                        color = Color.White,
                        fontSize = (screenHeightDp.value * 0.25f / maxLinesCollapsed / getFontScale() * 0.6f).sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = if (textVariabilityList[pageIndex].second) Int.MAX_VALUE else maxLinesCollapsed,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                        onTextLayout = {
                            textVariabilityList[pageIndex] =
                                Pair(it.hasVisualOverflow, textVariabilityList[pageIndex].second)
                        }
                    )
                }
            }
        }
    }
}