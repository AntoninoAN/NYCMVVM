package com.example.nycschoolsmvvm.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NYCDao {
    @Insert(entity = NYCSchoolTable::class,
        onConflict = OnConflictStrategy.REPLACE)
    fun insertNYCSchoolTable(dataSet: List<NYCSchoolTable>)

    @Insert(entity = NYCSATTable::class,
        onConflict = OnConflictStrategy.REPLACE)
    fun insertNYCSATTable(dataSet: List<NYCSATTable>)

    @Query("SELECT * FROM SCHOOL_SAT INNER JOIN SCHOOL_TABLE " +
            "ON SCHOOL_SAT.dbn = SCHOOL_TABLE.id")
    fun getSchoolsWithSATScore(): List<NYCSchoolTable>

    @Query("SELECT * FROM SCHOOL_SAT")
    fun getSchoolSATScore(): List<NYCSATTable>

    @Query("SELECT * FROM school_table")
    fun getSchoolTable(): List<NYCSchoolTable>
}

