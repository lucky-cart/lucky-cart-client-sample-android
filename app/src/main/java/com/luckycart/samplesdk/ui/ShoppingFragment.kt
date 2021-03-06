package com.luckycart.samplesdk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Product
import com.luckycart.samplesdk.ui.banner.ProductsAndBannerFragment
import com.luckycart.samplesdk.utils.*
import kotlinx.android.synthetic.main.fragment_shopping.*

class ShoppingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productsBasket = arguments?.getParcelableArrayList<Product>(INTENT_FRAGMENT_CART)
        val productPrice = arguments?.getFloat(INTENT_FRAGMENT_CART_TTC)
        imgCoffee.setOnClickListener {
            (context as MainActivity).showFragment(
                ProductsAndBannerFragment(),
                CATEGORY_COFFEE_ID,
                BANNER_CATEGORIES,
                productsBasket,
                productPrice
            )
        }
        imgFruit.setOnClickListener {
            (context as MainActivity).showFragment(
                ProductsAndBannerFragment(),
                CATEGORY_FRUITS_ID,
                BANNER_CATEGORIES,
                productsBasket,
                productPrice
            )
        }
    }
}