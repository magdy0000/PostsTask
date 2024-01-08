package com.magdy.poststask.data.repository

import android.util.Log
import com.magdy.poststask.data.datasource.local.ILocalDataSource
import com.magdy.poststask.data.datasource.network.IRemoteDataSource
import com.magdy.poststask.data.mappers.toDomain
import com.magdy.poststask.domain.models.ModelPosts
import com.magdy.poststask.domain.repository.IRepository
import com.magdy.poststask.domain.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository constructor(
    private val iLocalDataSource: ILocalDataSource,
    private val iRemoteDataSource: IRemoteDataSource
) : IRepository {

    override suspend fun getPosts(): Flow<Resource<List<ModelPosts>>> {
        return flow {
            val localData = iLocalDataSource.getPosts()
            if (localData.isSuccess())
                emit(Resource.success(localData.getSuccessData().map { it.toDomain() }))

            val remoteData = iRemoteDataSource.getPosts()
            if (remoteData.isSuccess()) {
                iLocalDataSource.cachePosts(remoteData.getSuccessData())
                //ui will not receive this emit if remote data same as local data
                emit(Resource.success(remoteData.getSuccessData().map { it.toDomain() }))
            } else {
                emit(Resource.error(Exception(remoteData.getErrorMessage())))
            }
        }
    }
}