package com.example.composedemo

import androidx.compose.ui.graphics.Color


enum class WidgetType {
    NONE,
    ALTER_DIALOG,
    CUSTOMIZE_DIALOG,
    BUTTON,
    CARD,
    FLOAT_ACTION_BUTTON,
    ICON,
    IMAGE,
    ICON_BUTTON,
    SURFACE,
    SLIDER,
    TEXT,
    TEXT_FIELD,

    BOTTOM_NAVIGATION,
    COLUMN,
    MODAL_BOTTOM_SHEET_LAYOUT,
    ROW,
    SPACER,
    SCAFFOLD,
    TOP_APP_BAR,
    RECYCLERVIEW
}

data class Message(
    val author: String,
    val body: String = "",
    val type: WidgetType = WidgetType.NONE
)

data class ButtonState(
    var text: String,
    var textColor: Color,
    var buttonColor: Color
)