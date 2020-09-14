package com.example.nycschoolsmvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschoolsmvvm.R
import com.example.nycschoolsmvvm.model.NYCSchoolTable
import kotlinx.android.synthetic.main.item_layout.view.*

class NYCAdapter(val dataSet: List<NYCSchoolTable>,
                 listener: (item: NYCSchoolTable) -> Unit) :
    RecyclerView.Adapter<NYCAdapter.NYCViewHolder>() {

    private val activityListener: (item: NYCSchoolTable) -> Unit = listener

    class NYCViewHolder(nycView: View) : RecyclerView.ViewHolder(nycView) {
        val tvSchoolName: TextView = nycView.txt_fragnyc_school_name
        val tvSchoolTotalStudentValue: TextView = nycView.txt_frafnyc_value_stu
        val tvSchoolSportsValue: TextView = nycView.txt_frafnyc_value_sport

        fun onBind(item: NYCSchoolTable, listener: (item: NYCSchoolTable) -> Unit) {
            tvSchoolName.text = item.school_name
            tvSchoolTotalStudentValue.text = item.total_students
            tvSchoolSportsValue.text = item.school_sports
            itemView.setOnClickListener { listener.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NYCViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_layout,
                    parent, false
                )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: NYCViewHolder, position: Int) {
        holder.onBind(dataSet[position], ::listener)
    }

    private fun listener(nycSchoolTable: @ParameterName(name = "item") NYCSchoolTable) {
        activityListener.invoke(nycSchoolTable)
    }
}
