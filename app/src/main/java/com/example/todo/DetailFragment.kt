package com.example.todo

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailFragment : Fragment() {

    private lateinit var detailRecyclerView: RecyclerView
    private lateinit var dataManager: ListDataManager
    private lateinit var list: TaskList
    private lateinit var detailFab: FloatingActionButton
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataManager = ViewModelProvider(this).get(ListDataManager::class.java)

//        list = requireArguments().getParcelable(TodoListFragment.TODO_BUNDLE_KEY)!!
        requireArguments().also {
//          args = DetailFragmentArgs.fromBundle(it)
            val name = args.detailListName
            list = dataManager.readList().filter { list -> list.name == name }[0]
        }

        activity?.findViewById<Toolbar>(R.id.toolbar)?.title = list.name
    }

    //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailFab = view.findViewById(R.id.detailFloatingActionButton)
        detailFab.setOnClickListener {
            addDetailTaskDialog()
        }

        activity?.also {
            detailRecyclerView = view.findViewById(R.id.detail_task_recycler)
            detailRecyclerView.layoutManager = LinearLayoutManager(it)
            detailRecyclerView.adapter = DetailTaskAdapter(list)
        }
    }

    private fun addItem(text: String) {
        (detailRecyclerView.adapter as DetailTaskAdapter).addItem(text)
    }

    private fun addDetailTaskDialog() {
        activity?.also {
            val editText = EditText(it)
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            val dialogTitle = getString(R.string.detailTaskAdd, list.name)
            AlertDialog.Builder(it).setTitle(dialogTitle).setView(editText)
                .setPositiveButton(R.string.detailDialogfabButton) { dialog, _ ->
                    val text = editText.text.toString()
                    if (taskNotAlreadyExist(text)) {
                        addItem(text)
                        dataManager.saveList(list)
                    }
                    dialog.dismiss()
                }.create().show()
        }
    }

    private fun taskNotAlreadyExist(task: String): Boolean {
        val ans = (detailRecyclerView.adapter as DetailTaskAdapter).taskNotAlreadyExist(task)
        if (!ans)
            Toast.makeText(requireContext(), "A Task With The Name \"$task\" Already Exists!", Toast.LENGTH_SHORT).show()
        return ans
    }
}