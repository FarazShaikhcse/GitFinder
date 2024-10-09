package com.faraz.gitfinder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraz.gitfinder.data.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: RemoteRepository) : ViewModel() {

    fun searchRepositories(query: String) {
        viewModelScope.launch {
            Log.d("Response:", repository.searchRepositories(query).toString())
        }
    }
}
