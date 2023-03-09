package com.example.kylebomeisl_20230308_nycschools.data.network

import com.example.kylebomeisl_20230308_nycschools.data.databases.School_DB
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.jackson.jackson
import javax.inject.Inject

@JsonIgnoreProperties(ignoreUnknown = true)
class School_network(
    @JsonProperty("school_name") val school_name: String = "",
    @JsonProperty("overview_paragraph") val overview_paragraph: String = "",
    @JsonProperty("location") val location: String = ""
)

fun School_network.toDB() = School_DB(
    name = school_name,
    overview = overview_paragraph,
    location = location
)
class SchoolNetworkDataSource @Inject constructor(ktorEngine: HttpClientEngine) {




    val ktorClient = HttpClient(ktorEngine) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    suspend fun ktorGetSchoolList(): MutableList<School_network> =
        ktorClient.get("https://data.cityofnewyork.us/resource/s3k6-pzi2.json").body()
}
