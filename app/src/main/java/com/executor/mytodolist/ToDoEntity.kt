package com.executor.mytodolist

import java.util.*

data class ToDoEntity(
    var name:String,
    var description:String="",
    var priority:ToDoPriority,
    var isReady:Boolean=false,
    val date: Date
)
enum class ToDoPriority{
    High,Mid,Low
}