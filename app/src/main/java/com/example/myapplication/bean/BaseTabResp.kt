package com.example.myapplication.bean

data class BaseTabResp<T> (
    var code :Int = 0,
    var count:String = "",
    var `data`:T
)