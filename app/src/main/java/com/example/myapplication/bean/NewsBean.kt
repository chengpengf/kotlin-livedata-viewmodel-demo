package com.example.myapplication.bean

data class NewsBean(

    val `abstract`: String,
    var cover: String,
    var date: String,
    val multi_list: List<NewsBean>,
    val multi_list_count: Int,
    var origin: String,
    var origin_name: String,
    val position: String,
    val product: String,
    val tag: String,
    val time: String,
    var title: String,
    val type: Int,
    var url: String
)



    /*val `abstract`: String,
    val cover: String,
    val date: String,
    val origin: String,
    val origin_name: String,
    val position: String,
    val product: String,
    val tag: String,
    val time: String,
    val title: String,
    val type: Int,
    val url: String*/
