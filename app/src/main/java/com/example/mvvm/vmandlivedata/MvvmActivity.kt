package com.example.mvvm.vmandlivedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import kotlinx.android.synthetic.main.activity_mvvm.*


class MvvmActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)

        val vm = ViewModelProviders.of(this).get(ViewModel::class.java)
        (vm.getNum() as LiveData<String>).observe(this, Observer {
            tvBindingText.text = it
        })

        btnBindData.setOnClickListener {
            vm.createNum()
        }

    }
}