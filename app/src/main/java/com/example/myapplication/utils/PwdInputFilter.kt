package com.example.myapplication.utils

import android.text.InputFilter
import android.text.Spanned

class PwdInputFilter  : InputFilter {

    override fun filter(
        source: CharSequence,//将要输入的字符串,如果是删除操作则为空
        start: Int,//将要输入的字符串起始下标，一般为0
        end: Int,//start + source字符的长度
        dest: Spanned,//输入之前文本框中的内容
        dstart: Int,//将会被替换的起始位置
        dend: Int//dstart+将会被替换的字符串长度
    ): CharSequence {//方法返回的值将会替换掉dest字符串中dstartd位置到dend位置之间字符，返回source表示不做任何处理，返回空字符串""表示不输入任何字符
        var tem = ""
        var digits =  "/\\:*?<>|\"\n\t"

       for (i in 0..source.length){
           if (digits.indexOf(source[i]) < 0) {
               //不含特殊字符 匹配成功
               return ""
           }
       }
        /*for(var i=0 ;i<source.length();i++){
            if (digits.indexOf(source.charAt(i)) < 0) {
                //不含特殊字符 匹配成功
                return null;
            }
        }*/
        //匹配不成功！
        return tem ;


    }
}

