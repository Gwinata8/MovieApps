package com.example.movieapps

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->

                val request = chain.request()
                    .newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMzFmNjY1YjRlMDRlNGM0NTkzYmJmZjFkZTlhZjhkNyIsIm5iZiI6MTc4MDQxMTczNS44NzgsInN1YiI6IjZhMWVlZDU3MTI1YTBiNjdlZGNhNWViMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Z14rHf5qWtJmdi2a3X9FAs8_l5fbNh7MNVMENMixorM")
                    .build()

                chain.proceed(request)
            }
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(
        retrofit: Retrofit
    ): MovieApiService {
        return retrofit.create(
            MovieApiService::class.java
        )
    }
}