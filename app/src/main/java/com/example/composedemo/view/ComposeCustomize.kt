package com.example.composedemo.view

import android.annotation.SuppressLint
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Compose自定义组件
 */


/**
 *  自定义文本
 */
@Composable
fun ShowCustomizeText(text: String) {
    Text(text = text, modifier = Modifier.firstBaseLineToTop(24.dp))
}

/**
 *  Text组件Padding自定义
 *
 *  measurable: 子元素的测量句柄, 通过提供的api完成测量和布局功能
 *  constraint: 子元素的测量约束, 包括宽度和高度的最大最小值
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
private fun Modifier.firstBaseLineToTop(firstBaseLineToTop: Dp) = Modifier.layout { measurable, constraints ->
    //完成子元素的测量
    val placeable = measurable.measure(constraints)
    //检查测量模式,此处用到了运算重载符 get = []
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]
    //计算Text组件顶部到文本顶部的距离
    val placeableY = firstBaseLineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    //Text组件的宽高
    layout(placeable.width, height) {
        //完成子元素的布局
        placeable.placeRelative(0, placeableY)
    }
}

/**
 *  自定义Layout
 */
@Composable
fun ShowCustomizeColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        //和Modifier.layout不同, 这里都measurables是一个list, 而Modifier.layout的measurable只是一个
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }

        var yPosition = 0
        //方便起见, 将宽高指定为父元素允许的最大宽高
        layout(constraints.maxWidth, constraints.maxHeight) {
            //放置子元素,因为是纵向排列,只需要指定子元素顶部坐标就行
            placeables.forEach { placeable ->
                placeable.placeRelative(x= 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }

}