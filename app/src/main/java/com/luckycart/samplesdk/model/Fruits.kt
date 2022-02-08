package com.luckycart.samplesdk.model

import com.luckycart.samplesdk.utils.FakeData

data class Fruits(
    var name: String = "Fruits",
    var identifier: String = "fruits",
    var imageName: String = "fruits",
    var firstProduct: Product = FakeData.firstFruitsProduct,
    var secondProduct: Product = FakeData.secondFruitsProduct,
    var thirdProduct: Product = FakeData.thirdFruitsProduct,
    var fourthProduct: Product = FakeData.fourthFruitsProduct
)