package com.example.kt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoritesRestaurantModel(
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val address: String,
    val id : String,
    val rating : Double,
    val image_url : String
): Parcelable
