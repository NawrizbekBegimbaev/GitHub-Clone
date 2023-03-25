package com.example.github.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponseData(
    val access_token: String,
    val scope: String,
    val token_type: String
)