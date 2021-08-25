package com.example.myapplication.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bean.NewsBean
import com.example.myapplication.retrofit.RetrofitFactory
import kotlinx.coroutines.launch
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class NewsViewModel : ViewModel() {

    var news = MutableLiveData<List<NewsBean>>()
    fun getNews() {
        RetrofitUrlManager.getInstance().putDomain("cpf", "http://218.202.237.33:8088/")
        viewModelScope.launch {
            try {
                val data = RetrofitFactory.getApiService().getNews()
                news.value = data.datas
            } catch (e: Exception) {
                Log.d("cpf", "e--" + e.message)
            }
        }
    }
}