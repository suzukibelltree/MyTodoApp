package com.example.mytodoapp.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppBar(
    title:String,//トップバーに表示されるタイトル
    canNavigateBack:Boolean,//前に戻るボタンが有効か
    modifier: Modifier =Modifier,
    scrollBehavior: TopAppBarScrollBehavior?=null,//トップバーのスクロール動作
    navigateUp:()->Unit={}//ナビゲーションアップボタンが押されたときに呼び出される関数
){
    CenterAlignedTopAppBar(
        title = {Text(text = title)},
        modifier=modifier,
        scrollBehavior=scrollBehavior,
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick =navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )

                }
            }
        }
    )
}