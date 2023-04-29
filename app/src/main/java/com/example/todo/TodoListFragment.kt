package com.example.todo

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {
    //bc
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var todoList: RecyclerView
    private lateinit var dataManager: ListDataManager
    private lateinit var fab: FloatingActionButton

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
        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            addItemDialog()
        }
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

    private fun addItemDialog() {
        activity?.also {
            val dialogTitle = getString(R.string.dialog_title)
            val positiveButtonText = getString(R.string.dialog_title)
            val editView = EditText(it)
            editView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

            val dialog = AlertDialog.Builder(it)
            dialog.setView(editView)
            dialog.setTitle(dialogTitle)
            dialog.setPositiveButton(positiveButtonText) { it, _ ->
                val task = TaskList(editView.text.toString())
                addList(task)
                it.dismiss()
//                showDetailTaskList(task)
            }
            dialog.create().show()
        }
    }

}