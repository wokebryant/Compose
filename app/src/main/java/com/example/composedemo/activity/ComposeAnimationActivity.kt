package com.example.composedemo.activity

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.Message
import com.example.composedemo.R
import com.example.composedemo.WidgetType
import com.example.composedemo.base.BaseActivity
import com.example.composedemo.view.RecyclerViewItem

/**
 * @Author: LuoJia
 * @Date: 2021-11-24
 * @Description: Compose动画界面
 */
@ExperimentalAnimationApi
@ExperimentalMaterialApi
class ComposeAnimationActivity : BaseActivity() {

    @Preview
    @Composable
    override fun ShowPreview() {
//        ShowColumn(ResourceUtil.getAnimationList())

//        AnimationVisibility()
//        AnimationStateButton()
//        AnimationStateSearchBar()
//        AnimationTableButton()
        UpdateTransition()
    }

    @Composable
    private fun ShowColumn(messages: List<Message>) {
        LazyColumn {
            items(messages) { message ->
                RecyclerViewItem(message = message) { widgetType ->
                    when (widgetType) {
                        WidgetType.ANIM_FADE -> {}

                        WidgetType.ANIM_VISIBILITY -> {}

                        else -> {}
                    }
                }
            }
        }
    }

    /**
     *  显隐动画
     */
    @Composable
    private fun AnimationVisibility() {
        var state by remember {
            mutableStateOf(true)
        }

        Column (
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = state,
                enter = slideInVertically(
                    initialOffsetY = { - 1000 },
                    animationSpec = tween(durationMillis = 1200)
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 1200)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { - 1000 },
                    animationSpec = tween(durationMillis = 1200)
                ) + fadeOut(
                    animationSpec = tween(1200)
                ),
            ) {
                Text(
                    text = "这是一个普通的正文",
                    fontWeight = FontWeight.W900,
                    style = MaterialTheme.typography.h5
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 50.dp))

            Button(onClick = { state = !state }) {
                Text(text = if (state) "隐藏" else "显示")
            }
        }
    }

    /**
     *  为单个值制作动画,只需要提供结束值(目标值),类似MotionLayout
     *  AnimationState包含的常用的数值,如果需要自定义数值,请使用AnimateTable
     *  动画不支持打断
     */
    @Composable
    private fun AnimationStateButton() {
        var change by remember {
            mutableStateOf(false)
        }
        val buttonSize by animateDpAsState(
            targetValue = if (change) 32.dp else 24.dp
        )

        IconButton(onClick = {
                change = !change
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Camera,
                contentDescription = null,
                tint = if (change) Color.Red else Color.Gray,
                modifier = Modifier.size(buttonSize)
            )
        }
    }

    @Composable
    private fun AnimationStateSearchBar() {
        var text by remember {
            mutableStateOf("")
        }
        var focusState by remember {
            mutableStateOf(false)
        }
        val size by animateFloatAsState(targetValue = if (focusState) 1f else 0.5f)

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .onFocusChanged {
                        focusState = it.isFocused
                    }
                    .fillMaxWidth(size),
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            )
        }

    }

    /**
     *  自定义数值动画
     *  原生支持Color和Float
     *  如果需要支持其它类型的值可以使用TwoWayConverter()转换
     *  数值必须在协程里更新
     *  动画支持打断
     */
    @Composable
    private fun AnimationTableButton() {
        var change by remember {
            mutableStateOf(false)
        }

        val buttonSizeVariable = remember {
            Animatable(24.dp, Dp.Companion.VectorConverter)
        }

        LaunchedEffect(change) {
            buttonSizeVariable.animateTo(
                if (change) 32.dp else 24.dp,
                animationSpec = tween(1200)
            )
        }

        IconButton(onClick = { change = !change }) {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                modifier = Modifier.size(buttonSizeVariable.value),
                tint = if (change) Color.Red else Color.Gray
            )
        }
    }

    /**
     *  将多个属性数值绑定到一个状态
     *  比如将多个Animated*asState或AnimatedTable结合在一起
     *
     **/
    sealed class SwitchState {
        object OPEN: SwitchState()
        object CLOSE: SwitchState()
    }

    @Composable
    private fun UpdateTransition() {
        var selectedState: SwitchState by remember {
            mutableStateOf(SwitchState.CLOSE)
        }
        val transition = updateTransition(
            targetState = selectedState,
            label = "switch_transition"
        )

        val selectBarPadding by transition.animateDp(
            transitionSpec = { tween(1000) },
            label = ""
        ) {
            when (it) {
                SwitchState.CLOSE -> 40.dp
                SwitchState.OPEN -> 0.dp
            }
        }

        val textAlpha by transition.animateFloat(
            transitionSpec = { tween(1000) },
            label = ""
        ) {
            when(it) {
                SwitchState.CLOSE -> 1f
                SwitchState.OPEN -> 0f
            }
        }

        Box(
            modifier = Modifier
                .size(159.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    selectedState = when (selectedState) {
                        SwitchState.CLOSE -> SwitchState.OPEN
                        SwitchState.OPEN -> SwitchState.CLOSE
                    }
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_kukong),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "我是谁",
                fontSize = 30.sp,
                fontWeight = FontWeight.W900,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(textAlpha)
            )
            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .padding(top = selectBarPadding)
                .background(Color(0xFF5FB878))
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(1 - textAlpha)
                ) {
                    Icon(imageVector = Icons.Rounded.StarBorder, contentDescription = null)
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "卡卡罗特",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W900,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(imageVector = Icons.Rounded.StarBorder, contentDescription = null)
                }
            }
        }
    }


}