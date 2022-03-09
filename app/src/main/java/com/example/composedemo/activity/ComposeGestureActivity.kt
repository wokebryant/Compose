package com.example.composedemo.activity

import android.content.res.ColorStateList
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.composedemo.base.BaseActivity
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * @Author: LuoJia
 * @Date: 2021-11-24
 * @Description: Composes手势
 */
class ComposeGestureActivity : BaseActivity() {

    @Preview
    @Composable
    override fun ShowPreview() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonClick()
            Spacer(modifier = Modifier.height(10.dp))
            ScrollList()
            Spacer(modifier = Modifier.height(10.dp))
            ScrollText()
            Spacer(modifier = Modifier.height(10.dp))
            DraggableBox()
            Spacer(modifier = Modifier.height(10.dp))
            SwipeableBox()
            Spacer(modifier = Modifier.height(10.dp))
            DoubleFingerTransform()
            Spacer(modifier = Modifier.height(10.dp))
            CustomGestureDrag()
        }
    }

    /**
     *  点击检测，如触碰, 双击, 长按, 点击
     */
    @Preview
    @Composable
    private fun ButtonClick() {
        var desc by remember {
            mutableStateOf("我是文本")
        }
        Text(
            text = desc,
            color = Color.White,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { desc = "onPress" },
                        onDoubleTap = { desc = "onDoubleTap" },
                        onLongPress = { desc = "onLongPress" },
                        onTap = { desc = "onTap" }
                    )
                }
                .background(Color.Blue)
                .padding(10.dp)
        )
    }

    /**
     *  滚动检测
     *  滚动修饰符 verticalScroll or horizontalScroll
     */
    @Composable
    private fun ScrollList() {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .size(100.dp)
                .verticalScroll(rememberScrollState())
        ) {
            repeat(10) {
                Text(text = "Item $it", modifier = Modifier.padding(2.dp))
            }
        }
    }

    /**
     *  可随手势滚动的文本
     *  scrollable修饰符, 可以检测手势偏移量, 但不会偏移内容
     */
    @Composable
    private fun ScrollText() {
        var offset by remember {
            mutableStateOf(0f)
        }
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .size(150.dp)
                .scrollable(
                    orientation = Orientation.Vertical,
                    state = rememberScrollableState { delta ->
                        offset += delta
                        delta
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = offset.toString())
        }
    }

    /**
     *  可拖动的
     *  draggable修饰符, 允许开发者监听UI组件的拖动手势偏移量
     */
    @Composable
    private fun DraggableBox() {
        var offsetX by remember {
            mutableStateOf(0f)
        }

        val boxSideLengthDp = 50.dp
        val boxSideLengthPx = with(LocalDensity.current) {
            boxSideLengthDp.toPx()
        }
        val draggableState = rememberDraggableState { delta ->
            // 限制offset取值范围
            offsetX = (offsetX + delta).coerceIn(0f, 3 * boxSideLengthPx)
        }

        // 由于Modifer链式执行，此时offset必需在draggable与background前面
        Box(
            modifier = Modifier
                .width(boxSideLengthDp * 4)
                .height(boxSideLengthDp)
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .size(boxSideLengthDp)
                    .offset {
                        IntOffset(offsetX.roundToInt(), 0)
                    }
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = draggableState
                    )
                    .background(Color.DarkGray)
            )
        }
    }

    /**
     *  可滑动的
     *  swipeable修饰符
     *  和draggable不同的是允许开发者通过锚点设置实现组组件吸附动画
     */

    enum class Status {
        CLOSE,
        OPEN
    }
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun SwipeableBox() {
        val blockSize = 48.dp
        val blockSizePx = with(LocalDensity.current) { blockSize.toPx() }
        val swipeableState = rememberSwipeableState(initialValue = Status.CLOSE)
        // 锚点（偏移值和状态）
        val anchor = mapOf(
            0f to Status.CLOSE,
            blockSizePx to Status.OPEN
        )
        Box(modifier = Modifier
            .size(height = blockSize, width = blockSize * 2)
            .background(Color.LightGray)
        ) {
            // 由于Modifer链式执行，此时offset必需在draggable与background前面
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(swipeableState.offset.value.toInt(), 0)
                    }
                    .swipeable(
                        state = swipeableState,
                        anchors = anchor,
                        thresholds = { from, to ->
                            // 定制滑动临界阈值(非必须)
                            if (from == Status.CLOSE) {
                                // 从关滑动到开临界阈值为0.3(最大偏移值 * 0.3)
                                FractionalThreshold(0.3f)
                            } else {
                                // 从开滑动到关临界阈值为0.5(最大偏移值 * 0.5)
                                FractionalThreshold(0.5f)
                            }
                        },
                        orientation = Orientation.Horizontal
                    )
                    .size(blockSize)
                    .background(Color.DarkGray)
            )
        }
    }

    /**
     *  双指拖动, 缩放，旋转
     *  Transformer修饰符, 可以监听UI组件的双指拖动，缩放或者旋转手势
     */
    @Composable
    private fun DoubleFingerTransform() {
        // ⚠️offset需要在rotate后面调用，和旋转坐标系有关
        var boxSize = 100.dp
        var offset by remember { mutableStateOf(Offset.Zero) }
        var rotationAngle by remember { mutableStateOf(1f) }
        var scale by remember { mutableStateOf(1f) }

        var transformableState = rememberTransformableState { zoomChange, panChange, rotationChange ->  
            scale *= zoomChange
            offset += panChange
            rotationAngle += rotationChange
        }

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier
                .size(boxSize)
                .rotate(rotationAngle)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .scale(scale)
                .background(Color.Green)
                .transformable(
                    state = transformableState,
                    lockRotationOnZoomPan = false
                )
            ) {

            }
        }
    }

    /**
     *  嵌套滑动
     *  nestedScroll修饰符: 处理嵌套滑动的场景， 为父布局拦截子布局成为可能
     *
     *  折叠ToolBar相关实现在ComposeWidget类
     */
    @Composable
    private fun NestedScroll() {

    }

    /**
     *  自定义手势
     *  PointerInput修饰符
     *  如draggable, swishable, transform这些手势API都是基于PointerInput封装
     *  直接使用PointerInput修饰符可以拓展更多能力
     */
    @Composable
    private fun CustomGestureDrag() {
        val boxSize = 100.dp
        var offset by remember {
            mutableStateOf(Offset.Zero)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(boxSize)
                    .offset {
                        IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
                    }
                    .background(Color.Blue)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                // 拖动开始
                            },
                            onDragEnd = {
                                // 拖动结束
                            },
                            onDragCancel = {
                                // 拖动取消
                            },
                            onDrag = { change: PointerInputChange, dragAmount: Offset ->
                                // 拖动时
                                offset += dragAmount
                            }
                        )
                    }
            )
        }
    }

}