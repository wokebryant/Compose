package com.example.composedemo.activity

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.COMPOSE_WELCOME
import com.example.composedemo.R
import com.example.composedemo.base.BaseActivity
import com.example.composedemo.share.ComposeShareActivity
import com.example.composedemo.utils.AppUtil

/**
 *  Compose主页面
 */
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class ComposeMainActivity : BaseActivity() {

    @Preview
    @Composable
    override fun ShowPreview() {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp)
        ) {
            Text(
                text = COMPOSE_WELCOME,
                style = TextStyle(
                    fontWeight = FontWeight.W900,
                    fontSize = 20.sp
                ),
            )

            PageItem(backgroundColor = Color.Magenta, content = resources.getString(R.string.compose_widget))
            PageItem(backgroundColor = Color.Blue, content = resources.getString(R.string.compose_customize_view))
            PageItem(backgroundColor = Color.Red, content = resources.getString(R.string.compose_list))
            PageItem(backgroundColor = Color.Green, content = resources.getString(R.string.compose_graphics))
            PageItem(backgroundColor = Color.Gray, content = resources.getString(R.string.compose_animation))
            PageItem(backgroundColor = Color.DarkGray, content = resources.getString(R.string.compose_gestures))
            PageItem(backgroundColor = Color.Black, content = resources.getString(R.string.compose_share))
        }
    }

    @Composable
    private fun PageItem(backgroundColor: Color, content: String) {
        Spacer(modifier = Modifier.size(20.dp))

        TextButton(
            onClick = {
                when (content) {
                    resources.getString(R.string.compose_widget) ->
                        AppUtil.startActivity<ComposeWidgetActivity>(this@ComposeMainActivity) {}

                    resources.getString(R.string.compose_customize_view) ->
                        AppUtil.startActivity<ComposeCustomizeActivity>(this@ComposeMainActivity) {}

                    resources.getString(R.string.compose_animation) ->
                        AppUtil.startActivity<ComposeAnimationActivity>(this@ComposeMainActivity) {}

                    resources.getString(R.string.compose_list) ->
                        AppUtil.startActivity<ComposeListActivity>(this@ComposeMainActivity) {}

                    resources.getString(R.string.compose_gestures) ->
                        AppUtil.startActivity<ComposeGestureActivity>(this@ComposeMainActivity) {}

                    resources.getString(R.string.compose_share) ->
                        AppUtil.startActivity<ComposeShareActivity>(this@ComposeMainActivity) {}

                    resources.getString(R.string.compose_graphics) ->
                        AppUtil.startActivity<ComposeGraphicsActivity>(this@ComposeMainActivity) {}
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(shape = CircleShape)
        ) {
            Text(
                text = content,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        }
    }


}