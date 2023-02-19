package com.example.kt.Api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    val businesses: List<Business>,
    val region: Region,
    val total: Int
):Parcelable