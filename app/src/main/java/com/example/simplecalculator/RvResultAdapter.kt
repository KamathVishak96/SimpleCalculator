package com.example.simplecalculator

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.R
import kotlinx.android.synthetic.main.rvcalculator_result.view.*

class RvResultAdapter() : RecyclerView.Adapter<RvResultAdapter.VHResult>() {

    var results = ArrayList<Int>()

    fun addResult(result: Int) {
        results.add(result)
        notifyItemInserted(results.size - 1)

    }

    override fun onBindViewHolder(holder: VHResult, pos: Int) {
        holder.bind(results[pos])
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): VHResult {
        return VHResult(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rvcalculator_result, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun filterResults(filteredList: ArrayList<Int>) {
        results = filteredList
        notifyDataSetChanged()
    }

    inner class VHResult(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(result: Int?) {
            with(itemView) {
                tvResult.text = result?.toString()
                setOnClickListener {
                    Toast.makeText(
                        context, "${results[adapterPosition]} Clicked",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}