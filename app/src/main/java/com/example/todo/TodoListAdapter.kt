package com.example.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(
    private val lists: ArrayList<TaskList>,
    private val clickListener: TodoListClickListener,
) : RecyclerView.Adapter<TodoListViewHolder>() {

    interface TodoListClickListener {
        fun listItemClicked(list: TaskList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_view, parent, false)

        return TodoListViewHolder(view)
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        (position + 1).toString().also { holder.itemNumber.text = it }
        (lists[position].name).also { holder.itemString.text = it }

        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(lists[position])
        }
    }

    fun addItem(item: TaskList) {
        lists.add(item)
        notifyItemInserted(lists.size - 1)
    }

    fun listNotAlreadyExist(task: String): Boolean {
        lists.forEach { if (it.name == task) return false }
        return true
    }
}