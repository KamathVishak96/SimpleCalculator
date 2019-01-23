package com.example.database

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import kotlinx.android.synthetic.main.activity_database.*

class DatabaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val db = DatabaseHelper(this)

        btnAddToDB.setOnClickListener {
            if (etDBInputOne.text.toString().isNotEmpty() && etDBInputOne.text.toString().toInt() > 0 && etDBInputTwo.text.toString().isNotEmpty()) {
                val student = Students(etDBInputOne.text.toString().toInt(), etDBInputTwo.text.toString())

                db.insertData(student)
            } else {

            }

        }
        btnReadDB.setOnClickListener {
            var data = db.readData()
            tvResultDB.text = ""
            for(i in 0 until data.size){
                tvResultDB.append(data[i].toString()+"\n")
            }
        }
    }

}
