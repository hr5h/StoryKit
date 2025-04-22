package org.hrsh.story_kit.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.presentation.story.calculateSizeCoefficient

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
                .background(if(lowerBlackout) blackoutGradient else transparentGradient)
        ) {
            Text(
                text = itemImage.text,
                color = Color.White,
                fontSize = (28.0 * calculateSizeCoefficient(itemImage.text.length)).toInt().sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
            )
        }
    }
}