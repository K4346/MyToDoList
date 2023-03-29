package com.executor.mytodolist.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.executor.mytodolist.data.entities.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao

    companion object {
        private var db: ToDoDatabase? = null
        private const val DB_NAME = "todo.db"
        private val Lock = Any()
        fun getInstance(context: Context): ToDoDatabase {
            synchronized(Lock) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(context, ToDoDatabase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }
}