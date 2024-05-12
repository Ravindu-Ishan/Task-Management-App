package com.example.taskmanagementapp.dao

import androidx.room.*
import com.example.taskmanagementapp.models.Task
@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task):Long
}