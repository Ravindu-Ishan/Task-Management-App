package com.example.taskmanagementapp.utils

import android.app.Dialog
import android.widget.LinearLayout

enum class Status{
    SUCCESS,
    ERROR,
    LOADING
}
fun Dialog.setupDialog(layoutResId:Int){
    setContentView(layoutResId)
    window!!.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    setCancelable(false)
}