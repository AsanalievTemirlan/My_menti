package com.example.mymenti.data.di

import android.content.Context
import androidx.room.Room
import com.example.mymenti.data.local.db.MentiDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MentiDataBase =
        Room.databaseBuilder(context, MentiDataBase::class.java, "MentiDataBase")
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideMentiDao(database: MentiDataBase) = database.mentiDao()
}