package com.example.nycschoolsmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nycschoolsmvvm.model.NYCResponse
import com.example.nycschoolsmvvm.model.NYCSATResponse
import com.example.nycschoolsmvvm.model.NYCSchoolTable
import com.example.nycschoolsmvvm.viewmodel.NYCViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var nycViewModel: NYCViewModel

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
                            is NYCViewModel.ConnectionState.ErrorMessage->
                                showErrorMessage(it.errorMessage)
                            is NYCViewModel.ConnectionState.AdapterData->
                                updateAdapter(it.dataSet)
                        }
                    }
                }
            })

        nycViewModel.initNetworkCall()
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