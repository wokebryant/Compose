package com.example.composedemo.activity

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.base.BaseActivity
import com.example.composedemo.view.ListWithHeader

/**
 * @Author: LuoJia
 * @Date: 2021-11-24
 * @Description: Compose列表
 */
@ExperimentalFoundationApi
class ComposeListActivity : BaseActivity() {


    @Preview
    @Composable
    override fun ShowPreview() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            LazyList()
            Spacer(modifier = Modifier.height(10.dp))
            ShowStickyHeaderListButton()
            Spacer(modifier = Modifier.height(10.dp))
            ListItemClick()
        }

    }

    /**
     *  懒加载列表
     */
    @Composable
    private fun LazyList() {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .height(100.dp)
        ) {
            LazyColumn(
                modifier = Modifier.border(5.dp, color = Color.Blue),
                // 设置内容间距, 第一个Item顶部和最后一个Item底部距离边框8dp，都有Item距离边框16dp
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                // 设置Item间距, 每个Item之间距离10dp
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(20) {
                    Text(text = "LazyColumn")
                }
            }
        }
    }

    @Composable
    private fun ShowStickyHeaderListButton() {
        TextButton(
            onClick = {
                setContent {
                    ListWithHeader()
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ),
        ) {
            Text(text = "点击进入标题列表")
        }
    }

    /**
     *  网格
     */
    @Composable
    fun ImageGrid(imageList: List<Int>) {
        LazyVerticalGrid(
            // 定义一列最小宽度
//            cells = GridCells.Adaptive(minSize = 128.dp)
            // 定义列数
            columns = GridCells.Fixed(3)
        ) {
            items(imageList) {
                Image(
                    painter = painterResource(it),
                    contentDescription = null
                )
            }
        }
    }

    /**
     *  Item点击
     */
    @Composable
    private fun ListItemClick() {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .height(100.dp)
        ) {
            val context = LocalContext.current

            val fruits = listOf("香蕉", "橘子", "苹果", "西瓜")
            LazyColumn (
                modifier = Modifier.border(5.dp, Color.Blue),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(fruits){ fruit->
                    FruitsRow(fruit) { fruitClick(fruit, context) }
                }
            }
        }

    }

    @Composable
    private fun FruitsRow(item:String, clickEvent:()->Unit) {
        Text(
            text = item,
            modifier = Modifier
                .clickable(onClick = clickEvent)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }

    val fruitClick = { fruit: String, context: Context ->
        Toast.makeText(context, fruit, Toast.LENGTH_SHORT).show()
    }


}