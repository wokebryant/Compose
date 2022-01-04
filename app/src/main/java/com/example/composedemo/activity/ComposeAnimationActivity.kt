package com.example.composedemo.activity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.Message
import com.example.composedemo.WidgetType
import com.example.composedemo.base.BaseActivity
import com.example.composedemo.utils.ResourceUtil
import com.example.composedemo.view.RecyclerViewItem

/**
 * @Author: LuoJia
 * @Date: 2021-11-24
 * @Description: Compose动画界面
 */
@ExperimentalAnimationApi
@ExperimentalMaterialApi
class ComposeAnimationActivity : BaseActivity() {

    @Preview
    @Composable
    override fun ShowPreview() {
        ShowColumn(ResourceUtil.getAnimationList())
    }

    @Composable
    private fun ShowColumn(messages: List<Message>) {
        LazyColumn {
            items(messages) { message ->
                RecyclerViewItem(message = message) { widgetType ->
                    when (widgetType) {
                        WidgetType.ANIM_FADE -> {}

                        WidgetType.ANIM_VISIBILITY -> {}

                    }
                }
            }
        }
    }

    /**
     *  进入退出动画
     */
    @Composable
    private fun ShowFadeAnim() {
        var state by remember {
            mutableStateOf(true)
        }

        Column (
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(visible = state) {
                Text(
                    text = "这是一个普通的正文",
                    fontWeight = FontWeight.W900,
                    style = MaterialTheme.typography.h5
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 50.dp))

            Button(onClick = { state = !state }) {
                Text(text = if (state) "隐藏" else "显示")
            }
        }
    }


}