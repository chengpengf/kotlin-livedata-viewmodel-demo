package com.example.myapplication.base;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    private static final String TAG = "BaseFragment";
    protected T binding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*getClass 获取该类的类类型(BaseFragment子类的类型)
         * getGenericSuperclass() 获得该类带有泛型的父类(在子类中确定的BaseFragment的类型)
         * Type是 Java中所有类型的公共高级接口。包括原始类型、参数化类型、数组类型、类型变量和基本类型
         * ParameterizedType参数化类型，即泛型
         * getActualTypeArguments获取参数化类型的数组，泛型可能有多个
         * */
        try {
            Type superclass = getClass().getGenericSuperclass();
            //获得父类的泛型参数的实际类型
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            //获取inflate方法 传入相应的参数
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            //执行inflate方法
            binding = (T) method.invoke(null, getLayoutInflater(), container, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

