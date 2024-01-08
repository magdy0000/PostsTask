package com.magdy.poststask.presentation.mappers

import com.magdy.poststask.domain.models.ModelPosts
import com.magdy.poststask.presentation.models.ModelPostsPresentation

fun ModelPosts.toPresentation () : ModelPostsPresentation {
    return ModelPostsPresentation(body, title)
}