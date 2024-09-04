package com.example.mytodoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,//id:主キー
    val title:String,//タスクのタイトル
    val description:String,//タスクの説明
    val done:Boolean//タスクが完了したかどうか
)