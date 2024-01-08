package com.magdy.poststask.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.magdy.poststask.data.models.ModelPostsData

@Database(entities = [ModelPostsData::class], version = 1)
abstract class  MyRoomDataBase : RoomDatabase() {
    abstract fun myDao (): MyDao

}