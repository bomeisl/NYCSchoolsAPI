package com.example.kylebomeisl_20230308_nycschools.di

import android.content.Context
import androidx.room.Room
import com.example.kylebomeisl_20230308_nycschools.data.databases.NYCDatabase
import com.example.kylebomeisl_20230308_nycschools.data.databases.SATDao
import com.example.kylebomeisl_20230308_nycschools.data.databases.SchoolDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun providesSchoolDao(database: NYCDatabase): SchoolDao {
        return database.schoolDao()
    }

    @Provides
    @Singleton
    fun providesSATDao(database: NYCDatabase): SATDao {
        return database.satDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): NYCDatabase {
        return Room.databaseBuilder(
            appContext,
            NYCDatabase::class.java,
            "nyc.db"
        ).build()
    }

}