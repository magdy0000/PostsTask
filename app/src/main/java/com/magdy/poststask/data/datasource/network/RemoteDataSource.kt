package com.magdy.poststask.data.datasource.network

import com.magdy.poststask.data.models.ModelPostsData
import com.magdy.poststask.domain.utils.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiCalls: ApiCalls): IRemoteDataSource {
    override suspend fun getPosts(): Resource<List<ModelPostsData>> {
        return try {
            val data = apiCalls.getPost()
            Resource.success(data)
        }catch (e :Exception){
            Resource.error(e)
        }
    }
}