package com.example.kt.Api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusinessesItem(val distance: Double = 0.0,
                          val imageUrl: String = "",
                          val rating: Int = 0,
                          val coordinates: Coordinates,
                          val reviewCount: Int = 0,
                          val url: String = "",
                          val displayPhone: String = "",
                          val phone: String = "",
                          val price: String = "",
                          val name: String = "",
                          val alias: String = "",
                          val location: Location,
                          val id: String = "",
                          val categories: List<CategoriesItem>?,
                          val isClosed: Boolean = false): Parcelable