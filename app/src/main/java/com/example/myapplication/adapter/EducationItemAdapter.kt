package com.example.myapplication.adapter

import com.example.myapplication.R
import com.example.myapplication.bean.EduItemBean
import com.example.myapplication.databinding.ItemEducationBinding
import com.example.myapplication.utils.GlideUtil

class EducationItemAdapter : BaseBindingAdapter<ItemEducationBinding, EduItemBean>() {

    override fun convert(holder: VBViewHolder<ItemEducationBinding>, item: EduItemBean) {
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_date, item.date)
        holder.setText(R.id.tv_source, item.origin_name + "/")
        GlideUtil.getInstance().loadUrlImage(context, item.cover, holder.getView(R.id.iv_img), true)
    }
}