package com.example.composedemo.activity

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.Message
import com.example.composedemo.R
import com.example.composedemo.WidgetType
import com.example.composedemo.base.BaseActivity
import com.example.composedemo.ui.theme.ComposeDemoTheme
import com.example.composedemo.utils.ResourceUtil
import com.example.composedemo.view.*

/**
 *  Compose组件页面
 */
@ExperimentalMaterialApi
class ComposeWidgetActivity : BaseActivity() {

    companion object {
        private const val TAG = "ComposeWidgetActivity"
    }

    /**
     *  预览页面
     *  可以添加多个"Preview"字段预览不同的效果
     */
    @Preview(name = "Light Mode")
    @Preview(
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
    )
    @Composable
    override fun ShowPreview() {
        ComposeDemoTheme {
            WidgetList()
        }
    }

    /**
     *  首页控件列表
     */
    @Composable
    fun WidgetList() {
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            /* 基本组件 */
            MessageCard(Message("Alter Dialog", type = WidgetType.ALTER_DIALOG))
            MessageCard(Message("Customize Dialog", type = WidgetType.CUSTOMIZE_DIALOG))
            MessageCard(Message("Button", type = WidgetType.BUTTON))
            MessageCard(Message("Card", type = WidgetType.CARD))
            MessageCard(Message("FloatingActionBar", type = WidgetType.FLOAT_ACTION_BUTTON))
            MessageCard(Message("Icon", type = WidgetType.ICON))
            MessageCard(Message("Image", type = WidgetType.IMAGE))
            MessageCard(Message("IconButton", type = WidgetType.ICON_BUTTON))
            MessageCard(Message("Surface", type = WidgetType.SURFACE))
            MessageCard(Message("Slider", type = WidgetType.SLIDER))
            MessageCard(Message("Text", type = WidgetType.TEXT))
            MessageCard(Message("TextField", type = WidgetType.TEXT_FIELD))

            /* 布局组件 */
            MessageCard(Message("RecyclerView", type = WidgetType.RECYCLERVIEW))
            MessageCard(Message("BottomNavigation", type = WidgetType.BOTTOM_NAVIGATION))
            MessageCard(Message("Column", type = WidgetType.COLUMN))
            MessageCard(Message("ModalBottomSheetLayout", type = WidgetType.MODAL_BOTTOM_SHEET_LAYOUT))
            MessageCard(Message("Row", type = WidgetType.ROW))
            MessageCard(Message("Spacer", type = WidgetType.SPACER))
            MessageCard(Message("Scaffold", type = WidgetType.SCAFFOLD))
            MessageCard(Message("TopAppBar", type = WidgetType.TOP_APP_BAR))
        }
    }

    /**
     *  单个Item
     */
    @Composable
    fun MessageCard(msg: Message) {
        /** 添加一个存储状态,当状态更新,使用该状态的Composable方法及其子函数会重新绘制(重组) **/
        var isClick by remember {
            mutableStateOf(false)
        }

        val surfaceColor by animateColorAsState(
            targetValue = if (isClick) Color(0xFFCCCCCC) else MaterialTheme.colors.surface
        )

        if (isClick) {
            OpenWidgetByType(msg.type)
        }

        /** 形状 **/
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 5.dp,
            modifier = Modifier
                .padding(all = 8.dp)
                .clickable {
                    isClick = !isClick
                },
            color = surfaceColor
        ) {
            /** 横向显示 **/
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(all = 8.dp)
            ) {
                /** 显示图片 **/
                Image(
                    painter = painterResource(id = R.drawable.ic_kukong),
                    contentDescription = "profile picture",  //这个描述无障碍
                    modifier = Modifier
                        .size(40.dp),
                    alignment = Alignment.CenterStart,
                )

                /** 间距 **/
                Spacer(modifier = Modifier.padding(5.dp))

                /** 竖排显示 **/
                Column() {
                    Text(
                        text = msg.author,
                        color = Color(0xFFCC7832),
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = msg.body,
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.body2,
//                        maxLines = if (isExpand) Int.MAX_VALUE else 1,
                        modifier = Modifier.animateContentSize()
                    )
                }
            }
        }
    }

    /**
     *  打开控件
     */
    @Composable
    private fun OpenWidgetByType(type: WidgetType) {
        when (type) {
            /* 基本组件 */
            WidgetType.ALTER_DIALOG -> { ShowAlterDialog({}, {})  }

            WidgetType.CUSTOMIZE_DIALOG -> { ShowCustomizeDialog() }

            WidgetType.BUTTON -> { ShowButton() }

            WidgetType.CARD -> { ShowCard() }

            WidgetType.FLOAT_ACTION_BUTTON -> { ShowFloatingActionBar() }

            WidgetType.ICON -> { ShowIcon() }

            WidgetType.IMAGE -> { ShowImage() }

            WidgetType.ICON_BUTTON -> { ShowIconButton() }

            WidgetType.SURFACE -> { ShowSurface() }

            WidgetType.SLIDER -> { ShowSlider() }

            WidgetType.TEXT -> { ShowText() }

            WidgetType.TEXT_FIELD -> { ShowTextField() }

            /* 布局组件 */
            WidgetType.BOTTOM_NAVIGATION -> { ShowBottomNavigation() }

            WidgetType.COLUMN ->  { ShowColumn() }

            WidgetType.MODAL_BOTTOM_SHEET_LAYOUT -> { ShowModalBottomSheetLayout(this) }

            WidgetType.ROW -> { ShowRow() }

            WidgetType.SPACER -> { ShowSpacer() }

            WidgetType.SCAFFOLD -> { ShowScaffold(this) }

            WidgetType.TOP_APP_BAR -> { ShowTopAppBar() }

            WidgetType.RECYCLERVIEW -> { ShowRecyclerView(ResourceUtil.getMessageList(), this)}
            else -> {}
        }
    }



}