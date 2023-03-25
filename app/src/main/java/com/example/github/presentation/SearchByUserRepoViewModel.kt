package com.example.github.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.data.models.SearchByRepositoryResponse
import com.example.github.data.models.data.ResultData
import com.example.github.domain.MainRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchByUserRepoViewModel(private val repo: MainRepository) : ViewModel() {
    val activeSearchFlow = MutableSharedFlow<SearchByRepositoryResponse>()
    val messageFlow = MutableSharedFlow<String>()
    val errorFlow = MutableSharedFlow<Throwable>()

    suspend fun searchByRepo(repoName:String) {
        repo.searchByRepo(repoName).onEach {
            when(it) {
                is ResultData.Success -> {
                    activeSearchFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    messageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    errorFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}