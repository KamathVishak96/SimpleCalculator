package com.example.mvvm.vmandlivedata

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import java.util.*


class ViewModel : ViewModel() {

    private var num: MutableLiveData<String>? = null


    fun getNum(): MutableLiveData<String>? {
        if(num == null) {
            num = MutableLiveData()
            createNum()
        }
        return num
    }

    fun createNum(){
        num?.value = "${Random().nextInt(10 - 1) + 1}"
    }
}