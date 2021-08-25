package com.example.myapplication.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.myapplication.R
import com.example.myapplication.WebActivity
import com.example.myapplication.adapter.BannerImageAdapter
import com.example.myapplication.adapter.HomeMarketAdapter
import com.example.myapplication.adapter.HomeNewsAdapter
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.bean.BannerBean
import com.example.myapplication.bean.FiveHundredMScd
import com.example.myapplication.bean.NewsBean
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.utils.GlobalValue.FRESH_INTERVAL
import com.example.myapplication.viewmodel.ShareViewModel
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var shareViewModel: ShareViewModel
    //private lateinit var wanAndroidBannerImageAdapter: WanAndroidBannerImageAdapter
    private lateinit var bannerAdapter: BannerImageAdapter

    //private var wanAndroidImageList: MutableList<WanAndroidBannerBean> = mutableListOf()
    private var imageList: MutableList<BannerBean> = mutableListOf()

    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        //netViewModel = ViewModelProvider(this).get(NetViewModel::class.java)
        shareViewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
        initBanner()
        initNews()
        initMarket()
    }

    @ObsoleteCoroutinesApi
    private fun initMarket() {
        val tickerChannel = ticker(delayMillis = FRESH_INTERVAL, initialDelayMillis = 0)

        lifecycleScope.launchWhenResumed {
            for (event in tickerChannel) {
                homeViewModel.getMarketData()
            }
        }

        val homeMarketAdapter = HomeMarketAdapter()
        binding.recyclerMarket.adapter = homeMarketAdapter
        binding.recyclerMarket.layoutManager = LinearLayoutManager(
            context,
            GridLayoutManager.VERTICAL,
            false
        )

        homeViewModel.marketData.observe(viewLifecycleOwner, Observer {
            val optionalList: MutableList<FiveHundredMScd> = HomeUtils.
            dealWithMarketData(it)
            homeMarketAdapter.setNewInstance(optionalList)
        })
    }

    /*
    * 初始化主界面的banner
    */
    private fun initBanner() {
        val banner = binding.banner
        homeViewModel.getBanner()

        bannerAdapter = BannerImageAdapter(requireActivity(), imageList)
        banner.adapter = bannerAdapter

        /*wanAndroidBannerImageAdapter = WanAndroidBannerImageAdapter(requireActivity(), wanAndroidImageList)
        banner.adapter = wanAndroidBannerImageAdapter
        homeViewModel.wanAndroidBanners.observe(viewLifecycleOwner, Observer {
            wanAndroidImageList = it as MutableList<WanAndroidBannerBean>
            bannerAdapter.updateData(wanAndroidImageList)
            banner.setLoopTime(5000)
            banner.indicator = CircleIndicator(requireContext())
            binding.textHome.text = it.toString()
            initFlipper()
        })*/
        homeViewModel.banners.observe(viewLifecycleOwner, Observer {
            imageList = it as MutableList<BannerBean>
            bannerAdapter.updateData(imageList)
            banner.setLoopTime(5000)
            banner.indicator = CircleIndicator(requireContext())
            //binding.textHome.text = it.toString()
            initFlipper()
        })
        banner.setOnBannerListener { data, _ ->
            goToWebViewActivity(
                (data as BannerBean).url,
                data.title
            )
        }
    }

    /*
   * 初始化主界面的新闻
   */
    private fun initNews() {
        val mHomeNewsAdapter = HomeNewsAdapter()
        binding.recyclerNews.layoutManager = LinearLayoutManager(
            context,
            GridLayoutManager.VERTICAL,
            false
        )

        binding.recyclerNews.adapter = mHomeNewsAdapter

        shareViewModel.getNews()
        shareViewModel.shareNewsData.observe(viewLifecycleOwner, Observer {
            val list: MutableList<NewsBean> = mutableListOf()
            val mBaseNewsBean = it
            if (!TextUtils.isEmpty(mBaseNewsBean[0].cover)) {
                list.add(mBaseNewsBean[0])
                list.addAll(mBaseNewsBean[0].multi_list)
            }
            mHomeNewsAdapter.setNewInstance(list)
            mHomeNewsAdapter.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>, _, position: Int ->
                val item: NewsBean = adapter.data[position] as NewsBean
                goToWebViewActivity(item.url, item.title)
            }
        })
    }

    @SuppressLint("InflateParams", "CutPasteId")
    private fun initFlipper() {
       /* for (i in wanAndroidImageList.indices) {
            val view: View = this.layoutInflater.inflate(R.layout.item_flipper, null)
            (view.findViewById<View>(R.id.tv_message) as TextView).text = wanAndroidImageList[i].title
            view.findViewById<View>(R.id.tv_message).tag = wanAndroidImageList[i]
            view.findViewById<View>(R.id.tv_message).setOnClickListener { v ->
                goToWebViewActivity(
                    (v.tag as WanAndroidBannerBean).url,
                    (v.tag as WanAndroidBannerBean).title
                )
            }
            binding.viewflipper.addView(view)
        } */
        for (i in imageList.indices) {
            val view: View = this.layoutInflater.inflate(R.layout.item_flipper, null)
            (view.findViewById<View>(R.id.tv_message) as TextView).text = imageList[i].title
            view.findViewById<View>(R.id.tv_message).tag = imageList[i]
            view.findViewById<View>(R.id.tv_message).setOnClickListener { v ->
                goToWebViewActivity(
                    (v.tag as BannerBean).url,
                    (v.tag as BannerBean).title
                )
            }
            binding.viewflipper.addView(view)
        }
        binding.viewflipper.startFlipping()
    }

    private fun goToWebViewActivity(url: String, title: String) {
        val intent = Intent(activity, WebActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", url)
        bundle.putString("title", title)
        intent.putExtras(bundle)
        startActivity(intent)
    }



}