package com.luckycart.samplesdk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.banner.ProductsAndBannerFragment
import com.luckycart.samplesdk.utils.CATEGORY_COFFE_ID
import com.luckycart.samplesdk.utils.CATEGORY_FRUITS_ID
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_CARD
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_CARD_TTC
import kotlinx.android.synthetic.main.fragment_shopping.*

class ShoppingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productsName = arguments?.getStringArrayList(INTENT_FRAGMENT_CARD)
        val productPrice = arguments?.getFloat(INTENT_FRAGMENT_CARD_TTC)
        imgCoffee.setOnClickListener {
            (context as MainActivity).showFragment(ProductsAndBannerFragment(), CATEGORY_COFFE_ID,null,productsName,productPrice)
        }
        imgFruit.setOnClickListener {
            (context as MainActivity).showFragment(ProductsAndBannerFragment(), CATEGORY_FRUITS_ID,null,productsName,productPrice)
        }
    }
}