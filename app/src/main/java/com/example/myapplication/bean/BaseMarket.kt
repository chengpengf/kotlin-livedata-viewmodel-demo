package com.example.myapplication.bean

data class BaseMarket<T>(
    var count: Int,
    var status: Int,
    var `data`: T
)
