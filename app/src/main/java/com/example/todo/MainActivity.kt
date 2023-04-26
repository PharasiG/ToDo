package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TodoListFragment.OnFragmentInteractionListener {

    private lateinit var binding: ActivityMainBinding
    private var todoListFragment = TodoListFragment.newInstance()
    companion object {
        const val INTENT_LIST_KEY = "list"
        const val DETAIL_REQUEST_CODE = 223
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportFragmentManager.beginTransaction()
            .add(R.id.todoFragmentContainer, todoListFragment)
            .commit()

        binding.fab.setOnClickListener {
            addItemDialog()
        }
    }

    private fun addItemDialog() {
        val dialogTitle = getString(R.string.dialog_title)
        val positiveButtonText = getString(R.string.dialog_title)
        val editView = EditText(this)
        editView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        val dialog =  AlertDialog.Builder(this)
        dialog.setView(editView)
        dialog.setTitle(dialogTitle)
        dialog.setPositiveButton(positiveButtonText) { it, _->
            val task = TaskList(editView.text.toString())
            todoListFragment.addList(task)
            it.dismiss()
            showDetailTaskList(task)
        }
        dialog.create().show()
    }

    @Suppress("DEPRECATION")
    private fun showDetailTaskList(task : TaskList) {
        val detailTaskListIntent = Intent(this, DetailTasks::class.java)
        detailTaskListIntent.putExtra(INTENT_LIST_KEY, task)
        startActivityForResult(detailTaskListIntent, DETAIL_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DETAIL_REQUEST_CODE) {
            data?.also {
                val list = data.getParcelableExtra<TaskList>(INTENT_LIST_KEY)!!
                todoListFragment.saveList(list)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onToDoListClicked(list: TaskList) {
        showDetailTaskList(list)
    }
}
