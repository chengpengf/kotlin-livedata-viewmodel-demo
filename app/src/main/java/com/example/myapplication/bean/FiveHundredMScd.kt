package com.example.myapplication.bean

data class FiveHundredMScd (
    var instrumentid: String = "--", //合约号
    var instrumentName: String = "--",//合约名
    var tradingday: String = "--", //交易日
    var updatetime: String = "--", //最后修改时间
    var updown : String= "--", //涨跌
    var lowestprice: String = "--",//最低价
    var lastprice : String= "--", //最新价
    var turnover: String = "--", //成交金额
    var askprice1 : String= "--", //申卖价一
    var bidvolume1 : String= "--", //申买量一
    var openprice : String= "--", //今开盘
    var openinterest : String= "--",//持仓量
    var precloseprice: String = "--",//昨收盘
    var updownper : String= "--", //涨跌幅
    var askvolume1 : String= "--", //申卖量一
    var settlementprice: String = "--", //今结算
    var presettlementprice: String = "--", //昨结算
    var lowerlimitprice: String = "--", //跌停板价
    var upperlimitprice: String = "--", //涨停板价
    var highestprice: String = "--", //最高价
    var volumechange : String= "--", //先手
    var volume : String= "--", //数量
    var preopeninterest: String = "--", //昨持仓量
    var bidprice1: String = "--", //申买价一
    var amplitude: String = "--", //
    var turnoverrate : String= "--"
)