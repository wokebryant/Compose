package com.example.composedemo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.view.ShowCustomizeColumn
import com.example.composedemo.view.ShowCustomizeIntrinsicRow
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
//        ShowCustomizeColumn {
//            Text(text = "自定义列表1")
//            Spacer(modifier = Modifier.size(20.dp))
//            Text(text = "自定义列表2")
//            Text(text = "自定义列表3")
//            Text(text = "自定义列表4")
//        }

        // 固有特性测量
        ShowCustomizeIntrinsicRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp),
        ) {
            Text(text = "Left",
                Modifier
                    .wrapContentWidth(Alignment.Start)
                    .layoutId("main"))

            Divider(
                color = Color.Black,
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .layoutId("divider")
            )

            Text(text = "Right",
                Modifier
                    .wrapContentWidth(Alignment.End)
                    .layoutId("main"))
        }
    }

}