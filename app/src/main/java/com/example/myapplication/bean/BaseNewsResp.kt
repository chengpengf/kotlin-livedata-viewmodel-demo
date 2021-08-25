package com.example.myapplication.bean

data class BaseNewsResp<T> (
    var code :Int = 0,
    var count:String = "",
    var `datas`:T
)