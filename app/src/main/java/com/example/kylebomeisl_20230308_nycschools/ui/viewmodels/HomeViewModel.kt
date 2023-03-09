package com.example.kylebomeisl_20230308_nycschools.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kylebomeisl_20230308_nycschools.data.databases.SAT_DB
import com.example.kylebomeisl_20230308_nycschools.data.databases.School_DB
import com.example.kylebomeisl_20230308_nycschools.data.repositories.SATRepository
import com.example.kylebomeisl_20230308_nycschools.data.repositories.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val schoolRepository: SchoolRepository,
    private val satRepository: SATRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            schoolRepository.refreshDb()
            satRepository.refreshDb()
        }
        fetchSchools()
    }

    //Home UI State Holder
    private val _schoolList = MutableStateFlow(mutableListOf<School_DB>())
    val schoolList = _schoolList.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchSchools() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val schoolList = schoolRepository.getAllSchools().collect{
                    _schoolList.value = it
                }
            } catch (e: HttpRequestTimeoutException) {
                Log.e("school","http exception")
            }
        }
    }


    //Details UI Holder
    private var _satData = MutableStateFlow<List<SAT_DB>>(mutableListOf())
    val satData = _satData.asStateFlow()

    private var fetchSATJob: Job? = null

    fun fetchSATs(currentSchool: String) {
        fetchSATJob?.cancel()
        fetchSATJob = viewModelScope.launch {
            try {
                satRepository.getSAT(currentSchool).collect{
                    _satData.value = it
                }
            } catch (e: HttpRequestTimeoutException) {
                Log.e("school","http exception")
            }
        }
    }

}