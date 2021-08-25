package com.example.myapplication.ui.news

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.WebActivity
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.bean.NewsBean
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.viewmodel.ShareViewModel

class NewsFragment : BaseFragment<FragmentNewsBinding>(), SwipeRefreshLayout.OnRefreshListener {

    //private lateinit var nwesViewModel: NewsViewModel
    private lateinit var shareViewMode : ShareViewModel
    private val informationNewsAdapter = NewsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareViewMode = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
        shareViewMode.getNews()
        initView()
        shareViewMode.shareNewsData.observe(viewLifecycleOwner, Observer {
            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
            }
            informationNewsAdapter.data.clear()
            informationNewsAdapter.setNewInstance(it as MutableList<NewsBean>?)
        })

    }

    private fun initView() {
        binding.recycler.adapter = informationNewsAdapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        informationNewsAdapter.setOnItemChildClickListener { adapter: BaseQuickAdapter<Any?, BaseViewHolder>, view: View, i: Int ->
            when (view.id) {
                R.id.newsCoverImg -> {
                    val intent = Intent(context, WebActivity::class.java)
                    val item = adapter.data[i] as NewsBean
                    intent.putExtra("url", item.url)
                    intent.putExtra("title", item.title)
                    startActivity(intent)
                }
            }
        }
        binding.swipeRefresh.setColorSchemeColors(
            Color.parseColor("#FF00BAFF"),
            Color.parseColor("#e1e1e1")
        )
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        shareViewMode.getNews()
    }
}

