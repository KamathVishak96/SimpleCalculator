package com.example.mainactivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import kotlinx.android.synthetic.main.rv_main.view.*

class RecyclerAdapter(val onClick: (ActivityNames) -> Unit) :
    RecyclerView.Adapter<RecyclerAdapter.ActivityViewHolder>() {

    private var activityNames = emptyList<ActivityNames>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ActivityViewHolder {
        return ActivityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_main, parent, false
            )
        )
    }

    override fun getItemCount(): Int = activityNames.size

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(activityNames[position])
    }

    fun setActivities(activityNameList: List<ActivityNames>) {
        activityNames = activityNameList
        notifyDataSetChanged()
    }

    inner class ActivityViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(activity: ActivityNames) {
            with(itemView) {
                btnNextActivity.text = activity.name
                setOnClickListener {

                    onClick(activityNames[adapterPosition])
                }
            }
        }
    }
}