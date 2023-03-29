package com.executor.mytodolist.data.bd

import androidx.lifecycle.LiveData
import androidx.room.*
import com.executor.mytodolist.data.entities.ToDoEntity

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): LiveData<List<ToDoEntity>>

    @Insert
    fun insert(user: ToDoEntity)

    @Update
    fun update(user: ToDoEntity)

    @Delete
    fun delete(user: ToDoEntity)
}