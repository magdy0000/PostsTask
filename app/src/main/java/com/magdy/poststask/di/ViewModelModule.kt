package com.magdy.poststask.di

import com.magdy.poststask.data.datasource.local.ILocalDataSource
import com.magdy.poststask.data.datasource.local.LocalDataSource
import com.magdy.poststask.data.datasource.local.MyDao
import com.magdy.poststask.data.datasource.network.ApiCalls
import com.magdy.poststask.data.datasource.network.IRemoteDataSource
import com.magdy.poststask.data.datasource.network.RemoteDataSource
import com.magdy.poststask.data.repository.Repository
import com.magdy.poststask.domain.repository.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun getRemoteDataSource(apiCalls: ApiCalls): IRemoteDataSource {
        return RemoteDataSource(apiCalls)
    }

    @Provides
    fun getLocalDataSource(myDao: MyDao): ILocalDataSource {
        return LocalDataSource(myDao)
    }

    @Provides
    fun getRepository(
        iLocalDataSource: ILocalDataSource,
        iRemoteDataSource: IRemoteDataSource
    ): IRepository {
        return Repository(iLocalDataSource,iRemoteDataSource)
    }

}