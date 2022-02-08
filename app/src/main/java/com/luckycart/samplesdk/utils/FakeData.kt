package com.luckycart.samplesdk.utils

import com.luckycart.samplesdk.model.Brand
import com.luckycart.samplesdk.model.Category
import com.luckycart.samplesdk.model.Product

class FakeData {

    companion object {

        val coffeeBrothers = Brand("Coffee Brothers", "coffee_brothers")
        private val queensBeverages = Brand("Queens Beverage", "queens_beverage")
        private val locoBananas = Brand("Loco Bananas", "loco_bananas")
        private val bringYaFruit = Brand("Bring Ya Fruit", "bring_ya_fruit")
        private val firstCoffeeProduct: Product = Product(
            "Ristretto", "ristretto", "coffee", coffeeBrothers, 3.90F
        )
        private val secondCoffeeProduct: Product = Product(
            "Classic", "classic", "coffee", coffeeBrothers, 2.95F
        )
        private val thirdCoffeeProduct: Product = Product(
            "Blue Mountain", "blue_mountain", "coffee2", queensBeverages, 4.95F
        )
        private val fourthCoffeeProduct: Product = Product(
            "Costa Rica", "costa_rica", "coffee2", queensBeverages, 4.45F
        )
        private val firstFruitsProduct: Product = Product(
            "Bananas", "bananas", "bananas", locoBananas, 1.70F
        )
        private val secondFruitsProduct: Product = Product(
            "Coconut", "coconut", "coconut", locoBananas, 2.85F
        )
        private val thirdFruitsProduct: Product = Product(
            "Red Apple", "apple.red", "applered", bringYaFruit, 4.95F
        )
        private val fourthFruitsProduct: Product = Product(
            "Green Apple", "apple.green", "applegreen", bringYaFruit, 4.45F
        )
        val coffees: Category = Category("Coffees","coffees", "coffees", listOf(firstCoffeeProduct, secondCoffeeProduct, thirdCoffeeProduct, fourthCoffeeProduct))
        val fruits: Category = Category("Fruits","fruits", "fruits", listOf(firstFruitsProduct, secondFruitsProduct, thirdFruitsProduct, fourthFruitsProduct))
    }

}