package com.example.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DetailTaskAdapter(private val list: TaskList) : RecyclerView.Adapter<DetailTaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_task_view, parent, false)
        return DetailTaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailTaskViewHolder, position: Int) {
        holder.detailTask.text = list.tasks[position]
        (position + 1).toString().also { holder.detailTaskNumber.text = it }
    }

    override fun getItemCount(): Int = list.tasks.size

    fun addItem(task: String) {
        list.tasks.add(task)
        notifyItemInserted(list.tasks.size - 1)
    }

    fun taskNotAlreadyExist(task: String): Boolean {
        return !list.tasks.contains(task)
    }
}