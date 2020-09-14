package com.example.nycschoolsmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "school_table")
data class NYCSchoolTable(
    @PrimaryKey
    @ColumnInfo(name="id")
    val dbn: String,
    @ColumnInfo(name="name")
    val school_name: String,
    val total_students: String,
    val school_sports: String,
    val latitude: String,
    val longitude: String
)
@Entity(tableName = "school_sat")
data class NYCSATTable(
    @PrimaryKey
    @ForeignKey(entity = NYCSchoolTable::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("dbn"))
    val dbn: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,
    val school_name: String)