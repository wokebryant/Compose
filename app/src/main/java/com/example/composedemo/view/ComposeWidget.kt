package com.example.composedemo.view

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
 *  Compose??????
 */

/**
 *  ???????????????
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
                Text(text = "??????????????????????????????")
            },
            text = {
                Text(
                    text = "????????????,??????????????????????????????"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmClick()
                        openDialog.value = false
                    }

                ) {
                    Text(text = "??????")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissCLick()
                        openDialog.value = false
                    }
                ) {
                    Text(text = "??????")
                }
            }
        )
    }
}

/**
 *  ????????????
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
 *  ??????
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
        //??????Material???????????????
        Icon(
            Icons.Filled.Favorite,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = "??????")
    }
}

/**
 *  Button????????????
 */
@Composable
fun ShowPressButton() {
    // ?????????????????????
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
 *  ????????????
 */
@Composable
fun ShowCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            //?????????
            .padding(15.dp)
            .clickable { },
        elevation = 10.dp
    ) {
        Column(
            //?????????
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                //?????????????????????
                text = buildAnnotatedString {
                    append("???????????? ")
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.W900, color = Color.Blue)
                    ) {
                        append("Jetpack Compose ?????????")
                    }
                }
            )

            Text(
                text = buildAnnotatedString {
                    append("??????????????????????????? ")
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
 *  ????????????
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
 *  ??????
 *  Icons.Filled???????????????????????????
 *  tint???????????????????????????
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
 *  ??????
 */
@Composable
fun ShowImage() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.img_river),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .clip(shape = CircleShape)     //????????????
        )

        Image(
            painter = painterResource(id = R.drawable.img_river),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .clip(shape = Shapes.small)     //??????????????????(??????)
                .border(2.dp, MaterialTheme.colors.secondary, shape = Shapes.small)        //??????
        )

        Image(
            painter = painterResource(id = R.drawable.img_river),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(size = 12.dp))     //??????????????????(?????????)
        )
    }

}

/**
 *  ??????????????????
 *  ????????????????????????????????????IconButton,???Box??????indication?????????null
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
 * ??????????????????
 * ??????????????????Shape???????????????????????????
 */
@Composable
fun ShowSurface() {
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp) // ?????????
    ){
        Column(
            modifier = Modifier
                .clickable { }
                .padding(15.dp) // ?????????
        ) {
            Text(
                buildAnnotatedString {
                    append("???????????? ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append("Jetpack Compose ?????????")
                    }
                }
            )
            Text(
                buildAnnotatedString {
                    append("??????????????????????????? ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Surface")
                    }
                }
            )
        }
    }
}

/**
 *  ???????????????
 *  value???????????????0f..1f
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
 *  ??????
 */
@Composable
fun ShowText() {
    Column (
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "?????????Compose????????????",
            style = TextStyle(
                fontWeight = FontWeight.W900,        //??????????????????
                fontSize = 20.sp,
                letterSpacing = 7.sp                //??????????????????
            )
        )

        Spacer(modifier = Modifier.size(10.dp))

        /** ???????????? **/
        Text(
            text = "???????????????????????????,??????????????????,???????????????,??????????????????????????????????????????",
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.size(10.dp))

        /** ???????????? **/
        Text(
            text = "????????????",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            color = Color.Blue
        )
        Text(
            text = "?????????",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Red
        )
        Text(
            text = "????????????",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Right,
            color = Color.Cyan
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "?????????????????????????????????",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.size(10.dp))

        /** ???????????????????????????,?????????,??????????????? **/
        Text(
            buildAnnotatedString {
                append("??????????????????????????? ")
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

        /** ??????????????? **/
        val annotatedText = buildAnnotatedString {
            append("?????????????????????")

            //??????????????? "????????????" ?????????,???????????????????????? "tag"
            pushStringAnnotation(
                tag = "tag1",
                annotation = "????????????"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("????????????")
            }
            pop()

            append("??????")

            pushStringAnnotation(
                tag = "tag2",
                annotation = "????????????"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("????????????")
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

                //???????????????AnnotatedString??????????????????
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

        /** ???????????? **/
        SelectionContainer {
            Text(text = "?????????????????????", color = Color.Magenta)
        }
        Spacer(modifier = Modifier.size(10.dp))

        /** ?????????????????? **/
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            Text(text = "??????high????????????")
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = "??????medium????????????")
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(text = "????????????????????????")
        }
    }
}

/**
 *  ???????????????
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
            Text(text = "??????")
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
 *  ???????????????
 */
@Composable
fun ShowBottomNavigation() {
    var selectedItem by remember {
        mutableStateOf(0)
    }
    val items = listOf("??????", "????????????", "??????", "??????")

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
 *  ????????????
 */
@Composable
fun ShowColumn() {
    Column (modifier = Modifier
        .padding(8.dp)
        .size(60.dp, 300.dp)
    ) {
        //?????????????????????????????????????????????
        Box(modifier = Modifier
            .size(40.dp, 80.dp)
            .background(Color.Magenta))

        //?????????,?????????????????????????????????????????????
        Box(modifier = Modifier
            .width(40.dp)
            .weight(1f)
            .background(Color.Yellow))

        // ?????????????????? fill ????????? false??????????????????????????????????????????????????????
        // ?????????????????????????????????????????????????????? 80 dp????????????????????????
        Box(modifier = Modifier
            .size(40.dp, 80.dp)
            .weight(1f, fill = false)
            .background(Color.Green))
    }
}

/**
 *  ????????????
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
 *  ?????????????????????
 */
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun ShowModalBottomSheetLayout(activity: ComponentActivity) {
    //TODO ?????????Compose????????????????????????,??????Dialog
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
                    ListItem (text = { Text(text = "????????????????????????")})

                    ListItem(
                        text = { Text(text = "github")},
                        icon = { Icon(Icons.Filled.Home, null)}
                    )

                    ListItem(
                        text = { Text(text = "??????")},
                        icon = { Icon(Icons.Filled.Favorite, null)},
                        modifier = Modifier.clickable {}
                    )
                }
            }
        ) {
            //???????????????
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
 *  ??????
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
 *  ?????????,?????????????????????MaterialDesign App??????
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
                    text = "??????Compose?????????",
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
 *  ??????AppBar
 */
@Composable
fun ShowTopAppBar() {
    TopAppBar(
        title = { Text(text = "??????")},
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
 *  ??????
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
 *  ??????ToolBar
 */
@Composable
fun ShowFoldToolBar(activity: ComponentActivity) {
    activity.setContent {
        val toolbarHeight = 200.dp
        val maxUpPx = with(LocalDensity.current) {
            // 56.dp??? androidx.compose.material AppBar.kt ??????????????? private val AppBarHeight = 56.dp
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
                backgroundImageId = R.drawable.img_kukong,
                scrollableAppBarHeight = toolbarHeight,
                toolbarOffsetHeightPx = toolbarOffsetHeightPx
            )
        }
    }
}

/**
 * ????????????
 */
@Composable
fun ShowPullRefreshLayout(activity: ComponentActivity) {
    activity.setContent {
        val sentences = remember {
            mutableStateListOf(
                "???????????????????????? 1",
                "???????????????????????? 2",
                "???????????????????????? 3",
                "???????????????????????? 4",
                "???????????????????????? 5",
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            SmartSwipeRefresh(
                onRefresh = {
                    delay(2000)
                    if (sentences.size == 5) {
                        sentences.add(0, "???????????????????????? 1")
                        sentences.add(0, "???????????????????????? 2")
                        sentences.add(0, "???????????????????????? 3")
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











