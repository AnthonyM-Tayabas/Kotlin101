package csv.masters.kotlin101.data.api

import csv.masters.kotlin101.data.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserInterface {
    @GET("/api/users?page=2")
    suspend fun getAllUsers(): Response<UserResponse>

    @GET("/api/users")
    suspend fun getUser(id: String): Response<UserResponse>
}