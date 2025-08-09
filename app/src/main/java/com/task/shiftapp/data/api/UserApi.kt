package com.task.shiftapp.data.api

import com.task.shiftapp.data.model.UserResponse
import retrofit2.http.GET

interface UserApi {
    @GET("api/")
    suspend fun getRandomUser(): UserResponse
}