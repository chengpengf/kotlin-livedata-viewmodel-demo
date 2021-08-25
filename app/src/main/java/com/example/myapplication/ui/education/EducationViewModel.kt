package com.example.myapplication.ui.education

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bean.EduTabBean
import com.example.myapplication.retrofit.RetrofitFactory
import kotlinx.coroutines.launch
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class EducationViewModel : ViewModel() {

    var tabs = MutableLiveData<List<EduTabBean>>()
    fun getTabs() {
        RetrofitUrlManager.getInstance().putDomain("cpf", "http://218.202.237.33:8088/")
        viewModelScope.launch {
            try {
                val data = RetrofitFactory.getApiService().getTabs()
                tabs.value = data.data
            } catch (e: Exception) {
                Log.d("cpf", "e--" + e.message)
            }
        }
    }
}