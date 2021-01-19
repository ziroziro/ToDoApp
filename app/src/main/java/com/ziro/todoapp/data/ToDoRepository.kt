package com.ziro.todoapp.data

import androidx.lifecycle.LiveData
import com.ziro.todoapp.data.ToDoDao
import com.ziro.todoapp.data.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData((toDoData))
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAllData(){
        toDoDao.deleteAllData()
    }
}