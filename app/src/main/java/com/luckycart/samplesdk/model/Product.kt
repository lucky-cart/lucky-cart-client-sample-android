package com.luckycart.samplesdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var name: String?,
    var identifier: String?,
    var imageName: String?,
    var brand: Brand?,
    var price: Float
) : Parcelable