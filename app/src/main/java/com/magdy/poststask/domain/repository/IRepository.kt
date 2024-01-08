package com.magdy.poststask.domain.repository

import com.magdy.poststask.domain.models.ModelPosts
import com.magdy.poststask.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IRepository {

    suspend fun getPosts(): Flow<Resource<List<ModelPosts>>>

}