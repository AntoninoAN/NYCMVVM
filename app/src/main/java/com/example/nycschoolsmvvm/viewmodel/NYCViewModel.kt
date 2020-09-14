package com.example.nycschoolsmvvm.viewmodel

import android.content.Context
import android.view.View
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
//abstract class NYCViewModel: ViewModel(){onCleared()
// common()
// }
//class Act1VM(val something = "dfd"): NYCVIewModel{}
// class InterfaceImpl : Interface{
//  fun 1(){}, fun 2(){}, fun3(){}
// }
// class Act2VM: NYCVIewModel, InterfaceImpl(){
// override fun 1(){
//    custom impl fun 1
// }
// }

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
        nycRepository.insertListSATTable(dataSet)
    }

    fun queryListOfSchoolsTable(){
        nycRepository.queryListOfSchools {
            mutableLiveDataDataSet.postValue(ConnectionState.AdapterData(it))
        }
    }

    private fun updateMutableLiveData(responseBody: List<NYCResponse>){
        val data = ConnectionState.ListSchoolsNetworkResponse(responseBody)
        mutableLiveDataDataSet.value = data
    }

    private fun updateMutableLiveDataSATResponse(responseBody: List<NYCSATResponse>){
        val data = ConnectionState.ListSatNetworkResponse(responseBody)
        mutableLiveDataDataSet.value = data
    }

    fun initNetworkCall(){
        nycRepository.initNetworkCall(::updateMutableLiveData)
    }

    fun initNetworkCallSat(){
        nycRepository.initNetworkCallSat(::updateMutableLiveDataSATResponse)
    }



    sealed class ConnectionState{
        data class SATNetworkResponse(val dataSet: List<NYCSATResponse>): ConnectionState()
        data class ListSchoolsNetworkResponse(val dataSet: List<NYCResponse>): ConnectionState()
        data class ListSatNetworkResponse(val dataSet: List<NYCSATResponse>): ConnectionState()
        object LOADING: ConnectionState()
        data class ErrorMessage(val errorMessage: String): ConnectionState()
        data class AdapterData(val dataSet: List<NYCSchoolTable>): ConnectionState()
    }
}

