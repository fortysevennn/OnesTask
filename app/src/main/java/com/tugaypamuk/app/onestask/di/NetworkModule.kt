package com.tugaypamuk.app.onestask.di

import com.google.gson.GsonBuilder
import com.tugaypamuk.app.onestask.data.local.database.OnesDatabase
import com.tugaypamuk.app.onestask.data.remote.api.OnesApi
import com.tugaypamuk.app.onestask.data.repository.OnesRepositoryImpl
import com.tugaypamuk.app.onestask.domain.repository.OnesRepository
import com.tugaypamuk.app.onestask.domain.use_cases.AccessLogUseCases
import com.tugaypamuk.app.onestask.domain.use_cases.approve.ApproveAccessLogUseCase
import com.tugaypamuk.app.onestask.domain.use_cases.get.GetAccessLogUseCase
import com.tugaypamuk.app.onestask.utils.Const.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideOnsApi() : OnesApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OnesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        serviceUrl : String
    ) : Retrofit{
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder().apply {
            baseUrl(serviceUrl)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttpClient)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient{

        val builder : OkHttpClient.Builder = OkHttpClient.Builder().apply {
            connectTimeout(90,TimeUnit.SECONDS)
            readTimeout(90,TimeUnit.SECONDS)
            writeTimeout(90,TimeUnit.SECONDS)
            cache(null)

        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideOnesRepository(
        api : OnesApi,
        db : OnesDatabase
    ) : OnesRepository{
        return OnesRepositoryImpl(
            api,
            db.onesLogDao,
            db.onesMailDao
        )
    }

    @Provides
    @Singleton
    fun provideAccessLogUseCases(repository: OnesRepository) : AccessLogUseCases{
        return AccessLogUseCases(
            approveAccessLogUseCase = ApproveAccessLogUseCase(repository),
            getAccessLogUseCase = GetAccessLogUseCase(repository)
        )
    }
}