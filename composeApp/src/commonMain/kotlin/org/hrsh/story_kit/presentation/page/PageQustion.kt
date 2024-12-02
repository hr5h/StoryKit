package org.hrsh.story_kit.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import org.hrsh.story_kit.domain.PageItem

@Composable
fun PageQuestion(itemQuestion: PageItem.Question, imageSize: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = imageSize
                scaleY = imageSize
            }
            .background(Color.Black)
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
            for (i in itemQuestion.listAnswers) {
//                Button(
//                    modifier = Modifier
//                    .padding(vertical = 5.dp)
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp)
//                    .background(Color.White),
//                    onClick = {}
//                    )
//                {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color.White),
                        text = i,
                        fontSize = 28.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
//                }
            }
        }
    }
}