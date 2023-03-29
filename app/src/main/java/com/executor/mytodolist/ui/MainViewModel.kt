package com.executor.mytodolist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.executor.mytodolist.SingleLiveEvent
import com.executor.mytodolist.data.bd.ToDoDatabase
import com.executor.mytodolist.data.entities.ToDoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val bd = ToDoDatabase.getInstance(application.applicationContext).todoDao()
    val todoListFromDb: LiveData<List<ToDoEntity>> = bd.getAll()
    val listUpdatedSLE = SingleLiveEvent<Unit>()

    var todoList = arrayListOf<ToDoEntity>()

    fun update(obj: ToDoEntity, position: Int) {
        todoList[position] = obj
        sortItems()
        viewModelScope.launch(Dispatchers.IO) {
            bd.update(obj)
        }
        listUpdatedSLE.call()
    }

    fun delete(position: Int) {
        val obj = todoList[position]
        todoList.removeAt(position)
        viewModelScope.launch(Dispatchers.IO) {
            bd.delete(obj)
        }
        listUpdatedSLE.call()
    }

    fun insert(obj: ToDoEntity) {
        todoList.add(obj)
        sortItems()
        viewModelScope.launch(Dispatchers.IO) {
            bd.insert(obj)
        }
        listUpdatedSLE.call()
    }

    fun sortItems() {
        todoList.sortWith(compareByDescending { it.date })
        todoList.sortBy { it.priority }
        todoList.sortBy { it.isReady }
    }
}