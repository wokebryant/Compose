package com.example.composedemo.view

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.composedemo.ButtonState
import com.example.composedemo.Message
import com.example.composedemo.R
import com.example.composedemo.WidgetType
import com.example.composedemo.ui.theme.Shapes
import com.example.composedemo.view.widget.ScrollableAppBar
import com.example.composedemo.view.widget.SmartSwipeRefresh
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  Compose组件
 */

/**
 *  打开提示框
 */
@Composable
fun ShowAlterDialog(confirmClick: () -> Unit, dismissCLick: () -> Unit)  {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = true
            },
            title = {
                Text(text = "确定要放弃当前任务吗")
            },
            text = {
                Text(
                    text = "一旦放弃,已完成操作将无法恢复"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmClick()
                        openDialog.value = false
                    }

                ) {
                    Text(text = "是的")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissCLick()
                        openDialog.value = false
                    }
                ) {
                    Text(text = "暂不")
                }
            }
        )
    }
}

/**
 *  打开弹窗
 */
@Composable
fun ShowCustomizeDialog() {
    var openDialog by remember {
        mutableStateOf(true)
    }

    if (openDialog) {
        Dialog(
            onDismissRequest = {openDialog = false}
        ) {
            Box(
                modifier = Modifier
                    .size(
                        width = 300.dp,
                        height = 200.dp
                    )
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    LinearProgressIndicator()
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(text = "Loading...")
                }
            }
        }
    }
}

/**
 *  按钮
 */
@Composable
fun ShowButton() {
    var isClick by remember {
        mutableStateOf(false)
    }

    if (isClick) {
        ShowPressButton()
    }

    Button(
        onClick = { isClick = !isClick },
        modifier =  Modifier.padding(all = 8.dp)
    ) {
        //添加Material库中的图标
        Icon(
            Icons.Filled.Favorite,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = "喜欢")
    }
}

/**
 *  Button按压效果
 */
@Composable
fun ShowPressButton() {
    // 获取按钮的状态
    val interactionState = remember { MutableInteractionSource() }

    val (text, textColor, buttonColor) = when {
        interactionState.collectIsPressedAsState().value -> {
            ButtonState("Just Pressed", Color.Red, Color.Black)
        }

        else -> {
            ButtonState("Just Button", Color.White, Color.Red)
        }
    }

    Button(
        onClick = { /*TODO*/ },
        interactionSource = interactionState,
        elevation = null,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
        ),
        modifier = Modifier.padding(all = 8.dp)
    ) {
        Text(
            text = text, color = textColor
        )
    }
}

/**
 *  卡片效果
 */
@Composable
fun ShowCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            //外边距
            .padding(15.dp)
            .clickable { },
        elevation = 10.dp
    ) {
        Column(
            //内边距
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                //自定义文本样式
                text = buildAnnotatedString {
                    append("欢迎来到 ")
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.W900, color = Color.Blue)
                    ) {
                        append("Jetpack Compose 博物馆")
                    }
                }
            )

            Text(
                text = buildAnnotatedString {
                    append("你现在观看的章节是 ")
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.W900)
                    ) {
                        append("Card")
                    }
                }
            )
        }
    }
}

/**
 *  悬浮按钮
 */
@Composable
fun ShowFloatingActionBar() {
    FloatingActionButton(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(all = 8.dp),
        backgroundColor = Color.Red
    ) {
        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
    }

}

/**
 *  图标
 *  Icons.Filled包里含有常见的图标
 *  tint可以更改图标的颜色
 */
@Composable
fun ShowIcon() {
    Row (
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(Icons.Filled.AccountBox, contentDescription = null)
        Icon(Icons.Filled.Add, contentDescription = null, tint = Color.Gray)
        Icon(Icons.Filled.Delete, contentDescription = null, tint = Color.Red)
        Icon(Icons.Filled.Create, contentDescription = null, tint = Color.DarkGray)
        Icon(Icons.Filled.Done, contentDescription = null, tint = Color.Magenta)
        Icon(Icons.Filled.ExitToApp, contentDescription = null, tint = Color.Yellow)
        Icon(Icons.Filled.Face, contentDescription = null, tint = Color.Cyan)
    }
}

