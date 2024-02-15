package com.example.e_notes

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("user/login")
    fun getData(): Call<MyData>

    @POST("user/api/login")
    fun login(@Body loginRequest: LoginRequestData): Call<MyData>

    @POST("user/api/signup")
    fun signUp(@Body signUpRequestData: SignUpRequestData): Call<MyData>
}
