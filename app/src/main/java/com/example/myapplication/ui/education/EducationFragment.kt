package com.example.myapplication.ui.education

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.bean.EduTabBean
import com.example.myapplication.databinding.FragmentEducationBinding
import com.example.myapplication.adapter.EducationFragmentListAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class EducationFragment : BaseFragment<FragmentEducationBinding>() {

    private lateinit var educationViewModel: EducationViewModel
    private lateinit var educationFragmentListAdapter: EducationFragmentListAdapter
    private val mFragmentList = ArrayList<Fragment>()
    private var mTabDataLists = ArrayList<Map<String, String>>()
    private var mTitleMap: MutableMap<String, String> = mutableMapOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        educationViewModel = ViewModelProvider(this).get(EducationViewModel::class.java)
        educationViewModel.getTabs()
        initView()
        educationViewModel.tabs.observe(viewLifecycleOwner, Observer {

            val list: List<EduTabBean> = it
            for (item: EduTabBean in list) {
                val map = mutableMapOf(
                    "title" to item.title,
                    "fileName" to item.file_name
                )
                mTabDataLists.add(map)
            }
            if (mTabDataLists.size > 4) {
                binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            } else {
                binding.tabLayout.tabMode = TabLayout.MODE_FIXED
            }
            //mTabDataLists中投教平台已经加载过，所以i从1开始
            //mTabDataLists中投教平台已经加载过，所以i从1开始
            (1 until mTabDataLists.size).forEach { i ->
                val tab: TabLayout.Tab =
                    binding.tabLayout.newTab().setText(mTabDataLists[i]["title"])
                binding.tabLayout.addTab(tab)
                if (!TextUtils.isEmpty(mTabDataLists[i]["fileName"])) {
                    val tag = mTabDataLists[i]["fileName"]
                    val item = EducationItemFragment.getInstance(tag)
                    mFragmentList.add(item)
                }
            }
            educationFragmentListAdapter.updateData(mFragmentList)
            TabLayoutMediator(
                binding.tabLayout,
                binding.myViewPage
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = mTabDataLists[position]["title"]
                tab.customView = getTabView(position)
            }.attach()
        })

    }

    private fun initView() {
        mTabDataLists = ArrayList()
        //先加载投教平台
        val tag = "investor_education.json"
        mTitleMap["title"] = resources.getString(R.string.education_platform)
        mTitleMap["file_name"] = tag
        mTabDataLists.add(mTitleMap)

        val item: EducationItemFragment = EducationItemFragment.getInstance(tag)
        mFragmentList.add(item as Fragment)
        educationFragmentListAdapter = EducationFragmentListAdapter(
            requireActivity(),
            mFragmentList
        )
        binding.myViewPage.adapter = educationFragmentListAdapter
        //设置标签的文字显示和自定义CustomView
        //设置标签的文字显示和自定义CustomView
        TabLayoutMediator(
            binding.tabLayout,
            binding.myViewPage
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(R.string.education_platform)
            tab.customView = getTabView(position)
        }.attach()
    }


    @SuppressLint("InflateParams")
    private fun getTabView(currentPosition: Int): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.tab_text, null)
        val textView = view.findViewById<TextView>(R.id.tab_item_textview)
        textView.text = mTabDataLists[currentPosition]["title"]
        return view
    }
}

