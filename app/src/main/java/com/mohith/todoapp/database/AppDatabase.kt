package com.mohith.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mohith.todoapp.model.TodoModel
import com.mohith.todoapp.utils.subscribeOnBackground

@Database(entities = [TodoModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao():TodoDao

    companion object{

        private var instance :AppDatabase? = null

        @Synchronized
        fun getInstance(ctx:Context) : AppDatabase{
            if (instance == null){
                instance = Room.databaseBuilder(ctx.applicationContext,AppDatabase::class.java,"todo")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance!!
        }

        private val roomCallback = object :Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                addData(instance!!)
            }
        }

        private fun addData(db:AppDatabase){
            val todoDao = db.todoDao()
            subscribeOnBackground {
                todoDao.insert(TodoModel(id = 1, title = "DEMO", description = "* Swipe LEFT to DELETE. \n* Swipe RIGHT to COMPLETE"))

            }
        }
    }


}