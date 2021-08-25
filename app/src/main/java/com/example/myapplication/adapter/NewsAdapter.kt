package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.WebActivity
import com.example.myapplication.bean.NewsBean
import com.example.myapplication.utils.GlideUtil

class NewsAdapter : BaseQuickAdapter<NewsBean, BaseViewHolder>(R.layout.news_list_item, null) {
    override fun convert(holder: BaseViewHolder, item: NewsBean) {
        if ((holder.getView<View>(R.id.contentLayout) as LinearLayout).childCount >= 1) {
            (holder.getView<View>(R.id.contentLayout) as LinearLayout).removeAllViews()
        }
        if (item.multi_list_count > 0) {
            for ((_, cover, _, _, _, _, _, _, _, _, _, title, _, url) in item.multi_list) {
                @SuppressLint("InflateParams") val singleNewsLayout = LayoutInflater.from(context)
                    .inflate(R.layout.item_information_news, null) as LinearLayout
                val titleTxt = singleNewsLayout.findViewById<TextView>(R.id.newsTitle)
                val coverImg = singleNewsLayout.findViewById<ImageView>(R.id.coverImg)
                titleTxt.text = title
                //singleNewsLayout.setTag(news.getUrl());
                if (TextUtils.isEmpty(cover)) {
                    coverImg.visibility = View.GONE
                } else {
                    coverImg.visibility = View.VISIBLE
                    GlideUtil.getInstance().loadUrlImage(context, cover, coverImg)
                }
                (holder.getView<View>(R.id.contentLayout) as LinearLayout).addView(singleNewsLayout)
                singleNewsLayout.setOnClickListener { v: View? ->
                    val intent = Intent(context, WebActivity::class.java)
                    intent.putExtra("url", url)
                    intent.putExtra("title", title)
                    context.startActivity(intent)
                }
            }
        }
        holder.setText(R.id.newsTitle, item.title)
        holder.setText(R.id.updateTime, item.date)
        if (TextUtils.isEmpty(item.cover)) {
            holder.getView<View>(R.id.rl_coverimg).visibility = View.GONE
        } else {
            holder.getView<View>(R.id.rl_coverimg).visibility = View.VISIBLE
            GlideUtil.getInstance()
                .loadUrlImage(context, item.cover, holder.getView(R.id.newsCoverImg), true)
        }
    }

    init {
        addChildClickViewIds(R.id.newsCoverImg)
    }
}