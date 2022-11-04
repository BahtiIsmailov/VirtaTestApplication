package com.example.testapplication.di

import android.content.Context
import com.example.testapplication.apibuilder.RetrofitFactory
import com.example.testapplication.network.remotedatasource.Api
import com.example.testapplication.network.remotedatasource.ApiRepository
import com.example.testapplication.network.remotedatasource.ApiRepositoryImpl
import com.example.testapplication.presentation.login.domain.LogginInteractor
import com.example.testapplication.presentation.login.domain.LoginInteractorImpl
import com.example.testapplication.presentation.station.domain.StationsInteractor
import com.example.testapplication.presentation.station.domain.StationsInteractorImpl
import com.example.testapplication.presentation.station.resource_provider.ResourceProvider
import com.example.testapplication.utils.prefs.SharedWorker
import com.example.testapplication.utils.prefs.SharedWorkerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): Api {
        return RetrofitFactory().create()
    }


    @Singleton
    @Provides
    fun provideApiRepository(api: Api): ApiRepository{
        return ApiRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideSharedWorker(@ApplicationContext context: Context): SharedWorker{
        return SharedWorkerImpl(context)
    }

    @Singleton
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context):ResourceProvider{
        return ResourceProvider(context)
    }

    @Singleton
    @Provides
    fun provideInteractor(apiRepository: ApiRepository,sharedWorker: SharedWorker): LogginInteractor {
        return LoginInteractorImpl(apiRepository,sharedWorker)
    }

    @Singleton
    @Provides
    fun provideStationsInteractor(apiRepository: ApiRepository):StationsInteractor{
        return StationsInteractorImpl(apiRepository)
    }


}