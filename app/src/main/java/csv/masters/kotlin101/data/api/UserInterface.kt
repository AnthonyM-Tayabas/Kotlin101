package csv.masters.kotlin101.data.api

import csv.masters.kotlin101.data.UserListResponse
import csv.masters.kotlin101.data.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInterface {
    @GET("/api/users?page=2")
    suspend fun getAllUsers(): Response<UserListResponse>

    @GET("/api/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<UserResponse>
}