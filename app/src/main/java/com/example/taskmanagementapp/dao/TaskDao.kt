package com.example.taskmanagementapp.dao

import androidx.room.*
import com.example.taskmanagementapp.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task ORDER BY date DESC")
    fun getTaskList() : Flow<List<Task>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task):Long

    @Update
    suspend fun updateTask(task:Task):Int

    @Query("UPDATE Task SET taskTitle=:title, description=:description WHERE taskID=:taskId")
    suspend fun updateSpecificTask(taskId:String,title: String,description: String) : Int


    @Query("DELETE FROM Task WHERE taskID == :taskId")
    suspend fun deleteTaskById(taskId:String) : Int


}