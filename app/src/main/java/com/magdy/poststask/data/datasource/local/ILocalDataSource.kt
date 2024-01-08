package com.magdy.poststask.data.datasource.local

import com.magdy.poststask.data.models.ModelPostsData
import com.magdy.poststask.domain.utils.Resource

interface ILocalDataSource {

    suspend fun getPosts(): Resource<List<ModelPostsData>>
    suspend fun cachePosts(list: List<ModelPostsData>)
}