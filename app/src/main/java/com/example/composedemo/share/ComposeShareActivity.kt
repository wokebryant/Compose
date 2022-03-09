package com.example.composedemo.share

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.R
import com.example.composedemo.base.BaseActivity

/**
 * @Author: LuoJia
 * @Date: 20212-1-4
 * @Description: Compose分享
 */
@ExperimentalAnimationApi
class ComposeShareActivity : BaseActivity() {

    @Preview
    @Composable
    override fun ShowPreview() {
//        Text(text = "Hello World!")
//        PreviewMessageCard()
        JetpackCompose()
    }

    @Composable
    private fun MessageCard(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview
    @Composable
    private fun PreviewMessageCard() {
//        MessageCard(name = "Android")
    }

    @Composable
    private fun ClickShowDialog() {
        Button(onClick = { /**/ }) {
            Text(text = "点击")
        }
    }

    @Composable
    private fun JetpackCompose() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE6E7E6)),
            contentAlignment = Alignment.Center
        ) {
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(12.dp)
            ){
                var expanded by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier
                        .clickable { expanded = !expanded },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.size(50.dp))
                    Image(
                        painter = painterResource(id = R.drawable.img_kukong),
                        modifier = Modifier.size(200.dp),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.size(50.dp))

                    AnimatedVisibility(visible = expanded) {
                        Text(
                            text = "Jetpack Compose",
                            style = MaterialTheme.typography.h2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }
            }
        }
    }

}