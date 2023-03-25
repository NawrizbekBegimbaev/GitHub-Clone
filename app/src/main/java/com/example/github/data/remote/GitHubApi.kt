package com.example.github.data.remote

import com.example.github.data.models.GetUserProfileInfoResponse
import com.example.github.data.models.GetUserRepo
import com.example.github.data.models.SearchByUserName.SearchByUserNameResponse
import com.example.github.data.models.LoginResponseData
import com.example.github.data.models.SearchByRepositoryResponse
import retrofit2.Response
import retrofit2.http.*

interface GitHubApi {

    @Headers("Accept: application/json")
    @POST("https://github.com/login/oauth/access_token")
    @FormUrlEncoded
    suspend fun login(
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
        @Field("code") code: String
    ): Response<LoginResponseData>


    @GET("/user")
    suspend fun getUserProfileInfo() : Response<GetUserProfileInfoResponse>

    @GET("/user/repos")
    suspend fun getUserRepo() : Response<List<GetUserRepo>>

    @GET("/search/users?q")
    suspend fun searchUserName(@Query("q") name: String) : Response<SearchByUserNameResponse>

    @GET("/search/repositories?q")
    suspend fun searchUserRepo(@Query("q") repo: String) : Response<SearchByRepositoryResponse>







}