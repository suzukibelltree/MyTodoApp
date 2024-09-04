package com.example.mytodoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version =1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase(){
    abstract fun itemDao():ItemDao //daoインタフェースをプロパティとして定義しておく

    companion object{ //companion object内に定義したプロパティや関数は静的メンバとなる
        @Volatile
        private var Instance: TodoDatabase?=null

        fun getDatabase(context: Context): TodoDatabase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(
                    context,//DBのコンテキスト
                    TodoDatabase::class.java,//DBのクラス
                    "todo_database"//DBの名前
                ).build().also { Instance=it }
            }
        }

    }
}