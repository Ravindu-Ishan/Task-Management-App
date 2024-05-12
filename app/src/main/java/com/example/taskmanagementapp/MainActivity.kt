package com.example.taskmanagementapp
import com.example.taskmanagementapp.utils.setupDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagementapp.adapters.TaskRecyclerViewAdapter
import com.example.taskmanagementapp.databinding.ActivityMainBinding
import com.example.taskmanagementapp.models.Task
import com.example.taskmanagementapp.utils.Status
import com.example.taskmanagementapp.utils.longToastShow
import com.example.taskmanagementapp.viewmodels.TaskViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val mainBinding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val addTaskDialog:Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply{
            setupDialog(R.layout.add_task_layout)
        }
    }

    private val updateTaskDialog:Dialog by lazy {
        Dialog(this,R.style.DialogCustomTheme).apply{
            setupDialog(R.layout.update_task_layout)
        }
    }

    private val loadingTaskDialog:Dialog by lazy {
        Dialog(this,R.style.DialogCustomTheme).apply{
            setupDialog(R.layout.loading_layout)
        }
    }

    private val taskViewModel: TaskViewModel by lazy{
        ViewModelProvider(this)[TaskViewModel::class.java]
    }

    private val taskRecyclerViewAdapter : TaskRecyclerViewAdapter by lazy{
        TaskRecyclerViewAdapter{position, task ->
            taskViewModel.deleteTaskById(task.id).observe(this){
                when(it.status){
                    Status.LOADING->{
                        loadingTaskDialog.show()
                    }
                    Status.SUCCESS->{
                        loadingTaskDialog.dismiss()
                        if(it.data != -1)
                        {
                            longToastShow(msg ="Task Has Deleted")
                        }
                    }
                    Status.ERROR->{
                        loadingTaskDialog.dismiss()
                        it.message?.let { it1 -> longToastShow(it1) }
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.tskRcView.adapter = taskRecyclerViewAdapter
        mainBinding.tskRcView.layoutManager = LinearLayoutManager(this)  // Or other layout manager if needed


        callGetTaskList()

        val addCloseBtn = addTaskDialog.findViewById<ImageButton>(R.id.newTaskCloseBtn)
        val updateCloseBtn = updateTaskDialog.findViewById<ImageButton>(R.id.updateCloseBtn)

        //new task save button
        val saveNewTaskBtn = addTaskDialog.findViewById<Button>(R.id.saveTaskBtn)
        val updateTaskBtn = updateTaskDialog.findViewById<Button>(R.id.updateTaskBtn)

        //new task inputs
        val newTitleInput = addTaskDialog.findViewById<TextInputEditText>(R.id.taskTitleInput)
        val newDescInput = addTaskDialog.findViewById<TextInputEditText>(R.id.taskDescInput)

        //update task inputs
        val updateTitleInput = updateTaskDialog.findViewById<TextInputEditText>(R.id.updateTaskTitleInput)
        val updateDescInput = updateTaskDialog.findViewById<TextInputEditText>(R.id.updateTaskDescInput)


        addCloseBtn.setOnClickListener{
            addTaskDialog.dismiss()
        }
        updateCloseBtn.setOnClickListener{
            updateTaskDialog.dismiss()
        }

        mainBinding.addNewTaskFloatingBtn.setOnClickListener{
            addTaskDialog.show()
            newTitleInput.text?.clear()
            newDescInput.text?.clear()
        }

        saveNewTaskBtn.setOnClickListener{
            val taskTitle = newTitleInput.text.toString()
            val taskDesc = newDescInput.text.toString()

            //val toastMessage = "Set : $taskTitle - $taskDesc"  // Use string templates for cleaner formatting
            //val toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT)
            //toast.show()

            //save task
            addTaskDialog.dismiss()
            val newTask = Task(
                UUID.randomUUID().toString(),
                taskTitle.toString().trim(),
                taskDesc.toString().trim(),
                Date()
            )
            taskViewModel.insertTask(newTask).observe(this){
                when(it.status){
                    Status.LOADING->{
                        loadingTaskDialog.show()
                    }
                    Status.SUCCESS->{
                        loadingTaskDialog.dismiss()
                        if(it.data?.toInt() != -1)
                        {
                            longToastShow(msg ="Task Has Been Created")
                        }
                    }
                    Status.ERROR->{
                        loadingTaskDialog.dismiss()
                        it.message?.let { it1 -> longToastShow(it1) }
                    }
                }
            }
        }

        updateTaskBtn.setOnClickListener{
            //val taskTitle = updateTitleInput.text.toString()
            //val taskDesc = updateDescInput.text.toString()

            loadingTaskDialog.show()

            //update task

        }



    }

    private fun callGetTaskList(){
        loadingTaskDialog.show()
        CoroutineScope(Dispatchers.Main).launch {
            taskViewModel.getTaskList().collect {
                when (it.status) {
                    Status.LOADING -> {
                        loadingTaskDialog.show()
                    }
                    Status.SUCCESS -> {
                        loadingTaskDialog.dismiss()
                        it.data?.collect {taskList ->
                            loadingTaskDialog.dismiss()
                            taskRecyclerViewAdapter.addAllTask(taskList)
                        }
                    }
                    Status.ERROR -> {
                        loadingTaskDialog.dismiss()
                        it.message?.let { it1 -> longToastShow(it1) }
                    }
                }
            }
        }
    }
}