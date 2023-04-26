package com.example.todo

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val itemNumber = itemView.findViewById<TextView>(R.id.itemNumber)
    val itemString = itemView.findViewById<TextView>(R.id.itemString)
}