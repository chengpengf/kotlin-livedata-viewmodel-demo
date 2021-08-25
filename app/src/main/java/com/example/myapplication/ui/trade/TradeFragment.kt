package com.example.myapplication.ui.trade

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentTradeBinding
import com.example.myapplication.utils.PwdInputFilter

class TradeFragment : BaseFragment<FragmentTradeBinding>() {

    private lateinit var tradeViewModel: TradeViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tradeViewModel =
            ViewModelProvider(this).get(TradeViewModel::class.java)
        tradeViewModel.getData()
        tradeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        binding.mima.filters = arrayOf(PwdInputFilter(), InputFilter.LengthFilter(11))
        binding.btnLogin.setOnClickListener {
            hideSoftInput()
            if (TextUtils.isEmpty(binding.user.text)) {
                Toast.makeText(context, "投资者编号不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.mima.text)) {
                Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    private fun hideSoftInput() {

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            (context as Activity).currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    inner class EmojiInputFilter : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    //Toast("不能输入特殊字符")
                    Toast.makeText(context, "不能输入特殊字符", Toast.LENGTH_SHORT).show()
                    return ""
                }
            }
            return null
        }
    }
}