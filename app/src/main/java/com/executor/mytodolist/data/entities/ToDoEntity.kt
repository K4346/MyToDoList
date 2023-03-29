package com.executor.mytodolist.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDoEntity(
    var name: String,
    var description: String = "",
    var priority: ToDoPriority,
    var isReady: Boolean = false,
    val date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

enum class ToDoPriority {
    High, Mid, Low
}