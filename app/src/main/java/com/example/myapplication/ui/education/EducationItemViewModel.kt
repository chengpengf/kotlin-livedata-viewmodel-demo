package com.example.myapplication.ui.education

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bean.EduItemBean
import com.example.myapplication.retrofit.RetrofitFactory
import kotlinx.coroutines.launch
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class EducationItemViewModel : ViewModel() {

    var itemData = MutableLiveData<List<EduItemBean>>()
    fun getTopicData(tag: String) {
        RetrofitUrlManager.getInstance().putDomain("cpf", "http://218.202.237.33:8088/")
        viewModelScope.launch {
            try {
                val data = RetrofitFactory.getApiService().getTopicData(tag)
                itemData.value = data!!.datas
            } catch (e: Exception) {
                Log.d("cpf", "e--" + e.message)
            }
        }
    }
}