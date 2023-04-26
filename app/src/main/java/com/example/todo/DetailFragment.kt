package com.example.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailFragment : Fragment() {

    private lateinit var detailRecyclerView: RecyclerView
    private lateinit var list: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list = arguments?.getParcelable(DETAIL_ARG_KEY)!!
        activity?.title = list.name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailRecyclerView = view.findViewById(R.id.detail_task_recycler)
        detailRecyclerView.layoutManager = LinearLayoutManager(activity)
        detailRecyclerView.adapter = DetailTaskAdapter(list)
    }

//    @Suppress("DEPRECATION")
//    override fun onAttach(context: Context) {
//        super.onAttach(context)

//        list =
//            activity?.intent?.getParcelableExtra<TaskList>(MainActivity.INTENT_LIST_KEY) as TaskList
//        requireActivity().title = list.name
//    }

    fun getDialogTitle(): String = getString(R.string.detailTaskAdd, list.name)

    fun addItem(text: String) {
        (detailRecyclerView.adapter as DetailTaskAdapter).addItem(text)
    }

    fun returnResult() {
        val intent = Intent()
        intent.putExtra(MainActivity.INTENT_LIST_KEY, list)
        activity?.setResult(Activity.RESULT_OK, intent)
    }

    companion object {
        private const val DETAIL_ARG_KEY = "key for detail fragment argument"

        fun newInstance(list: TaskList) : DetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(DETAIL_ARG_KEY, list)
            val detailFrag = DetailFragment()
            detailFrag.arguments = bundle
            return detailFrag
        }
    }
}