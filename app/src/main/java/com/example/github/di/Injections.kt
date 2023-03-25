package com.example.github.di

import android.util.Log
import com.example.github.data.remote.GitHubApi
import com.example.github.domain.MainRepository
import com.example.github.presentation.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appmodule = module {
    single<MainRepository> {
        MainRepository(api = get())
    }

    single<GitHubApi> {
        provideApi(retrofit = get())
    }
}

val remoteModule = module {
    single<GithubInterceptor> {
        GithubInterceptor()
    }
    single<Retrofit> {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(
            GithubInterceptor()
        ).build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com")
            .client(client)
            .build()
        retrofit
    }

}

val viewModelModule = module {
    viewModel<LoginViewModel>{
        LoginViewModel(repo = get())
    }
    viewModel<GetUserInfoViewModel>{
        GetUserInfoViewModel(repo = get())
    }
    viewModel<GetUserRepositoriesViewModel>{
        GetUserRepositoriesViewModel(repo = get())
    }
    viewModel<SearchByUserNameViewModel>{
        SearchByUserNameViewModel(repo = get())
    }
    viewModel<SearchByUserRepoViewModel>{
        SearchByUserRepoViewModel(repo = get())
    }



}

fun provideApi(retrofit: Retrofit):  GitHubApi {
    return retrofit.create(GitHubApi::class.java)
}