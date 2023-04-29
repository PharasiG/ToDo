package com.example.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailTasks : AppCompatActivity() {

    private lateinit var detailFragment: DetailFragment
//    private var dataManager: ListDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail_tasks)

        detailFragment =
            @Suppress("DEPRECATION")
            DetailFragment.newInstance(intent.getParcelableExtra<TaskList>(MainActivity.INTENT_LIST_KEY) as TaskList)

        supportFragmentManager.beginTransaction()
            .add(R.id.detailFragmentContainer, detailFragment)
            .commit()

//        list.tasks = dataManager.readTasks(list.name)
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