package com.example.nycschoolsmvvm.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nycschoolsmvvm.NYCApplication

@Database(entities = [NYCSchoolTable::class,
        NYCSATTable::class], version = 1)
abstract class NYCRoomDB : RoomDatabase() {

    abstract fun getNYCDao(): NYCDao

    companion object {
        @Volatile
        var INSTANCE: NYCRoomDB? = null

        fun newInstance(): NYCRoomDB {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    NYCApplication.nycApplication!!,
                    NYCRoomDB::class.java,
                    "nyc_db")
                    //.allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

//object SOMETHING