package com.pastitechnicaltest.pastimovieapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
) : Parcelable
