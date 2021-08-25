package com.example.myapplication.ui.hangqing

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentHangqingBinding

class HangqingFragment : BaseFragment<FragmentHangqingBinding>() {

    private lateinit var notificationsViewModel: HangqingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationsViewModel =
            ViewModelProvider(this).get(HangqingViewModel::class.java)
        notificationsViewModel.getData()
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.progressBar.hide()
            binding.textHangqing.text = it
        })

    }
}