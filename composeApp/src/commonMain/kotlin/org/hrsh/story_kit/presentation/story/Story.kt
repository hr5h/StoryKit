package org.hrsh.story_kit.presentation.story

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hrsh.story_kit.presentation.page.PageImage
import org.hrsh.story_kit.presentation.page.PageItem
import org.hrsh.story_kit.presentation.page.PageQuestion
import org.hrsh.story_kit.presentation.page.PageVideo

@Composable
fun Story(
    selectStoryItem: StoryItem,
    onClose: () -> Unit
) {
    val text = remember {
        mutableIntStateOf(0)
    }
    val currentPage by remember {
        mutableStateOf(selectStoryItem.listPages.first())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .clickable { onClose() }
    ) {
        //TimeLine
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black)
                .padding(5.dp),
        ) { }
        //Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(17f)
                .background(Color.LightGray),
        ) {
            when (currentPage) {
                is PageItem.PageItemImage -> PageImage()
                is PageItem.PageItemVideo -> PageVideo()
                is PageItem.PageItemQuestion -> PageQuestion()
                is PageItem.PageItemGame -> TODO()
                is PageItem.PageItemError -> TODO()
            }
        }
        //LikeAndFavorite
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(Color.Black)
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(modifier = Modifier
                .padding(10.dp), onClick = { text.value += 1 }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "LIKE",
                        tint = Color.White
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 5.dp, end = 10.dp),
                        text = "${text.value}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            IconButton(modifier = Modifier
                .padding(10.dp), onClick = { text.value += 1 }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = "LIKE",
                    tint = Color.White
                )
            }
        }
    }
}