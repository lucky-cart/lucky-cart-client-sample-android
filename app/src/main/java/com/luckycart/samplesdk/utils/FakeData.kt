package com.luckycart.samplesdk.utils

import com.luckycart.samplesdk.model.Brand
import com.luckycart.samplesdk.model.Category
import com.luckycart.samplesdk.model.Product

class FakeData {

    companion object {

        val coffeeBrothers = Brand("Coffee Brothers", "coffee_brothers")
        val queensBeverages = Brand("Queens Beverage", "queens_beverage")
        val locoBananas = Brand("Loco Bananas", "loco_bananas")
        val bringYaFruit = Brand("Bring Ya Fruit", "bring_ya_fruit")
        val firstCoffeeProduct: Product = Product(
            "Ristretto", "ristretto", "coffee", coffeeBrothers, 3.90F
        )
        val secondCoffeeProduct: Product = Product(
            "Classic", "classic", "coffee", FakeData.coffeeBrothers, 2.95F
        )
        val thirdCoffeeProduct: Product = Product(
            "Blue Mountain", "blue_mountain", "coffee2", FakeData.queensBeverages, 4.95F
        )
        val fourthCoffeeProduct: Product = Product(
            "Costa Rica", "costa_rica", "coffee2", FakeData.queensBeverages, 4.45F
        )
        val firstFruitsProduct: Product = Product(
            "Bananas", "bananas", "bananas", FakeData.locoBananas, 1.70F
        )
        val secondFruitsProduct: Product = Product(
            "Coconut", "coconut", "coconut", FakeData.locoBananas, 2.85F
        )
        val thirdFruitsProduct: Product = Product(
            "Red Apple", "apple.red", "applered", FakeData.bringYaFruit, 4.95F
        )
        val fourthFruitsProduct: Product = Product(
            "Green Apple", "apple.green", "applegreen", FakeData.bringYaFruit, 4.45F
        )
        val coffees: Category = Category("Coffees","coffees", "coffees", listOf(firstCoffeeProduct, secondCoffeeProduct, thirdCoffeeProduct, fourthCoffeeProduct))
        val fruits: Category = Category("Fruits","fruits", "fruits", listOf(firstFruitsProduct, secondFruitsProduct, thirdFruitsProduct, fourthFruitsProduct))
    }

}