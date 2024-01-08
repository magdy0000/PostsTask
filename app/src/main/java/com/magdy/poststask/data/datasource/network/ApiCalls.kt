package com.magdy.poststask.data.datasource.network

import com.magdy.poststask.data.models.ModelPostsData
import retrofit2.http.GET

interface ApiCalls {

    @GET("posts")
    suspend fun getPost () : List<ModelPostsData>

}