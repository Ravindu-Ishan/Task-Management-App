package com.example.taskmanagementapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.taskmanagementapp.database.TaskDatabase
import com.example.taskmanagementapp.models.Task
import com.example.taskmanagementapp.utils.Resource
import com.example.taskmanagementapp.utils.Resource.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class TaskRepository(application:Application) {
    private val taskDao = TaskDatabase.getInstance(application).taskDao

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
}