package com.example.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {
//bc
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var todoList : RecyclerView
    private lateinit var dataManager: ListDataManager
//bc
    interface OnFragmentInteractionListener {
        fun onToDoListClicked(list: TaskList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lists = dataManager.readList()
        todoList = view.findViewById(R.id.todoRecyclerView)
        todoList.layoutManager = LinearLayoutManager(activity)
        todoList.adapter = TodoListAdapter(lists, this)
    }
//bc
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            dataManager = ListDataManager(context)
        }
    }
//bc
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        fun newInstance() = TodoListFragment()
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onToDoListClicked(list)
    }

    fun addList(task: TaskList) {
        saveList(task)
        val todoListAdapter = todoList.adapter as TodoListAdapter
        todoListAdapter.addItem(task)
    }

    fun saveList(list: TaskList) {
        dataManager.saveList(list)
        updateLists()
    }

    private fun updateLists() {
        val lists = dataManager.readList()
        todoList.adapter = TodoListAdapter(lists, this)
    }
}