/**
 *  图片
 */
@Composable
fun ShowImage() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .clip(shape = CircleShape)     //圆形裁剪
        )

        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .clip(shape = Shapes.small)     //圆角矩形裁剪(预设)
                .border(2.dp, MaterialTheme.colors.secondary, shape = Shapes.small)        //边框
        )

        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))     //圆角矩形裁剪(自定义)
        )
    }

}

/**
 *  带按钮带图标
 *  取消水波纹需要自定义一个IconButton,将Box里的indication设置为null
 */
@Composable
fun ShowIconButton() {
    Row  {
       IconButton(onClick = { /*TODO*/ }) {
           Icon(Icons.Filled.Search, null)
       }
       IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.ArrowBack, null)
       }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Done, null)
        }
    }

}

/**
 * 自定义的形状
 * 类似于容器，Shape里的元素都基于容器
 */
@Composable
fun ShowSurface() {
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp) // 外边距
    ){
        Column(
            modifier = Modifier
                .clickable { }
                .padding(15.dp) // 内边距
        ) {
            Text(
                buildAnnotatedString {
                    append("欢迎来到 ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append("Jetpack Compose 博物馆")
                    }
                }
            )
            Text(
                buildAnnotatedString {
                    append("你现在观看的章节是 ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Surface")
                    }
                }
            )
        }
    }
}

/**
 *  可滑动控件
 *  value取值范围为0f..1f
 */
@Composable
fun ShowSlider() {
    var progress by remember {
        mutableStateOf(0.2f)
    }

    Slider(
        value = progress,
        onValueChange = { progress = it },
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.Red,
            inactiveTrackColor = Color.Blue
        ),
        modifier = Modifier.padding(8.dp)
    )
}

/**
 *  文本
 */
@Composable
fun ShowText() {
    Column (
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "这里是Compose文本学习",
            style = TextStyle(
                fontWeight = FontWeight.W900,        //设置文本粗细
                fontSize = 20.sp,
                letterSpacing = 7.sp                //设置文本间隔
            )
        )

        Spacer(modifier = Modifier.size(10.dp))

        /** 文本截断 **/
        Text(
            text = "这是一段超长的文本,超出最大行后,会截断显示,比如用省略号代替被截断的文本",
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.size(10.dp))

        /** 文本对齐 **/
        Text(
            text = "每天摸鱼",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            color = Color.Blue
        )
        Text(
            text = "这好吗",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Red
        )
        Text(
            text = "这非常好",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Right,
            color = Color.Cyan
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "这是一段居右显示的文本",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.size(10.dp))

        /** 让文本显示分段颜色,下划线,加粗等功能 **/
        Text(
            buildAnnotatedString {
                append("你现在观看的章节是 ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W900,
                        color = Color.Red)
                ) {
                    append("Text")
                }
            },
        )

        Spacer(modifier = Modifier.size(10.dp))

        /** 可点击文本 **/
        val annotatedText = buildAnnotatedString {
            append("勾选即代表同意")

            //附加注释到 "用户协议" 字段上,元数据为自定义的 "tag"
            pushStringAnnotation(
                tag = "tag1",
                annotation = "用户协议"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("用户协议")
            }
            pop()

            append("还有")

            pushStringAnnotation(
                tag = "tag2",
                annotation = "免责条款"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("免责条款")
            }
            pop()
        }

        val openDialog = remember { mutableStateOf(false) }

        if (openDialog.value) {
            ShowAlterDialog ({
                openDialog.value = false
            }, {
                openDialog.value = false
            })
        }

        ClickableText(
            text = annotatedText,
            onClick = { offset ->

                //获取附加在AnnotatedString的字符串注释
                annotatedText.getStringAnnotations(
                    tag = "tag1",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    openDialog.value = true
                }

                annotatedText.getStringAnnotations(
                    tag = "tag2",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    openDialog.value = true
                }
            }
        )
        Spacer(modifier = Modifier.size(10.dp))

        /** 文本复制 **/
        SelectionContainer {
            Text(text = "复制这一段文本", color = Color.Magenta)
        }
        Spacer(modifier = Modifier.size(10.dp))

        /** 文本强调功能 **/
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            Text(text = "这是high强调效果")
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = "这是medium强调效果")
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(text = "这是禁用后的效果")
        }
    }
}

