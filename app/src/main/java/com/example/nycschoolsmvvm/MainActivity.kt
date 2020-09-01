package com.example.nycschoolsmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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