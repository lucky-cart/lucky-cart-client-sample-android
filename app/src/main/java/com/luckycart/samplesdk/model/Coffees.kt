package com.luckycart.samplesdk.model

data class Coffees(
    var name: String = "Coffees",
    var identifier: String = "coffees",
    var imageName: String = "coffees",
    var firstProduct: Product = Product(
        "Ristretto",
        "ristretto",
        "coffee",
        CoffeeBrothers().brand,
        3.90F
    ),
    var secondProduct: Product = Product(
        "Classic",
        "classic",
        "coffee",
        CoffeeBrothers().brand,
        2.95F
    ),
    var thirdProduct: Product = Product(
        "Blue Mountain",
        "blue_mountain",
        "coffee2",
        QueensBeverages().brand,
        4.95F
    ),
    var fourthProduct: Product = Product(
        "Costa Rica",
        "costa_rica",
        "coffee2",
        QueensBeverages().brand,
        4.45F
    )
)
