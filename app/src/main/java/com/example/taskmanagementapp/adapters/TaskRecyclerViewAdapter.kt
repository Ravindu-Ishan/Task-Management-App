package com.example.taskmanagementapp.adapters

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.example.taskmanagementapp.R
import com.example.taskmanagementapp.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRecyclerViewAdapter(
    private val deleteUpdateCallback: (type:String, position:Int, task: Task) -> Unit
):RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    private val taskList = arrayListOf<Task>()


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleText : TextView = itemView.findViewById(R.id.tsktitle)
        val descText : TextView = itemView.findViewById(R.id.tskdsc)
        val taskDate : TextView = itemView.findViewById(R.id.tskdate)

        val deleteBtn : ImageButton = itemView.findViewById(R.id.deleteTaskbtn)

        val editBtn : ImageButton = itemView.findViewById(R.id.editTaskBtn)
    }

    fun addAllTask(newTaskList: List<Task>){
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a" , Locale.getDefault())

        holder.titleText.text = task.title
        holder.descText.text = task.description

        holder.taskDate.text = dateFormat.format(task.date)

        holder.deleteBtn.setOnClickListener{
            if (holder.adapterPosition != -1){
                deleteUpdateCallback("delete",holder.adapterPosition,task)
            }
        }

        holder.editBtn.setOnClickListener{
            if (holder.adapterPosition != -1){
                deleteUpdateCallback("update",holder.adapterPosition,task)
            }
        }



    }



    override fun getItemCount(): Int {
        return  taskList.size
    }

}