package com.faraz.gitfinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraz.gitfinder.data.db.GithubRepositoryEntity
import com.faraz.gitfinder.data.model.Contributor
import com.faraz.gitfinder.data.model.GithubRepository
import com.faraz.gitfinder.data.model.Resource
import com.faraz.gitfinder.data.model.toEntity
import com.faraz.gitfinder.data.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: RemoteRepository) : ViewModel() {

    private val _repositories = MutableStateFlow<List<GithubRepositoryEntity>>(emptyList())
    val repositories: StateFlow<List<GithubRepositoryEntity>> get() = _repositories

    private val _selectedRepo = MutableStateFlow<GithubRepositoryEntity?>(null)
    val selectedRepo: StateFlow<GithubRepositoryEntity?> get() = _selectedRepo

    private val _repoURL = MutableStateFlow<String?>(null)
    val repoURL: StateFlow<String?> get() = _repoURL

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _contributors = MutableStateFlow<List<Contributor>>(emptyList())
    val contributors: StateFlow<List<Contributor>> = _contributors

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val searchQuery = MutableStateFlow("")

    private var currentPage = 1
    private var pageSize = 15

    // Call this method when search input changes
    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    init {
        // Observe search query and debounce the API call
        viewModelScope.launch(Dispatchers.IO) {
            searchQuery
                .debounce(1000L) // 1 second debounce time
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        _repositories.value = emptyList()
                        loadRepositories(query)
                    }
                }
        }
    }

    private fun loadRepositories(query: String) {
        viewModelScope.launch {
            repository.getRepositories(query, currentPage, pageSize).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        val newList = _repositories.value.toMutableList()
                        newList.addAll(result.data ?: emptyList())
                        _repositories.value = newList
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.message
                    }
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }

    fun loadNextPage(query: String) {
        if (query.isNotBlank()) {
            currentPage += 1
            if (currentPage == 2) {
                pageSize = 10
            }
            loadRepositories(query)
        }
    }

    fun setSelectedRepo(repository: GithubRepositoryEntity) {
        _selectedRepo.value = repository
    }

    fun setURLToLoad(htmlUrl: String) {
        _repoURL.value = htmlUrl
    }

    fun loadSavedRepos() {
        viewModelScope.launch {
            _repositories.value = repository.getSavedRepositories().first()
        }
    }

    fun fetchContributors(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                val contributorsList = repository.getContributors(owner, repo)
                _contributors.value = contributorsList
            } catch (e: Exception) {
                // Handle the error
                _contributors.value = emptyList()
            }
        }
    }
}
