package com.example.nycschoolsmvvm.model


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// VM -> Repo [DATA]
// Repo -> VM
// interface{fun something(responseBody: List<NYCResponse>)}
// NYCViewmodel: interface{
//fun something(responseBody: List<NYCResponse>)
// }
// NYCRepository{
// val listener: interface
// fun bind(listener: interface){this.listener = listener}}
class NYCRepository {
    //vm.sendDAta

    fun initNetworkCall(callback: (responseBody: List<NYCResponse>)->Unit){
        NYCRetrofit.initRetrofit().getListOfSchools()
            .enqueue(object: Callback<List<NYCResponse>>{
                override fun onFailure(call: Call<List<NYCResponse>>, t: Throwable) {
                    //
                }

                override fun onResponse(
                    call: Call<List<NYCResponse>>,
                    response: Response<List<NYCResponse>>) {
                    if(response.isSuccessful && response.body() != null)
                        callback.invoke(response.body()!!)
                }
            })
    }

    fun insertListSchoolsTable(dataset: List<NYCResponse>,
        callback: ()->Unit){
        var listOfTables = mutableListOf<NYCSchoolTable>()
        for(element in dataset){
            val nycTable = NYCSchoolTable(element.dbn,
            element.school_name,
            element.total_students,
            element.school_sports)
            listOfTables.add(nycTable)
        }

        val dataBase = NYCRoomDB.newInstance().getNYCDao()
        //long operation, needs to happen in a WorkerThread
        Thread(Runnable {
            dataBase.insertNYCSchoolTable(listOfTables)
            callback.invoke()
        }).start()
    }

    fun queryListOfSchools(callback: (responseTable: List<NYCSchoolTable>)->Unit){
        val dataBase = NYCRoomDB.newInstance().getNYCDao()
        //var response = emptyList<NYCSchoolTable>()
        Thread(Runnable {
            callback.invoke(dataBase.getSchoolTable())
        }).start()
//        var response = emptyList<NYCSchoolTable>()
//        var dkdkd = Thread(Runnable {
//
//            response = dataBase.getSchoolTable()
//        }).join()
        //return response
    }
}


