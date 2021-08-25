package com.example.myapplication.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.bean.NewsBean
import com.example.myapplication.utils.GlideUtil

class HomeNewsAdapter : BaseQuickAdapter<NewsBean, BaseViewHolder>(R.layout.item_home_news, null) {
    override fun convert(holder: BaseViewHolder, item: NewsBean) {
        GlideUtil.getInstance()
            .loadUrlImage(context, item.cover, holder.getView(R.id.iv_cover))
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_date, item.date)
        holder.setText(R.id.tv_authorname, item.origin_name)
        if (holder.adapterPosition == data.size - 1) {
            holder.getView<View>(R.id.divider_line).visibility = View.GONE
        }
    }
}