package com.example.github.domain

import com.example.github.data.models.data.LocalStorage
import com.example.github.data.models.data.ResultData
import com.example.github.data.remote.GitHubApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MainRepository(private val api: GitHubApi) {
    suspend fun login() = flow {
        val response = api.login("8f3cf5f09bd0c93a0528", "5447af3efb5afba3751aa6a0025e97affcf1a538", LocalStorage().code)
        if(response.isSuccessful) {
            emit(ResultData.Success(response.body()!!))
        }else{
            emit(ResultData.Message(response.message()))
        }
    }.catch { emit(ResultData.Error(it)) }

    suspend fun getUserInfo() = flow {
        val response = api.getUserProfileInfo()

        if (response.isSuccessful){
            emit(ResultData.Success(response.body()!!))
        }else{
            emit(ResultData.Message(response.message()))
        }
    }.catch { emit(ResultData.Error(it)) }

    suspend fun getUserRepo() = flow{
        val response = api.getUserRepo()
        if (response.isSuccessful){
            emit(ResultData.Success(response.body()!!))
        }else{
            emit(ResultData.Message(response.message()))
        }
    }.catch { emit(ResultData.Error(it)) }

    suspend fun searchByUserName(name:String) = flow {
        val response = api.searchUserName(name)
        if (response.isSuccessful){
            emit(ResultData.Success(response.body()!!))
        }else{
            emit(ResultData.Message(response.message()))
        }
    }.catch { emit(ResultData.Error(it)) }


    suspend fun searchByRepo(repo: String) = flow {
        val response = api.searchUserRepo(repo)
        if (response.isSuccessful){
            emit(ResultData.Success(response.body()!!))
        }else{
            emit(ResultData.Message(response.message()))
        }
    }.catch { emit(ResultData.Error(it)) }
}