package com.example.taskmanagementapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.taskmanagementapp.database.TaskDatabase
import com.example.taskmanagementapp.models.Task
import com.example.taskmanagementapp.utils.Resource
import com.example.taskmanagementapp.utils.Resource.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception

class TaskRepository(application:Application) {
    private val taskDao = TaskDatabase.getInstance(application).taskDao

    fun getTaskList() = flow {
        emit(Loading())
        try {
            val result = taskDao.getTaskList()
            emit(Success(result))
        }catch(e:Exception){
            emit(Error(e.message.toString()))
        }
    }

    fun insertTask(task: Task)= MutableLiveData<Resource<Long>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch{
                val result = taskDao.insertTask(task)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }

    fun deleteTaskByID(task: String)= MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch{
                val result = taskDao.deleteTaskById(task)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }

    fun updateTask(task: Task)= MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch{
                val result = taskDao.updateTask(task)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }

    fun updateSpecificTask(taskID: String, title:String, description:String)= MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch{
                val result = taskDao.updateSpecificTask(taskID, title, description)
                postValue(Success(result))
            }
        }catch (e:Exception){
            postValue(Error(e.message.toString()))
        }
    }


}