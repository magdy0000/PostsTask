package com.magdy.poststask.di

import android.content.Context
import androidx.room.Room
import com.magdy.poststask.data.datasource.local.MyDao
import com.magdy.poststask.data.datasource.local.MyRoomDataBase
import com.magdy.poststask.data.datasource.network.ApiCalls
import com.magdy.poststask.data.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getRetrofit () : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    @Singleton
    @Provides
    fun getApiCall (retrofit: Retrofit) : ApiCalls {
        return retrofit.create(ApiCalls::class.java)
    }

    @Singleton
    @Provides
    fun getRoomInstance (@ApplicationContext context: Context) : MyRoomDataBase {
        return Room.databaseBuilder(context
            , MyRoomDataBase::class.java
            , "Posts Database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun getMyDao (roomDataBase :MyRoomDataBase) : MyDao {
        return roomDataBase.myDao()
    }
}