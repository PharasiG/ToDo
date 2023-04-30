package com.example.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager

class ListDataManager(application: Application) : AndroidViewModel(application) {
//class ListDataManager(private val context: Context) {

    fun readList(): ArrayList<TaskList> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
        val lists = ArrayList<TaskList>()
        val data = sharedPrefs.all
        for (content in data) {
            val items = ArrayList(content.value as HashSet<String>)
            val list = TaskList(content.key, items)
            lists.add(list)
        }
        return lists
    }

    fun saveList(list: TaskList) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplication()).edit()
        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
        sharedPrefs.apply()
    }
}


