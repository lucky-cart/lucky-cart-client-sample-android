package com.luckycart.samplesdk.model

data class Fruits(
    var name: String = "Fruits",
    var identifier: String = "fruits",
    var imageName: String = "fruits",
    var firstProduct: Product = Product(
        "Bananas",
        "bananas",
        "bananas",
        LocoBananas().brand,
        1.70F
    ),
    var secondProduct: Product = Product(
        "Coconut",
        "coconut",
        "coconut",
        LocoBananas().brand,
        2.85F
    ),
    var thirdProduct: Product = Product(
        "Red Apple",
        "apple.red",
        "applered",
        BringYaFruit().brand,
        4.95F
    ),
    var fourthProduct: Product = Product(
        "Green Apple",
        "apple.green",
        "applegreen",
        BringYaFruit().brand,
        4.45F
    )
)