package com.magdy.poststask.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magdy.poststask.data.models.ModelPostsData


@Dao
interface MyDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData (list: List<ModelPostsData>)

    @Query("select * from ModelPostsData")
    suspend fun getData () : List<ModelPostsData>

}