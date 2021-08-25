package com.example.myapplication.ui.education

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.adapter.EducationItemAdapter
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.bean.EduItemBean
import com.example.myapplication.databinding.FragmentEducatuonitemBinding


/**
 * 类名： EducationItemFragment
 * 功能说明：投教内容以及每个item fragment
 * 作者：cpf
 */

class EducationItemFragment : BaseFragment<FragmentEducatuonitemBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    private val mEducationItemAdapter = EducationItemAdapter()
    private var mTag = ""
    private lateinit var educationItemViewModel: EducationItemViewModel

    companion object {

        fun getInstance(tag: String?): EducationItemFragment {
            val args = Bundle()
            args.putString("args", tag)
            val fragment = EducationItemFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTag = arguments?.getString("args").toString()
        educationItemViewModel = ViewModelProvider(this).get(EducationItemViewModel::class.java)
        educationItemViewModel.itemData.observe(viewLifecycleOwner, Observer {
            if (binding.swipRefresh.isRefreshing) {
                binding.swipRefresh.isRefreshing = false
            }
            mEducationItemAdapter.data.clear()
            mEducationItemAdapter.setNewInstance(it as MutableList<EduItemBean>?)
        })
        binding.swipRefresh.setColorSchemeColors(
            Color.parseColor("#FF00BAFF"),
            Color.parseColor("#e1e1e1")
        )
        binding.swipRefresh.setOnRefreshListener(this)
        init()
    }


    private fun init() {
        binding.recycler.adapter = mEducationItemAdapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        //获取投教内容列表
        if (mTag != "") {
            educationItemViewModel.getTopicData(mTag)
        }
    }

    override fun onRefresh() {
        if (mTag != "") {
            educationItemViewModel.getTopicData(mTag)
        }
    }
}

/*mEducationItemAdapter.setOnItemClickListener { _, _, i ->
    selectPosition = i
    val item = mEducationItemAdapter.getItem(i)
    val intent = Intent(context, WebViewActivity::class.java)
    intent.putExtra(Constants.TITLE, item?.title)
    intent.putExtra("url", item?.url)
    startActivity(intent)
    this.activity?.overridePendingTransition(
        R.anim.slide_right_in,
        R.anim.slide_left_out
    )
}
*/