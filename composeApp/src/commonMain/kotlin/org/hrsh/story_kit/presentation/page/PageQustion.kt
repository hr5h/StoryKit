package org.hrsh.story_kit.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.StateFlow
import org.hrsh.story_kit.domain.entities.PageItem
import org.hrsh.story_kit.domain.entities.StoryItem

@Composable
fun PageQuestion(itemQuestion: PageItem.Question,
                 imageSize: Float,
                 selectedStoryItem: StoryItem,
                 onChose: (StoryItem, Int) -> Unit) {
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
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            text = itemQuestion.question,
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            itemQuestion.listAnswers.forEachIndexed { index, item ->
                val modifiedColor = if (selectedStoryItem.indexPressed == -1) {
                    Color.White
                } else {
                    if (index == selectedStoryItem.indexPressed) {
                        Color.White.copy(alpha = 1.2f)
                    } else {
                        Color.White.copy(alpha = 0.7f)
                    }
                }

                Button(
                    modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = modifiedColor),
                    onClick = { onChose(selectedStoryItem, index); }
                    )
                {
                    Text(
                        text = item,
                        fontSize = 28.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}