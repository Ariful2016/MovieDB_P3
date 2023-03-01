package com.arif.moviewithpaging3.di

import com.arif.moviewithpaging3.Const.Companion.BASE_URL
import com.arif.moviewithpaging3.apiService.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {



    @Provides
    @Singleton
    fun providesApiService() : MovieApi{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MovieApi::class.java)
    }
}