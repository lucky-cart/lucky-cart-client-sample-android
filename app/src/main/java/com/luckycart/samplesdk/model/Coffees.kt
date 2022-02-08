package com.luckycart.samplesdk.model

import com.luckycart.samplesdk.utils.FakeData

data class Coffees(
    var name: String = "Coffees",
    var identifier: String = "coffees",
    var imageName: String = "coffees",
    var firstProduct: Product = FakeData.firstCoffeeProduct,
    var secondProduct: Product = FakeData.secondCoffeeProduct,
    var thirdProduct: Product = FakeData.thirdCoffeeProduct,
    var fourthProduct: Product = FakeData.fourthCoffeeProduct
)
