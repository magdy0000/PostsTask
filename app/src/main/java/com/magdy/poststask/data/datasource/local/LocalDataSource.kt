package com.magdy.poststask.data.datasource.local

import com.magdy.poststask.data.models.ModelPostsData
import com.magdy.poststask.domain.utils.Resource
import javax.inject.Inject

class LocalDataSource @Inject constructor (private val myDao: MyDao) : ILocalDataSource {
    override suspend fun getPosts(): Resource<List<ModelPostsData>> {
        return try {
            val data = myDao.getData()
            if (data.isEmpty()){
                Resource.error(Exception("No data found"))
            }else {
                Resource.success(data)
            }
        }catch (e : Exception){
            Resource.error(e)
        }
    }

    override suspend fun cachePosts(list: List<ModelPostsData>) {
        myDao.insertData(list)
    }
}