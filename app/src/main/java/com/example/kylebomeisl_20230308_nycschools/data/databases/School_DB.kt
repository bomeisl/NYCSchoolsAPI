package com.example.kylebomeisl_20230308_nycschools.data.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "School")
data class School_DB(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "location") val location: String
)


//fun School_DB.toUI() = School_UI(
//    name = name,
//    overview = overview,
//    location = location
//)
