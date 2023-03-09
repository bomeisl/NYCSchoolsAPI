package com.example.kylebomeisl_20230308_nycschools.data.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSchool(school: School_DB)

    @Update
    suspend fun updateSchool(school: School_DB)

    @Upsert
    suspend fun upsertSchool(school: School_DB)

    @Delete
    suspend fun deleteSchool(school: School_DB)

    @Query("SELECT * FROM School ORDER BY name ASC")
    fun dbGetAllSchools(): Flow<MutableList<School_DB>>

    @Query("SELECT * FROM School WHERE name = :name")
    fun dbGetSchool(name: String): Flow<School_DB>
}