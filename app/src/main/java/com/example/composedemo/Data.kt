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
    RECYCLERVIEW,

    ANIM_FADE,
    ANIM_VISIBILITY,
    ANIM_STATE,
    ANIM_TABLE,
    ANIM_TRANSITION
}

enum class AnimType {

}

data class Message(
    val title: String,
    val body: String = "",
    val type: WidgetType = WidgetType.NONE
)


data class ButtonState(
    var text: String,
    var textColor: Color,
    var buttonColor: Color
)