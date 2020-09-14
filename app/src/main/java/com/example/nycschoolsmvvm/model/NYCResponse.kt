package com.example.nycschoolsmvvm.model

data class NYCResponse(
    val dbn: String,
    val school_name: String,
    val total_students: String,
    val school_sports: String,
    val latitude: String,
    val longitude: String)

data class NYCSATResponse(
    val dbn: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,
    val school_name: String)