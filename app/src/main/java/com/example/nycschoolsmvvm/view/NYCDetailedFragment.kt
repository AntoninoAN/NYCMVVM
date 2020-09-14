package com.example.nycschoolsmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nycschoolsmvvm.R
import com.example.nycschoolsmvvm.model.NYCSchoolTable
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_detail.view.*

class NYCDetailedFragment: Fragment() {

    lateinit var nycMap: GoogleMap
    lateinit var nycMapView: MapView

    companion object{
        val EXTRA_ITEM = "NYCDetailedFragment"
        fun newInstance(item: NYCSchoolTable): NYCDetailedFragment{
            val fragment = NYCDetailedFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_ITEM, item)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nycMapView.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_detail,
        container, true)
        //nycMapView = view.findViewById(R.id.mv_nyc_school)
        nycMapView = view.mv_nyc_school
        nycMapView.getMapAsync(object : OnMapReadyCallback{
            override fun onMapReady(p0: GoogleMap?) {
                p0?.let { nycMap = it }
            }
        })
        nycMapView.onResume()
        return view
    }
}