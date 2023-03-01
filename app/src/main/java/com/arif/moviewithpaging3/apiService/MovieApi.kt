package com.arif.moviewithpaging3.apiService

import com.arif.moviewithpaging3.Const.Companion.API_KEY
import com.arif.moviewithpaging3.entity.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("3/movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovie(
        @Query("language") language: String,
        @Query("page") page : Int) : Response<Movie>

    @GET("3/movie/upcoming?api_key=$API_KEY")
    suspend fun getUpcomingMovie(
        @Query("language") language: String,
        @Query("page") page : Int): Response<Movie>

    @GET("3/movie/top_rated?api_key=$API_KEY")
    suspend fun getTopRatedMovie(
        @Query("language") language: String,
        @Query("page") page : Int): Response<Movie>
}