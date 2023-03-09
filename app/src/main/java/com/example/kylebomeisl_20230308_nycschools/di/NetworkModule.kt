package com.example.kylebomeisl_20230308_nycschools.di

import com.example.kylebomeisl_20230308_nycschools.data.network.SATNetworkDataSource
import com.example.kylebomeisl_20230308_nycschools.data.network.SchoolNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun ProvidesHttpEngine(): HttpClientEngine {
        return CIO.create()
    }

    @Singleton
    @Provides
    fun ProvidesSchoolNetworkDataSource(engine: HttpClientEngine): SchoolNetworkDataSource {
        return SchoolNetworkDataSource(ktorEngine = engine)
    }

    @Singleton
    @Provides
    fun ProvidesSATNetworkDataSource(engine: HttpClientEngine): SATNetworkDataSource {
        return SATNetworkDataSource(engine)
    }

}