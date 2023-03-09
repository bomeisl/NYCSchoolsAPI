package com.example.kylebomeisl_20230308_nycschools.data.network

import androidx.room.ColumnInfo
import com.example.kylebomeisl_20230308_nycschools.data.databases.SAT_DB
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.jackson.jackson
import javax.inject.Inject



@JsonIgnoreProperties(ignoreUnknown = true)
class SAT_network(
    @JsonProperty("school_name") val school_name: String = "",
    @JsonProperty("num_of_sat_test_takers") val num_of_sat_test_takers: String = "",
    @JsonProperty("sat_critical_reading_avg_score") val sat_critical_reading_avg_score: String = "",
    @JsonProperty("sat_math_avg_score") val sat_math_avg_score: String = "",
    @JsonProperty("sat_writing_avg_score") val sat_writing_avg_score: String = ""
)

fun SAT_network.toDB() = SAT_DB(
    school = school_name,
    math = sat_math_avg_score,
    critical_reading = sat_critical_reading_avg_score,
    writing = sat_writing_avg_score
)

class SATNetworkDataSource @Inject constructor(ktorEngine: HttpClientEngine) {



    val ktorClient = HttpClient(ktorEngine) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    suspend fun ktorGetSATList(): MutableList<SAT_network> =
        ktorClient.get("https://data.cityofnewyork.us/resource/s3k6-pzi2.json").body()

}



