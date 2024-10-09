package com.faraz.gitfinder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraz.gitfinder.data.model.GithubRepository
import com.faraz.gitfinder.data.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: RemoteRepository) : ViewModel() {

    private val _repositories = MutableStateFlow<List<GithubRepository>>(emptyList())
    val repositories: StateFlow<List<GithubRepository>> get() = _repositories

    fun searchRepositories(query: String) {
        viewModelScope.launch {
//            Log.d("Response:", repository.searchRepositories(query).toString())
            val result = repository.searchRepositories(query)
            _repositories.value = result.items
        }
    }
}
