package com.example.myapplication.ui.trade

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TradeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

    }
    private var testData: ArrayList<Map<String, String>>? = null

    val text: LiveData<String> = _text
    fun getData() {
        Handler().postDelayed({
            _text.value = "this is hangqing ,please wait"
        }, 1000L)

    }
}