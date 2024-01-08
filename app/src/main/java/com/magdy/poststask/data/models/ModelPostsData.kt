package com.magdy.poststask.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelPostsData(
    var body: String,
    @PrimaryKey
    val id: Int,
    val title: String,
    val userId: Int
)