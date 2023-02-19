package com.example.kt.Api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Location(
    val address1: String,
    val address2: String,
    val address3: @RawValue Any,
    val city: String,
    val country: String,
    val display_address: List<String>,
    val state: String,
    val zip_code: String
):Parcelable