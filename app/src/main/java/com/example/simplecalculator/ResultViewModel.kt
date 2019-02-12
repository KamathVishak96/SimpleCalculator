package com.example.simplecalculator

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ResultViewModel: ViewModel() {
    private val resultMutableLiveData by lazy { MutableLiveData<ArrayList<Int>>() }

    fun getResult(): MutableLiveData<ArrayList<Int>> {
        return resultMutableLiveData
    }

    fun setResult(result: ArrayList<Int>){
        resultMutableLiveData.value = result
    }
}