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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.hrsh.story_kit.di.getFontScale
import org.hrsh.story_kit.di.getScreenHeightDp
import org.hrsh.story_kit.domain.entities.PageItem

@Composable
internal fun PageImage(itemImage: PageItem.Image, imageSize: Float, lowerBlackout: Boolean) {
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
            var isExpanded by remember { mutableStateOf(false) }
            var isOverflow by remember { mutableStateOf(false) }
            val textMeasurer = rememberTextMeasurer()
            var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
            val availableWidth by remember { mutableStateOf(0) }

            LaunchedEffect(textMeasurer, itemImage.text, isExpanded, textLayoutResult) {
                textLayoutResult?.let { _ ->
                    val unlimitedLayout = textMeasurer.measure(
                        text = AnnotatedString(itemImage.text),
                        style = TextStyle.Default,
                        constraints = Constraints(maxWidth = availableWidth)
                    )
                    isOverflow = unlimitedLayout.lineCount > maxLinesCollapsed
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(max = if (isExpanded) screenHeightDp * 0.6f else screenHeightDp * 0.25f)
                    .clickable(
                        enabled = isOverflow,
                        onClick = {
                            isOverflow = !isOverflow
                            isExpanded = !isExpanded
                        }
                    ),
                userScrollEnabled = isExpanded
            ) {
                item {
                    Text(
                        text = itemImage.text,
                        color = Color.White,
                        fontSize = (screenHeightDp.value * 0.25f / maxLinesCollapsed / getFontScale() * 0.6f).sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = if (isExpanded) Int.MAX_VALUE else maxLinesCollapsed,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                        onTextLayout = {
                            textLayoutResult = it
                        }
                    )
                }
            }
        }
    }
}