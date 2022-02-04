package com.luckycart.samplesdk.model

data class Brand(val name: String, val identifier: String)
data class CoffeeBrothers(var brand: Brand = Brand("Coffee Brothers", "coffee_brothers"))
data class QueensBeverages(var brand: Brand = Brand("Queens Beverage", "queens_beverage"))
data class LocoBananas(var brand: Brand = Brand("Loco Bananas", "loco_bananas"))
data class BringYaFruit(var brand: Brand = Brand("Bring Ya Fruit", "bring_ya_fruit"))