/**
 *  文本输入框
 */
@Composable
fun ShowTextField() {
    var text by remember {
        mutableStateOf("")
    }
    TextField(
        value = text,
        onValueChange = {text = it},
        singleLine = true,
        label = {
            Text(text = "邮箱")
        },
        modifier = Modifier.padding(8.dp),
        leadingIcon = {
            Icon( Icons.Filled.Search, contentDescription = null)
        },
        trailingIcon = {
            Text(text = "@163.com", modifier = Modifier.padding(end = 8.dp))
        }
    )
}

/**
 *  底部导航栏
 */
@Composable
fun ShowBottomNavigation() {
    var selectedItem by remember {
        mutableStateOf(0)
    }
    val items = listOf("主页", "我喜欢的", "收藏", "设置")

    BottomNavigation {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    when (index) {
                        0 -> Icon(Icons.Filled.Home, null)
                        1 -> Icon(Icons.Filled.Favorite, null)
                        2 -> Icon(Icons.Filled.DateRange, null)
                        3 -> Icon(Icons.Filled.Settings, null)
                    }
                },
                label = { Text(text = item)},
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }

}

/**
 *  竖排控件
 */
@Composable
fun ShowColumn() {
    Column (modifier = Modifier
        .padding(8.dp)
        .size(60.dp, 300.dp)
    ) {
        //没有权重的子项将会有指定的大小
        Box(modifier = Modifier
            .size(40.dp, 80.dp)
            .background(Color.Magenta))

        //有权重,这个子项将会占用剩余高度的一半
        Box(modifier = Modifier
            .width(40.dp)
            .weight(1f)
            .background(Color.Yellow))

        // 有权重，但是 fill 参数是 false，这个子项将会最多占用剩余高度的一半
        // 因此，如果指定的高度较大，它将会占用 80 dp（它的首选高度）
        Box(modifier = Modifier
            .size(40.dp, 80.dp)
            .weight(1f, fill = false)
            .background(Color.Green))
    }
}

/**
 *  横排控件
 */
@Composable
fun ShowRow() {
    Row(modifier = Modifier
        .padding(8.dp)
        .height(80.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .size(70.dp, 40.dp)
            .background(Color.Magenta))

        Box(modifier = Modifier
            .height(40.dp)
            .weight(1f)
            .background(Color.Yellow))

        Box(modifier = Modifier
            .size(70.dp, 40.dp)
            .weight(1f, fill = true)
            .background(Color.Green))
    }
}

/**
 *  底部卡片式弹窗
 */
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun ShowModalBottomSheetLayout(activity: ComponentActivity) {
    //TODO 如何在Compose显示两个全屏视图,类似Dialog
    activity.setContent {
        val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()
        scope.launch {
            state.show()
        }
        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                Column {
                    ListItem (text = { Text(text = "选择分享到哪里吧")})

                    ListItem(
                        text = { Text(text = "github")},
                        icon = { Icon(Icons.Filled.Home, null)}
                    )

                    ListItem(
                        text = { Text(text = "微信")},
                        icon = { Icon(Icons.Filled.Favorite, null)},
                        modifier = Modifier.clickable {}
                    )
                }
            }
        ) {
            //返回键监听
            BackHandler (
                enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded
                        || state.currentValue == ModalBottomSheetValue.Expanded),
                onBack = {
                    scope.launch {
                        state.hide()
                    }
                }
            )

        }
    }
}

