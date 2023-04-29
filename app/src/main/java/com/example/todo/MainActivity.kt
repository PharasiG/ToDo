package com.example.todo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), TodoListFragment.OnFragmentInteractionListener {

    private var todoListFragment = TodoListFragment.newInstance()

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val DETAIL_REQUEST_CODE = 223
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportFragmentManager.beginTransaction()
            .add(R.id.todoFragmentContain, todoListFragment)
            .commit()
    }

    @Suppress("DEPRECATION")
    fun showDetailTaskList(task : TaskList) {
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

    override fun onToDoListClicked(list: TaskList) {
        showDetailTaskList(list)
    }
}
