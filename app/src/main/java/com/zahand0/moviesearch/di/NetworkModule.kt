package com.zahand0.moviesearch.di

import androidx.paging.ExperimentalPagingApi
import com.zahand0.moviesearch.data.local.FilmDatabase
import com.zahand0.moviesearch.data.remote.KinopoiskUnofficialAPI
import com.zahand0.moviesearch.data.repository.RemoteDataSourceImpl
import com.zahand0.moviesearch.domain.repository.RemoteDataSource
import com.zahand0.moviesearch.util.Constants
import com.zahand0.moviesearch.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .apply {
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("X-API-KEY", Constants.API_KEY)
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
            }
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKinopoiskUnofficialApi(retrofit: Retrofit): KinopoiskUnofficialAPI {
        return retrofit.create(KinopoiskUnofficialAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        kinopoiskUnofficialAPI: KinopoiskUnofficialAPI,
        filmDatabase: FilmDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            kinopoiskUnofficialAPI = kinopoiskUnofficialAPI,
            filmDatabase = filmDatabase
        )
    }
}