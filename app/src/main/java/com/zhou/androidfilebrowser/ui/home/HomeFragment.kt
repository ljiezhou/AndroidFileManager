package com.zhou.androidfilebrowser.ui.home

import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhou.androidfilebrowser.R
import com.zhou.androidfilebrowser.app.Const
import com.zhou.androidfilebrowser.filemanager.FileBean
import com.zhou.androidfilebrowser.filemanager.task.LoadFilesListTask
import com.zhou.androidfilebrowser.uitl.LogcatUtil
import com.zhou.androidfilebrowser.uitl.ToastUtil
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var homeViewModel: HomeViewModel? = null
    private var loadFilesListTask: LoadFilesListTask? = null
    private var path: String? = null
    private val fileBeans: MutableList<FileBean> = ArrayList()
    private var homeAdapter: HomeAdapter? = null

    companion object {
        fun newInstance(currentPath: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString(Const.CURRENT_PATH, currentPath)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            path = arguments?.getString(Const.CURRENT_PATH)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        home_recyclerview.layoutManager = LinearLayoutManager(activity)
        homeAdapter = HomeAdapter(fileBeans as java.util.ArrayList<FileBean>?)
        home_recyclerview.adapter = homeAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel?.text?.observe(this, Observer {
        })

        loadDirectory()
        return root
    }

    fun loadDirectory() {
        if (loadFilesListTask != null)
            loadFilesListTask?.cancel(true)
        if (TextUtils.isEmpty(path)) {
            ToastUtil.getInstance().toastShowS("传入的路径为空")
            return
        }
        loadFilesListTask = LoadFilesListTask(path)
        loadFilesListTask?.setLoadFilesListListener {
            LogcatUtil.d("文件数量：" + it.size)
            fileBeans.clear()
            fileBeans.addAll(it)
            homeAdapter?.notifyDataSetChanged()
        }
        loadFilesListTask?.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}