package com.luckycart.samplesdk.extension

import android.util.DisplayMetrics

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()