/**
 *  空间
 */
@Composable
fun ShowSpacer() {
    Row {
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Red))
        Spacer(Modifier.width(20.dp))
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Magenta))
        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Black))
    }
}

/**
 *  脚手架,可以构建基于到MaterialDesign App模版
 */
@Composable
fun ShowScaffold(activity: ComponentActivity) {
    activity.setContent {
        Scaffold (
            topBar = {
                ShowTopAppBar()
            },

            bottomBar = {
                ShowBottomNavigation()
            }
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                Text(
                    text = "这是Compose脚手架",
                    color = Color.Magenta,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

/**
 *  顶部AppBar
 */
@Composable
fun ShowTopAppBar() {
    TopAppBar(
        title = { Text(text = "标题")},
        navigationIcon = { Icon(Icons.Filled.ArrowBack, null)},
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Search, null)
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.MoreVert, null)
            }
        }
    )
}

/**
 *  列表
 */
@Composable
@ExperimentalMaterialApi
fun ShowRecyclerView(messages: List<Message>, activity: ComponentActivity) {
    activity.setContent {
        LazyColumn {
            items(messages) { message ->
                RecyclerViewItem(message) {}
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun RecyclerViewItem(message: Message, onItemClick: (WidgetType) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = Shapes.small,
        onClick = { onItemClick(message.type) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = message.title, color = Color.DarkGray)
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = message.body, color = Color.Blue, fontWeight = FontWeight.ExtraBold)
        }
    }
}

/**
 *  折叠ToolBar
 */
@Composable
fun ShowFoldToolBar(activity: ComponentActivity) {
    activity.setContent {
        val toolbarHeight = 200.dp
        val maxUpPx = with(LocalDensity.current) {
            // 56.dp为 androidx.compose.material AppBar.kt 里面定义的 private val AppBarHeight = 56.dp
            toolbarHeight.roundToPx().toFloat() - 56.dp.roundToPx().toFloat()
        }
        val minUpPx = 0f
        val toolbarOffsetHeightPx = remember {
            mutableStateOf(0f)
        }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    val newOffset = toolbarOffsetHeightPx.value + delta
                    toolbarOffsetHeightPx.value = newOffset.coerceIn(- maxUpPx, - minUpPx)
                    return super.onPreScroll(available, source)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            LazyColumn {
                items(100) { index ->
                    Text(
                        text = "This is item $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
            ScrollableAppBar(
                backgroundImageId = R.drawable.ic_kukong,
                scrollableAppBarHeight = toolbarHeight,
                toolbarOffsetHeightPx = toolbarOffsetHeightPx
            )
        }
    }
}

/**
 * 下拉刷新
 */
@Composable
fun ShowPullRefreshLayout(activity: ComponentActivity) {
    activity.setContent {
        val sentences = remember {
            mutableStateListOf(
                "这是刷新前的页面 1",
                "这是刷新前的页面 2",
                "这是刷新前的页面 3",
                "这是刷新前的页面 4",
                "这是刷新前的页面 5",
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            SmartSwipeRefresh(
                onRefresh = {
                    delay(2000)
                    if (sentences.size == 5) {
                        sentences.add(0, "这是刷新后的页面 1")
                        sentences.add(0, "这是刷新后的页面 2")
                        sentences.add(0, "这是刷新后的页面 3")
                    }
                },
                loadingIndicator = {
                    Box(modifier = Modifier.padding(10.dp)) {
                        CircularProgressIndicator(Modifier.size(40.dp))
                    }
                }
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(sentences.size) { index ->
                        val currentSentence = sentences[index]
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                            elevation = 4.dp
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentSentence,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}











