package com.tugaypamuk.app.onestask.di

import android.app.Application
import androidx.room.Room
import com.tugaypamuk.app.onestask.data.local.database.OnesDatabase
import com.tugaypamuk.app.onestask.utils.Const.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app : Application) : OnesDatabase{
        return Room.databaseBuilder(
            app,
            OnesDatabase::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

}