package com.example.myapplication.ui.home

import com.example.myapplication.bean.FiveHundredMScd
import java.util.*

object HomeUtils {

    fun dealWithMarketData(it: List<String>): MutableList<FiveHundredMScd> {
        val array: List<String> = it
        val optionalList: MutableList<FiveHundredMScd> = mutableListOf()

        for (index in 0 until array.size) {
            val acc = FiveHundredMScd()
            val jItem: String = array[index]
            val scv = jItem.split(",").toTypedArray()
            if (scv.size != 26) {
                continue
            }
            acc.tradingday = scv[0]
            acc.lastprice = scv[1]
            acc.presettlementprice = scv[2]
            acc.precloseprice = scv[3]
            acc.preopeninterest = scv[4]
            acc.openprice = scv[5]
            acc.highestprice = scv[6]
            acc.lowestprice = scv[7]
            acc.volume = scv[8]
            acc.turnover = scv[9]
            acc.openinterest = scv[10]
            acc.settlementprice = scv[11]
            acc.upperlimitprice = scv[12]
            acc.lowerlimitprice = scv[13]
            acc.updatetime = scv[14]
            acc.instrumentid = scv[15].toLowerCase(Locale.ROOT)
            acc.instrumentName = scv[16]
            acc.bidprice1 = scv[17]
            acc.bidvolume1 = scv[18]
            acc.askvolume1 = scv[19]
            acc.askprice1 = scv[20]
            acc.amplitude = scv[21]
            acc.turnoverrate = scv[22]
            acc.updown = scv[23]
            acc.updownper = scv[24]
            acc.volumechange = scv[25]
            optionalList.add(acc)
        }
        return optionalList
    }
}