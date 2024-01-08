package com.magdy.poststask.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ModelPostsPresentation(
    val body: String,
    val title: String,
) : Parcelable