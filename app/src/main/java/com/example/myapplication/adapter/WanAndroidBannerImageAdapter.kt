package com.example.myapplication.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.WanAndroidBannerImageAdapter.BannerViewHolder
import com.example.myapplication.bean.WanAndroidBannerBean
import com.example.myapplication.utils.GlideUtil
import com.youth.banner.adapter.BannerAdapter

class WanAndroidBannerImageAdapter(val mContext: Context, data: MutableList<WanAndroidBannerBean>?) : BannerAdapter<WanAndroidBannerBean, BannerViewHolder>(data) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: WanAndroidBannerBean?,
        position: Int,
        size: Int
    ) {
        if ("" != data!!.imagePath) {
            GlideUtil.getInstance().loadUrlImage(mContext, data.imagePath, holder!!.imageView, true)
        }
    }

    fun updateData(list: MutableList<WanAndroidBannerBean>){
        this.mDatas.clear()
        this.mDatas.addAll(list)
        this.notifyDataSetChanged()
    }

    inner class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)

}