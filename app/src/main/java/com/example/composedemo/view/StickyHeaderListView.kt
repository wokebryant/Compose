package com.example.composedemo.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composedemo.R
import com.example.composedemo.ui.theme.Purple200
import kotlinx.coroutines.launch

/**
 * @Author: LuoJia
 * @Date: 2022-02-08
 * @Description: 分组列表
 */

@Composable
private fun Contributors() {
    Column {
        MemberItem(R.drawable.img_kukong, " 卡卡罗特")
        MemberItem(R.drawable.img_kukong, " 孙悟空")
        MemberItem(R.drawable.img_kukong, " KuKong")
        MemberItem(R.drawable.img_kukong, " 孙猴子")
        MemberItem(R.drawable.img_kukong, " 超级赛亚人之神")
    }
}

@Composable
fun TouchFish() {
    for (index in 0..20) {
        MemberItem(R.drawable.img_laugh, "Rugger")
    }
}

@Composable
private fun MemberItem(imageID: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(imageID),
                contentDescription = null
            )
        }
        Spacer(Modifier.padding(horizontal = 8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.W500
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = {  }
            ) {
                Icon(Icons.Filled.Email, null)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ListWithHeader() {
    val sections = listOf("贡献者", "卡卡罗特的粉丝")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        Column {
            TopAppBar (
                title = {
                    Text(text = "地球和平贡献者")
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.Search, null)
                    }
                },
                backgroundColor = Color.White
            )

            LazyColumn(
                state = listState
            ) {
                sections.forEachIndexed { index, section ->
                    stickyHeader {
                        Text(
                            text = section,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF2F4FB))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            fontWeight = FontWeight.W700,
                            color = Color(0xFF0079D3)
                        )
                    }
                    when(index) {
                        0 -> item { Contributors() }
                        1 -> item { TouchFish() }
                    }
                }
            }
        }

        AnimatedVisibility(visible = showButton) {
            Log.i("WokeBryant", " showButton= $showButton")
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                backgroundColor = Purple200,
                modifier = Modifier.padding(bottom = 50.dp)
            ) {
                Icon(Icons.Filled.Face, contentDescription = "Localized description")
            }
        }
    }

}