package com.example.kylebomeisl_20230308_nycschools.data.repositories


import com.example.kylebomeisl_20230308_nycschools.data.databases.SchoolDao
import com.example.kylebomeisl_20230308_nycschools.data.databases.School_DB
import com.example.kylebomeisl_20230308_nycschools.data.network.SchoolNetworkDataSource
import com.example.kylebomeisl_20230308_nycschools.data.network.School_network
import com.example.kylebomeisl_20230308_nycschools.data.network.toDB
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SchoolRepository @Inject constructor(
    private val schoolsDao: SchoolDao,
    private val schoolNetworkDataSource: SchoolNetworkDataSource
    ) {

    fun getAllSchools(): Flow<MutableList<School_DB>> =
        schoolsDao.dbGetAllSchools()

    fun getSchool(school: School_DB): Flow<School_DB> =
        schoolsDao.dbGetSchool(school.name)

    suspend fun insertSchool(school: School_DB) {
        schoolsDao.insertSchool(school = school)
    }

    suspend fun updateSchool(school: School_DB) {
        schoolsDao.updateSchool(school = school)
    }

    suspend fun upsertSchool(school: School_DB) {
        schoolsDao.upsertSchool(school = school)
    }

    suspend fun deleteSchool(school: School_DB) {
        schoolsDao.deleteSchool(school)
    }

    //Refresh database with new Ktor GET call results
    suspend fun refreshDb() {
        val newSchoolList = getSchoolList()
        for (school in newSchoolList) {
            upsertSchool(school.toDB())
        }
    }

    //Network calls

    //Ktor GET call for school JSON - deserialized return
    suspend fun getSchoolList(): MutableList<School_network> =
        schoolNetworkDataSource.ktorGetSchoolList()

}