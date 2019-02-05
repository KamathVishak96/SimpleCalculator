package com.example.mvvm.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.example.R
import kotlinx.android.synthetic.main.activity_mvvm_two.*

class MvvmTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_two)

        ViewModelProviders.of(this).get(MvvmTwoViewModel::class.java).let {
            (it.getString() as LiveData<String>)
                .observe(this, Observer { str ->
                    tvMvvmTwo.text = str
                })

            etMvvmTwo.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    it.setString(s.toString())
                }

            })
        }
    }
}
