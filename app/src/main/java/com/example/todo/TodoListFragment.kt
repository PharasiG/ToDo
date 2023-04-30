package com.example.todo

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R.layout
import com.example.todo.R.string
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {

    private lateinit var todoList: RecyclerView
    private lateinit var dataManager: ListDataManager
    private lateinit var fab: FloatingActionButton

//    companion object {
//        val TODO_BUNDLE_KEY = "Pass"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataManager = ViewModelProvider(this).get(ListDataManager::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.also {
            val lists = dataManager.readList()

            todoList = view.findViewById(R.id.todoRecyclerView)
            todoList.layoutManager = LinearLayoutManager(activity)
            todoList.adapter = TodoListAdapter(lists, this)
        }

        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            addItemDialog()
        }
    }

    override fun listItemClicked(list: TaskList) {
        showDetailTaskList(list)
    }

    private fun showDetailTaskList(list: TaskList) {
//        val bundle = Bundle()
//        bundle.putParcelable("Pass", list)
        val action = TodoListFragmentDirections.actionTodoListFragmentToDetailFragment2(list.name)
        requireView().also { it.findNavController().navigate(action) }
//        view?.also { it.findNavController().navigate(R.id.action_todoListFragment_to_detailFragment2, bundle) }
    }

    private fun addList(task: TaskList) {
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
            val dialogTitle = getString(string.dialog_title)
            val positiveButtonText = getString(string.dialog_title)
            val editView = EditText(it)
            editView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

            val dialog = AlertDialog.Builder(it)
            dialog.setView(editView)
            dialog.setTitle(dialogTitle)
            dialog.setPositiveButton(positiveButtonText) { dialg, _ ->
                val text = editView.text.toString()
                if (listNotAlreadyExist(text)) {
                    val task = TaskList(text)
                    addList(task)
                    showDetailTaskList(task)
                }
                dialg.dismiss()

            }
            dialog.create().show()
        }
    }

    private fun listNotAlreadyExist(list: String): Boolean {
        val ans = (todoList.adapter as TodoListAdapter).listNotAlreadyExist(list)
        if (!ans)
            Toast.makeText(requireContext(), "A List With The Name \"$list\" Already Exists!", Toast.LENGTH_SHORT).show()
        return ans
    }
}