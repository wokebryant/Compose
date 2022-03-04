package com.example.composedemo.view.widget

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @Author: LuoJia
 * @Date: 2022-03-04
 * @Description: 下滑刷新控件
 */
class SmartSwipeRefreshState {
    // 打断值变化,可以实现打断动画
    private val mutatorMutex = MutatorMutex()
    private val indicatorOffsetAnimatable = Animatable(0.dp, Dp.VectorConverter)
    val indicatorOffset get() = indicatorOffsetAnimatable.value
    private val _indicatorOffsetFlow  = MutableStateFlow(0f)
    val indicatorOffsetFlow: Flow<Float> get() = _indicatorOffsetFlow
    val isSwipeInProgress by derivedStateOf { indicatorOffset != 0.dp }

    var isRefreshing: Boolean by mutableStateOf(false)

    fun updateOffsetDelta(value: Float) {
        _indicatorOffsetFlow.value = value
    }

    suspend fun snapToOffset(value: Dp) {
        mutatorMutex.mutate(MutatePriority.UserInput) {
            indicatorOffsetAnimatable.snapTo(value)
        }
    }

    suspend fun animateToOffset(value: Dp) {
        mutatorMutex.mutate {
            indicatorOffsetAnimatable.animateTo(value, tween(1000))
        }
    }
}

private class SmartSwipeRefreshNestedScrollConnection(
    val state: SmartSwipeRefreshState,
    val height: Dp
): NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        // 1. 手势首先被父布局处理:
        //    (1)上滑:  父布局处理, 进度条出现, 滑动被父布局消费掉(即进度条和RecycleView的滑动事件失效,子布局offset由updateOffsetDelta()方法控制,
        //                        进度条不出现, 则不消费，由子布局处理(即RecycleView滑动事件生效)
        if (source == NestedScrollSource.Drag && available.y < 0) {
            state.updateOffsetDelta(available.y)

            return if (state.isSwipeInProgress) Offset(x = 0f, y = available.y) else Offset.Zero
        } else {
            return Offset.Zero  // 这里代表, 父布局不处理, 事件交由子布局处理
        }
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        //          当子布局滑动处理完毕(即RecycleView滑到顶部了), onPostScroll()方法才被回调,滑动交由父布局控制
        //          (即RecycleView滑动事件失效,offset由updateOffsetDelta()方法控制)
        return if (source == NestedScrollSource.Drag && available.y > 0) {
            state.updateOffsetDelta(available.y)
            Offset(x = 0f, y = available.y) // 将剩下的所有偏移量全部消费调，不再向外层父布局继续传播了
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        if (state.indicatorOffset > height / 2) {
            state.animateToOffset(height)
            state.isRefreshing = true
        } else {
            state.animateToOffset(0.dp)
        }
        return super.onPreFling(available)
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        return super.onPostFling(consumed, available)
    }
}

@Composable
private fun SubcomposeSmartSwipeRefresh(
    indicator: @Composable () -> Unit,
    content: @Composable (Dp) -> Unit
) {
    SubcomposeLayout { constraints: Constraints ->
        val indicatorPlaceable = subcompose("indicator", indicator).first().measure(constraints)
        val contentPlaceable = subcompose("content") {
            content(indicatorPlaceable.height.toDp())
        }.map {
            it.measure(constraints)
        }.first()
        Log.d("gzz","dp: ${indicatorPlaceable.height.toDp()}")
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
        }
    }
}

/**
 * A smart refresh component can customize your slide refresh animation component.
 * @param onRefresh: Refreshing behavior of data when sliding down.
 * @param state: The state contains some refresh state info.
 * @param loadingIndicator: Specify the refresh animation component.
 * @param content: Some slidable components need to be included here.
 */
@Composable
fun SmartSwipeRefresh(
    onRefresh: suspend () -> Unit,
    state: SmartSwipeRefreshState = remember { SmartSwipeRefreshState() },
    loadingIndicator: @Composable () -> Unit = { CircularProgressIndicator() },
    content: @Composable () -> Unit
) {
    SubcomposeSmartSwipeRefresh(indicator = loadingIndicator) { height ->
        val smartSwipeRefreshNestedScrollConnection = remember(state, height) {
            SmartSwipeRefreshNestedScrollConnection(state, height)
        }
        Box(
            Modifier
                .nestedScroll(smartSwipeRefreshNestedScrollConnection),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(Modifier.offset(y = -height + state.indicatorOffset )) {
                loadingIndicator()
            }
            Box(Modifier.offset(y = state.indicatorOffset )) {
                content()
            }
        }
        val density = LocalDensity.current
        LaunchedEffect(Unit) {
            state.indicatorOffsetFlow.collect {
                val currentOffset = with(density) { state.indicatorOffset + it.toDp() }
                state.snapToOffset(currentOffset.coerceAtLeast(0.dp).coerceAtMost(height))
            }
        }
        LaunchedEffect(state.isRefreshing) {
            if (state.isRefreshing) {
                onRefresh()
                state.animateToOffset(0.dp)
                state.isRefreshing = false
            }
        }
    }
}