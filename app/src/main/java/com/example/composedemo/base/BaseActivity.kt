package com.example.composedemo.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * @Author: LuoJia
 * @Date: 2021-11-24
 * @Description: Compose基类
 */
abstract class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShowPreview() }
    }

    @Preview
    @Composable
    abstract fun ShowPreview()

}