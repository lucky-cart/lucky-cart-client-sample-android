package com.luckycart.samplesdk.model

data class Product(
    var name: String,
    var identifier: String,
    var imageName: String,
    var brand: Brand?,
    var price: Float
)