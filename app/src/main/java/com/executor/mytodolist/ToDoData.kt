package com.executor.mytodolist

import java.util.*

object ToDoData {
    fun initExample() {
        val day1 = Calendar.getInstance()
        day1.add(Calendar.DATE, -4)
        toDoList.add(
            ToDoEntity("Прическа", "Сходить в Парикмахерскую", ToDoPriority.Mid, false, day1.time),
        )
        toDoList.add(
            ToDoEntity(
                "Смысл жизни",
                "Понять смысл жизни",
                ToDoPriority.High,
                true,
                Calendar.getInstance().time
            )
        )
        toDoList.add(
            ToDoEntity(
                "Посмотреть Чебурашку",
                "или аватара",
                ToDoPriority.Mid,
                true,
                Calendar.getInstance().time
            )
        )
        toDoList.add(
            ToDoEntity(
                "Таро",
                "Узнать судьбу",
                ToDoPriority.Low,
                false,
                Calendar.getInstance().time
            )
        )
        sortItems()
    }


    val toDoList: MutableList<ToDoEntity> = arrayListOf()

    fun sortItems() {
        toDoList.sortWith(compareByDescending { it.date })
        toDoList.sortBy { it.priority }
        toDoList.sortBy { it.isReady }
    }
}