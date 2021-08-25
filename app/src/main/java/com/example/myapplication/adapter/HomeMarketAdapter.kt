package com.example.myapplication.adapter

import android.graphics.Color
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.bean.FiveHundredMScd
import com.example.myapplication.utils.GlobalValue
import com.example.myapplication.utils.StringUtils
import java.text.DecimalFormat

class HomeMarketAdapter :
    BaseQuickAdapter<FiveHundredMScd, BaseViewHolder>(R.layout.item_home_market, null) {
    override fun convert(holder: BaseViewHolder, item: FiveHundredMScd) {
        if (data.size > 0) {
            holder.setText(
                R.id.tv_instrumentid,
                StringUtils.toUpperOptionInstrument(item.instrumentid)
            )
            holder.setText(R.id.tv_volume, "量" + calcFloat(item.volume))
            holder.setText(
                R.id.tv_openinterest,
                "仓" + calcFloat(item.openinterest.split("\\.").toTypedArray()[0])
            )
            holder.setText(R.id.tv_updown, item.updownper + "%")
            holder.setText(R.id.tv_lastprice, item.lastprice)
            if (item.updown.contains("--") || item.updown.toFloat() == 0f
            ) {
            } else if (item.updown.toFloat() < 0) {
                holder.setTextColor(R.id.tv_lastprice, GlobalValue.DOWN_COLOR)
                holder.setTextColor(R.id.tv_updown, GlobalValue.DOWN_COLOR)
            } else {
                holder.setTextColor(R.id.tv_lastprice, GlobalValue.UP_COLOR)
                holder.setTextColor(R.id.tv_updown, GlobalValue.UP_COLOR)
            }
            /*if (CommonValue.instrumentMap != null && CommonValue.instrumentMap.get(menuItem.getInstrumentid()) != null) {
                baseViewHolder.setText(R.id.tv_name, Objects.requireNonNull(CommonValue.instrumentMap.get(menuItem.getInstrumentid())).getInstrumentName());
            } else {
                baseViewHolder.setText(R.id.tv_name, StringUtils.toUpperOptionInstrument(menuItem.getInstrumentid()));
            }*/holder.setText(
                R.id.tv_name,
                StringUtils.toUpperOptionInstrument(item.instrumentid)
            )
            when (holder.layoutPosition % 3) {
                0 -> holder.setBackgroundColor(R.id.ll_bg, Color.parseColor("#F3EEF4"))
                1 -> holder.setBackgroundColor(R.id.ll_bg, Color.parseColor("#ECF3FD"))
                2 -> holder.setBackgroundColor(R.id.ll_bg, Color.parseColor("#EAF2F2"))
            }
        }
    }

    private fun calcFloat(value: String): String {
        if (TextUtils.isEmpty(value) || "--" == value) {
            return value
        }
        if (value.toFloat() / 10000 > 10 && value.toFloat() / 10000 < 100) {
            val df = DecimalFormat("#.00")
            return df.format((value.toFloat() / 10000).toDouble()) + "万"
        } else if (value.toFloat() / 10000 > 100 && value.toFloat() / 10000 < 1000) {
            val df = DecimalFormat("#.0")
            return df.format((value.toFloat() / 10000).toDouble()) + "万"
        } else if (value.toFloat() / 10000 > 1000 && value.toFloat() / 10000 < 10000) {
            val df = DecimalFormat("#")
            return df.format((value.toFloat() / 10000).toDouble()) + "万"
        } else if (value.toFloat() / 10000 > 9999) {
            val df = DecimalFormat("#.000")
            return df.format((value.toFloat() / 10000 / 10000).toDouble()) + "亿"
        }
        return value
    }
}