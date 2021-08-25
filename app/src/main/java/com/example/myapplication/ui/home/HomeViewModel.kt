package com.example.myapplication.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bean.BannerBean
import com.example.myapplication.bean.WanAndroidBannerBean
import com.example.myapplication.bean.Fiction
import com.example.myapplication.retrofit.RetrofitFactory
import kotlinx.coroutines.launch
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class HomeViewModel : ViewModel() {

    private var fictions = MutableLiveData<List<Fiction>>()
    var wanAndroidBanners = MutableLiveData<List<WanAndroidBannerBean>>()
    var banners = MutableLiveData<List<BannerBean>>()
    var marketData = MutableLiveData<List<String>>()

    fun getFictions() {
        viewModelScope.launch {
            try {
                val data = RetrofitFactory.getApiService().getFictions()
                fictions.value = data.data
            } catch (e: Exception) {
                Log.e("cpf", e.toString())
            }
        }
    }

    fun getWanAndroidBanner() {
        viewModelScope.launch {
            try {
                val data = RetrofitFactory.getApiService().getWanAndroidBanner()
                wanAndroidBanners.value = data.data
            } catch (e: Exception) {
                Log.e("cpf", e.toString())
            }
        }
    }

    fun getBanner(){
        RetrofitUrlManager.getInstance().putDomain("cpf", "http://218.202.237.33:8088/")
        viewModelScope.launch {
            try {
                var data = RetrofitFactory.getApiService().getBanner()
                banners.value = data.datas
            } catch (e: Exception) {
                Log.e("cpf", e.toString())
            }
        }
    }

    fun getMarketData(){
        RetrofitUrlManager.getInstance().putDomain("cpf", "http://218.202.237.33:8088/")
        viewModelScope.launch {
            try {
                var data = RetrofitFactory.getApiService().getOptionalMarketData("")
                marketData.value = data.data
            } catch (e: Exception) {
                Log.e("cpf", e.toString())
            }
        }
    }

}