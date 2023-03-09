package com.example.kylebomeisl_20230308_nycschools.data.repositories

import com.example.kylebomeisl_20230308_nycschools.data.databases.SATDao
import com.example.kylebomeisl_20230308_nycschools.data.databases.SAT_DB
import com.example.kylebomeisl_20230308_nycschools.data.network.SATNetworkDataSource
import com.example.kylebomeisl_20230308_nycschools.data.network.SAT_network
import com.example.kylebomeisl_20230308_nycschools.data.network.toDB
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SATRepository @Inject constructor(
    private val satDao: SATDao,
    private val satNetworkDataSource: SATNetworkDataSource
) {

    fun getAllSATs(): Flow<List<SAT_DB>> =
        satDao.dbGetAllSATs()

   fun getSAT(school: String): Flow<List<SAT_DB>> =
        satDao.dbGetSAT(school)

    suspend fun insertSAT(sat: SAT_DB) {
        satDao.insertSAT(sat)
    }

    suspend fun updateSAT(sat: SAT_DB) {
        satDao.updateSAT(sat)
    }

    suspend fun upsertSAT(sat: SAT_DB) {
        satDao.upsertSAT(sat)
    }

    suspend fun deleteSAT(sat: SAT_DB) {
        satDao.deleteSAT(sat)
    }

    //Refresh database with new Ktor GET call results
   suspend fun refreshDb() {
        val newSatList = satNetworkDataSource.ktorGetSATList()
        for (sat in newSatList) {
            upsertSAT(sat = sat.toDB())
        }
    }


    suspend fun getSATList(): MutableList<SAT_network> = satNetworkDataSource.ktorGetSATList()


}