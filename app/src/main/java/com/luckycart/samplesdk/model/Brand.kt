package com.luckycart.samplesdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Brand(val name: String, val identifier: String) : Parcelable