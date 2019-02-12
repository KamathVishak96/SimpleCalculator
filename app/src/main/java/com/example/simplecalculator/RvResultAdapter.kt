package com.example.simplecalculator

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import kotlinx.android.synthetic.main.rvcalculator_result.view.*
import timber.log.Timber

class RvResultAdapter(val onClick: (Int) -> Unit, val viewModel: ResultViewModel, val activity: AppCompatActivity) :
    RecyclerView.Adapter<RvResultAdapter.VHResult>() {


    var results = ArrayList<Int>()
    private lateinit var viewGroup: ViewGroup
    fun addResult(result: Int) {
        results.add(result)
        notifyItemInserted(results.size - 1)

    }

    override fun onBindViewHolder(holder: VHResult, pos: Int) {

    //    holder.bind(results[pos])
        holder.bind(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): VHResult {
        viewGroup = parent
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

    inner class VHResult(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pos: Int) {
            with(itemView) {

                viewModel.setResult(results)

                viewModel.let {
                    (it.getResult() as LiveData<ArrayList<Int>>)
                        .observe(activity, Observer {
                            if (!results.isEmpty() && it != null){
                                tvResult.text = it[pos].toString()
                                Timber.i("bind: $it[pos]")
                            }

                        })
                }

         //       tvResult.text = result?.toString()
                setOnClickListener {
                    onClick(results[adapterPosition])
                }
            }
        }
    }
}