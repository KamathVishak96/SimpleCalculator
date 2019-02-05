package com.example.mvvm.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MvvmTwoViewModel: ViewModel(){

    private val typedString: MutableLiveData<String>? by lazy {
        MutableLiveData<String>()
    }

    fun getString(): MutableLiveData<String>? {
        if(typedString == null){
            setString("")
        }
        return typedString
    }

    fun setString(string: String){
        typedString?.value = string
    }

}