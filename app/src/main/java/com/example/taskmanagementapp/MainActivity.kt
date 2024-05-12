package com.example.taskmanagementapp
import com.example.taskmanagementapp.utils.setupDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.taskmanagementapp.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

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
        }

        saveNewTaskBtn.setOnClickListener{
            val taskTitle = newTitleInput.text.toString()
            val taskDesc = newDescInput.text.toString()

//            val toastMessage = "Set : $taskTitle - $taskDesc"  // Use string templates for cleaner formatting
//            val toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT)
//            toast.show()

            //save task

        }

        updateTaskBtn.setOnClickListener{
            val taskTitle = updateTitleInput.text.toString()
            val taskDesc = updateDescInput.text.toString()

//            val toastMessage = "Set : $taskTitle - $taskDesc"  // Use string templates for cleaner formatting
//            val toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT)
//            toast.show()

            //save task

        }







    }
}