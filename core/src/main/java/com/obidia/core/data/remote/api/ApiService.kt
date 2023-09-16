package com.obidia.core.data.remote.api

import com.obidia.core.data.remote.response.ItemUserSearchResponse
import com.obidia.core.data.remote.response.UserDetailResponse
import com.obidia.core.data.remote.response.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

  @GET("users/{name}")
  suspend fun getDetailUser(
    @Path("name") name: String
  ): Response<UserDetailResponse>

  @GET("search/users")
  suspend fun searchUser(
    @Query("q") searchQuery: String?
  ): Response<UserSearchResponse>

  @GET("users/{username}/followers")
  suspend fun getListFollowers(
    @Path("username") username: String
  ): Response<ArrayList<ItemUserSearchResponse>>

  @GET("users/{username}/following")
  suspend fun getListFollowing(
    @Path("username") username: String
  ): Response<ArrayList<ItemUserSearchResponse>>
}