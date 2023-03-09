package com.example.kylebomeisl_20230308_nycschools.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.kylebomeisl_20230308_nycschools.data.databases.SAT_DB
import com.example.kylebomeisl_20230308_nycschools.data.databases.School_DB
import com.example.kylebomeisl_20230308_nycschools.data.network.SAT_network
import com.example.kylebomeisl_20230308_nycschools.data.repositories.SATRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val satRepository: SATRepository
    ): ViewModel() {



    var satList: MutableStateFlow<MutableList<SAT_network>> = MutableStateFlow(mutableListOf<SAT_network>())

    init {
        viewModelScope.launch(Dispatchers.IO) {
           satList.update {
               async {
                   satRepository.getSATList().toMutableList()

               }.await()
           }

        }
    }
}