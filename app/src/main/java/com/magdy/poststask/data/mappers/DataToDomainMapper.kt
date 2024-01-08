package com.magdy.poststask.data.mappers

import com.magdy.poststask.data.models.ModelPostsData
import com.magdy.poststask.domain.models.ModelPosts

fun ModelPostsData.toDomain () : ModelPosts {
    return ModelPosts(body, title, userId)
}