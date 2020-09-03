package com.example.nycschoolsmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nycschoolsmvvm.model.NYCRepository
import com.example.nycschoolsmvvm.model.NYCResponse
import com.example.nycschoolsmvvm.model.NYCSATResponse
import com.example.nycschoolsmvvm.model.NYCSchoolTable

/**
 * Handle databinding connections in a regular MVVM
 * with Android Jetpack
 * subclass ViewModel => should not have any Context relation
 * AndroidViewModel => can have Context
 */
class NYCViewModel: ViewModel() {
    // init Retrofit
    // get list of schools
    // get list of SAT

    var nycRepository: NYCRepository = NYCRepository()

    private var mutableLiveDataDataSet
            : MutableLiveData<ConnectionState> = MutableLiveData()

    /**
     * View can consume now
     * List<NYCSat> / List<NYCResponse> / ErrorMessages / Loading
     */
    fun getLiveData(): LiveData<ConnectionState>{
        return mutableLiveDataDataSet
    }

    fun insertIntoDBListSchools(dataSet: List<NYCResponse>) {
        nycRepository.insertListSchoolsTable(dataSet){
            //todo get data from DB
            queryListOfSchoolsTable()
        }
    }

    fun insertIntoSatTable(dataSet: List<NYCSATResponse>) {

    }

    fun queryListOfSchoolsTable(){
        nycRepository.queryListOfSchools {
            mutableLiveDataDataSet.value = ConnectionState.AdapterData(it)
        }
    }

    private fun updateMutableLiveData(responseBody: List<NYCResponse>){
        val data = ConnectionState.ListSchoolsNetworkResponse(responseBody)
        mutableLiveDataDataSet.value = data
    }

    fun initNetworkCall(){
        nycRepository.initNetworkCall(::updateMutableLiveData)
    }

    sealed class ConnectionState{
        data class SATNetworkResponse(val dataSet: List<NYCSATResponse>): ConnectionState()
        data class ListSchoolsNetworkResponse(val dataSet: List<NYCResponse>): ConnectionState()
        object LOADING: ConnectionState()
        data class ErrorMessage(val errorMessage: String): ConnectionState()
        data class AdapterData(val dataSet: List<NYCSchoolTable>): ConnectionState()
    }
}

