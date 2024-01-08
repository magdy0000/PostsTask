package com.magdy.poststask.data.datasource.network

import com.magdy.poststask.data.models.ModelPostsData
import com.magdy.poststask.domain.utils.Resource

interface IRemoteDataSource {

    suspend fun getPosts(): Resource<List<ModelPostsData>>

}