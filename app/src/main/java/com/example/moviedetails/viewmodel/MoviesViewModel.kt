package com.example.moviedetails.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MoviesViewModel: ViewModel(){
    private val flag: MutableLiveData<Boolean>? by lazy {
        MutableLiveData<Boolean>()
    }

    fun getBoolean(): MutableLiveData<Boolean>? {
        if(flag == null){
            setBoolean(false)
        }
        return flag
    }

    fun setBoolean(string: Boolean){
        flag?.value = string
    }
}