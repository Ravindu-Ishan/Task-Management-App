package com.example.taskmanagementapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.invoke.TypeDescriptor
import java.util.Date

@Entity()
data class Task(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="taskID")
    val id:String,
    @ColumnInfo(name = "taskTitle")
    val title:String,
    val description:String,
    val date: Date
)
