package com.example.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.R
import kotlinx.android.synthetic.main.list_layout.view.*

class AdapterClass(val context: Context, val names: List<Names>): RecyclerView.Adapter<AdapterClass.ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        val name = names[p1]
        p0.setData(name, p1)

    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_layout, p0, false))
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var currName: Names? = null
        var pos: Int = 0

        init{
            itemView.setOnClickListener {
                Toast.makeText(context, currName!!.name + "Clicked", Toast.LENGTH_LONG).show()
            }
        }

        fun setData(name: Names?, position: Int){
            itemView.titleList.text=name!!.name

            this.currName = name
            this.pos = position
        }
    }
}