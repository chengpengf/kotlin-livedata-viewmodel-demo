package com.example.myapplication.bean

data class BaseResp<T> (
    var errorCode :Int = 0,
    var errorMsg:String = "",
    var `data`:T
)