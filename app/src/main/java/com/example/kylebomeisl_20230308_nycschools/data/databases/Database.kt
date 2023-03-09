package com.example.kylebomeisl_20230308_nycschools.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [School_DB::class, SAT_DB::class], version = 1, exportSchema = false)
abstract class NYCDatabase: RoomDatabase() {

    abstract fun satDao(): SATDao
    abstract fun schoolDao(): SchoolDao

    companion object {
        @Volatile
        private var Instance: NYCDatabase? = null

        fun getDatabase(context: Context): NYCDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NYCDatabase::class.java, "NYC_DB")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}