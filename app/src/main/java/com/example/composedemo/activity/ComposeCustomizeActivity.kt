package com.example.composedemo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.view.ShowCustomizeColumn
import com.example.composedemo.view.ShowCustomizeText

/**
 *  Compose自定义页面
 */
@ExperimentalMaterialApi
@SuppressLint("CoroutineCreationDuringComposition")
class ComposeCustomizeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShowPreview() }
    }

    @Preview(showBackground = true)
    @Composable
    private fun ShowPreview() {
        //通过Modifier自定义padding, 类似自定义View
//        ShowCustomizeText(text = "Hi there!")
//        Text(text = "Hi three!", modifier = Modifier.padding(top = 24.dp))

        //通过Layout Composable,类似于自定义ViewGroup
        ShowCustomizeColumn {
            Text(text = "自定义列表1")
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "自定义列表2")
            Text(text = "自定义列表3")
            Text(text = "自定义列表4")
        }
    }

}