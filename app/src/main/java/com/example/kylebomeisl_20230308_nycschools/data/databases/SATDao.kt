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
interface SATDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSAT(sat: SAT_DB)

    @Update
    suspend fun updateSAT(sat: SAT_DB)

    @Upsert
    suspend fun upsertSAT(sat: SAT_DB)

    @Delete
    suspend fun deleteSAT(sat: SAT_DB)

    @Query("SELECT * FROM SAT ORDER BY school ASC")
    fun dbGetAllSATs(): Flow<List<SAT_DB>>

    @Query("SELECT * FROM SAT WHERE school = :school")
    fun dbGetSAT(school: String): Flow<List<SAT_DB>>

}