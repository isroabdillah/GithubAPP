package com.isrodicoding.githubapp.api

import com.isrodicoding.githubapp.data.DetailUser
import com.isrodicoding.githubapp.data.GithubUser
import com.isrodicoding.githubapp.data.SearchUsers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {

    @GET("users")
    fun getUsers(
        @Header("Authorization") token: String
    ): Call<List<GithubUser>>

    @GET("search/users")
    fun getUserSearch(
        @Query("q") login: String,
        @Header("Authorization") token: String
    ): Call<SearchUsers>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<DetailUser>

    @GET("users/{login}/followers")
    fun getAllFollowers(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<List<GithubUser>>

    @GET("users/{login}/following")
    fun getAllFollowings(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<List<GithubUser>>
}