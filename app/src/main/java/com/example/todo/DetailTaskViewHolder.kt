package com.example.todo

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val detailTask = itemView.findViewById<TextView>(R.id.detail_task)
    val detailTaskNumber: TextView = itemView.findViewById(R.id.detail_task_number)
}