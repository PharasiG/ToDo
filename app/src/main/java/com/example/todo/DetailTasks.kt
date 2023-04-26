package com.example.todo

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivityDetailTasksBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailTasks : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTasksBinding
    private lateinit var detailFab: FloatingActionButton
    private lateinit var detailFragment: DetailFragment
//    private var dataManager: ListDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailFragment =
            DetailFragment.newInstance(intent.getParcelableExtra<TaskList>(MainActivity.INTENT_LIST_KEY) as TaskList)

        supportFragmentManager.beginTransaction()
            .add(R.id.detailFragmentContainer, detailFragment)
            .commit()

        detailFab = binding.detailFloatingActionButton

//        list.tasks = dataManager.readTasks(list.name)

        detailFab.setOnClickListener {
            addDetailTaskDialog()
        }

    }

    private fun addDetailTaskDialog() {
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
        val dialogTitle = detailFragment.getDialogTitle()
        AlertDialog.Builder(this).setTitle(dialogTitle).setView(editText)
            .setPositiveButton(R.string.detailDialogfabButton) { dialog, _ ->
                val text = editText.text.toString()
                detailFragment.addItem(text)
                dialog.dismiss()
            }.create().show()
    }

//    override fun onBackPressed() {
//        dataManager.saveTask(list)
//        super.onBackPressed()
//    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
//        val bundle = Bundle()
//        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)
//        intent.putExtras(bundle)
        detailFragment.returnResult()
        super.onBackPressed()
    }
}