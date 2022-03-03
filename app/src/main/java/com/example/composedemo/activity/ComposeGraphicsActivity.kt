package com.example.composedemo.activity

import android.util.Log.d
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
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
        val LocalColor = compositionLocalOf { Color.Gray }

        Column() {
            CompositionLocalProvider(
                LocalContentColor provides Color.Gray,
            ) {

            }
        }
    }

    /**
     *  自定义一个主题
     */
    @Composable
    private fun CustomColorTheme(isDark: Boolean, content: @Composable () -> Unit) {
        val BLUE = Color(0xFF0000FF)
        val RED = Color(0xFFDC143C)
        val colors = if (isDark) {
            darkColors(
                primary = BLUE
            )
        } else {
            lightColors(
                primary = RED
            )
        }


        MaterialTheme(
            colors = colors,
            content = content
        )
    }




}