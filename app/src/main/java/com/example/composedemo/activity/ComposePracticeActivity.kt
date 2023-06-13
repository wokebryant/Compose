package com.example.composedemo.activity

import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.base.BaseActivity
import com.example.composedemo.ui.theme.Purple200
import com.example.composedemo.view.ShareAnimationView
import com.example.composedemo.view.ShowBottomSheetScaffold

/**
 * @Author: LuoJia
 * @Date: 2022-03-09
 * @Description: Compose实战练习
 */
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class ComposePracticeActivity : BaseActivity() {

    companion object {
        private const val TAG = "ComposePracticeActivity"
    }

    @Preview
    @Composable
    override fun ShowPreview() {
        PracticeGridView()
    }

    private val practiceObjectList = listOf(
        PracticeModel("🔥共享元素动画", PracticeType.ShareAnimation),
        PracticeModel("🔥BottomSheet脚手架", PracticeType.BottomSheetScaffold),
        PracticeModel("🔥待定", PracticeType.TODO),
        PracticeModel("🔥待定", PracticeType.TODO),
        PracticeModel("🔥待定", PracticeType.TODO),
        PracticeModel("🔥待定", PracticeType.TODO),
        PracticeModel("🔥待定", PracticeType.TODO),
        PracticeModel("🔥待定", PracticeType.TODO),
        PracticeModel("🔥待定", PracticeType.TODO),
    )

    data class PracticeModel(
        val name: String,
        val type: PracticeType
    )

    sealed class PracticeType {
        object ShareAnimation : PracticeType()
        object BottomSheetScaffold : PracticeType()
        object TODO : PracticeType()
    }

    private val itemClick = { type: PracticeType ->
        when (type) {
            PracticeType.ShareAnimation -> {
                setContent { ShareAnimationView() }
            }
            PracticeType.BottomSheetScaffold -> {
                setContent { ShowBottomSheetScaffold() }
            }
            PracticeType.TODO -> {
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    private fun PracticeGridView() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(practiceObjectList) {
                PracticeItem(it.name) { itemClick(it.type) }
            }
        }
    }

    @Composable
    private fun PracticeItem(name: String, onItemClick: () -> Unit) {
        TextButton(
            onClick = onItemClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Purple200
            ),
            modifier = Modifier.height(80.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}

