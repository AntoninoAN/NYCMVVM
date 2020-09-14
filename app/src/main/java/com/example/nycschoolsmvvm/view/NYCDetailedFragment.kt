package com.example.nycschoolsmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nycschoolsmvvm.R
import com.example.nycschoolsmvvm.model.NYCSchoolTable

class NYCDetailedFragment: Fragment() {
    companion object{
        val EXTRA_ITEM = "NYCDetailedFragment"
        fun newInstance(item: NYCSchoolTable): NYCDetailedFragment{
            val fragment = NYCDetailedFragment()
            val bundle = Bundle()
//            bundle.putParcelable(EXTRA_ITEM, item)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_detail,
        container, true)

        return view
    }
}