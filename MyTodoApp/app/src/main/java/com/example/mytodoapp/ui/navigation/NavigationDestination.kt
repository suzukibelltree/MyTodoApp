package com.example.mytodoapp.ui.navigation

interface NavigationDestination {
    val route:String //遷移先文字列
    val titleRes:Int //タイトルリソースID
}