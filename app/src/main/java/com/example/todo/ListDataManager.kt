package com.example.todo

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    fun readList(): ArrayList<TaskList> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
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
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
        sharedPrefs.apply()
    }

//    fun readTasks(name: String): ArrayList<String> {
//        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
//        return ArrayList<String>(sharedPrefs.getStringSet(name, HashSet()))
//    }
//
//    fun saveTask(list: TaskList) {
//        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
//        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
//        sharedPrefs.apply()
//    }

}


