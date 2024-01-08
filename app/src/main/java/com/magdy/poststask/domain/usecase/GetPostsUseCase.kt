package com.magdy.poststask.domain.usecase

import com.magdy.poststask.domain.models.ModelPosts
import com.magdy.poststask.domain.repository.IRepository
import com.magdy.poststask.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val iRepository: IRepository) {

    suspend fun getPosts(): Flow<Resource<List<ModelPosts>>> {
        return iRepository.getPosts()
    }
}