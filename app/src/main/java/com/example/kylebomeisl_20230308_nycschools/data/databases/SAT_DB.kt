package com.example.kylebomeisl_20230308_nycschools.data.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SAT")
data class SAT_DB(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "school") val school: String,
    @ColumnInfo(name ="math") val math: String,
    @ColumnInfo(name ="critical_reading") val critical_reading: String,
    @ColumnInfo(name ="writing") val writing: String
)

//fun SAT_DB.toUI(): SAT_UI = SAT_UI(
//    school = school,
//    math = math,
//    critical_reading = critical_reading,
//    writing = writing
//)