package com.example.taskmanagementapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.taskmanagementapp.models.Task
import com.example.taskmanagementapp.repository.TaskRepository
import com.example.taskmanagementapp.utils.Resource

class TaskViewModel(application: Application):AndroidViewModel(application) {
    private val taskRepository = TaskRepository(application)
    fun insertTask(task:Task):MutableLiveData<Resource<Long>>{
        return taskRepository.insertTask(task)
    }
    fun getTaskList() = taskRepository.getTaskList()

    fun deleteTaskById(taskId: String):MutableLiveData<Resource<Int>>{
        return taskRepository.deleteTaskByID(taskId)
    }

    fun updateTask(task: Task):MutableLiveData<Resource<Int>>{
        return taskRepository.updateTask(task)
    }

    fun updateSpecificTask(taskID: String, title:String, description:String):MutableLiveData<Resource<Int>>{
        return taskRepository.updateSpecificTask(taskID,title,description)
    }



}