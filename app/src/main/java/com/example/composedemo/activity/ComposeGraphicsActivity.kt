package com.example.composedemo.activity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.base.BaseActivity

/**
 * @Author: LuoJia
 * @Date: 2022-01-28
 * @Description: Compose绘制
 */
class ComposeGraphicsActivity : BaseActivity() {

    @Preview
    @Composable
    override fun ShowPreview() {
        Column(modifier = Modifier.fillMaxSize()) {
            DrawLine()
            DrawCircle()
            DrawRect()
        }
    }

    /**
     * 绘制线段
     */
    @Composable
    fun DrawLine() {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Gray)
        ) {
           val canvasWidth = size.width
           val canvasHeight = size.height
            val a = size / 2F

           drawLine(
               start = Offset(x = canvasWidth, y = 0f),
               end = Offset(x = 0f, y = canvasHeight),
               color = Color.Green,
               strokeWidth = 4.0f
           )
        }
    }

    /**
     * 绘制圆
     */
    @Composable
    fun DrawCircle() {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Gray)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawCircle(
                color = Color.Blue,
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 3
            )
        }
    }

    /**
     *  绘制矩形
     *  可变属性
     */
    @Composable
    fun DrawRect() {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val canvasSize = size
            val canvasWidth = size.width
            val canvasHeight = size.height

            withTransform({
                translate(left = canvasWidth / 2)
                rotate(degrees = 45f)
            }) {
                drawRect(
                    color = Color.Cyan,
                    topLeft = Offset(x = canvasWidth / 5f, y = canvasHeight / 3f),
//                    size = Size(width = 100f, height = 400f)
                    size = canvasSize / 3f
                )
            }
        }
    }

}