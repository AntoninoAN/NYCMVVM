package com.example.nycschoolsmvvm.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nycschoolsmvvm.R
import com.example.nycschoolsmvvm.model.NYCResponse
import com.example.nycschoolsmvvm.model.NYCSATResponse
import com.example.nycschoolsmvvm.model.NYCSchoolTable
import com.example.nycschoolsmvvm.viewmodel.NYCViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var nycViewModel: NYCViewModel
    lateinit var nycAdapter: NYCAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nycViewModel = ViewModelProvider(this,
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return NYCViewModel() as T
            }
        }).get(NYCViewModel::class.java)

        nycViewModel.getLiveData().observe(this,
            object : Observer<NYCViewModel.ConnectionState>{
                override fun onChanged(t: NYCViewModel.ConnectionState?) {
                    t?.let {
                        when(it){
                            is NYCViewModel.ConnectionState.LOADING->
                                showProgressBar()
                            is NYCViewModel.ConnectionState.SATNetworkResponse->
                                insertSatDB(it.dataSet)
                            is NYCViewModel.ConnectionState.ListSchoolsNetworkResponse->
                                insertListSchools(it.dataSet)
                            is NYCViewModel.ConnectionState.ListSatNetworkResponse->
                                insertSatDB(it.dataSet)
                            is NYCViewModel.ConnectionState.ErrorMessage->
                                showErrorMessage(it.errorMessage)
                            is NYCViewModel.ConnectionState.AdapterData->
                                updateAdapter(it.dataSet)
                        }
                    }
                }
            })

        storeFavoriteSchool("458")
        getFavoriteSchool()
        nycViewModel.initNetworkCall()
        nycViewModel.initNetworkCallSat()
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun insertListSchools(dataSet: List<NYCResponse>) {
        nycViewModel.insertIntoDBListSchools(dataSet)

    }

    private fun insertSatDB(dataSet: List<NYCSATResponse>) {
        nycViewModel.insertIntoSatTable(dataSet)
    }

    private fun showProgressBar() {
        pb_loading.visibility =
            if(pb_loading.visibility == View.GONE)
                View.VISIBLE
            else
                View.GONE
    }

    private fun updateAdapter(dataSet: List<NYCSchoolTable>){
        Log.d("MainActivity", "Size: ${dataSet.size}")
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = NYCAdapter(dataSet, ::openDetailedFragment)
    }

    private fun openDetailedFragment(nycSchoolTable: @ParameterName(name = "item") NYCSchoolTable) {
        Log.d("MainActivity", "openDetailedFragment")

        //supportFragmentManager.beginTransaction().replace().commit()




    }

    private fun storeFavoriteSchool(dbn: String){
        val sharedPreferences: SharedPreferences =
        getSharedPreferences("FavoriteSchools",
        Context.MODE_PRIVATE)
        //sharedPreferences.getString("KEY_DBN_FAVORITE",dbn)
        val editor: SharedPreferences.Editor=
            sharedPreferences.edit()
        editor.putString("KEY_DBN_FAVORITE", dbn)
        editor.apply()
//        getSharedPreferences("FavoriteSchools",
//            Context.MODE_PRIVATE)
//            .edit()
//            .putString("KEY_DBN_FAVORITE", dbn)
//            .apply()
    }

    private fun getFavoriteSchool(){
        val value=
        getSharedPreferences("FavoriteSchools",
            Context.MODE_PRIVATE)
            .getString("KEY_DBN_FAVORITE","N/A")
        Log.d("MainActivity", "$value")
    }

// GET the list of Schools
// GET the list of SAT
// insert into DB
// Query from the DB (display list of Schools)
// Query the SAT item (dbn)

    override fun onDestroy() {
        super.onDestroy()
        //todo
    }
}