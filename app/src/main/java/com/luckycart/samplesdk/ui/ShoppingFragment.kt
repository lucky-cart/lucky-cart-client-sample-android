package com.luckycart.samplesdk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.banner.BannerFragment
import com.luckycart.samplesdk.utils.CATEGORY_COFFE_ID
import com.luckycart.samplesdk.utils.CATEGORY_FRUITS_ID
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
        imgCoffee.setOnClickListener {
            (context as MainActivity).showFragment(BannerFragment(), CATEGORY_COFFE_ID,null)
        }
        imgFruit.setOnClickListener {
            (context as MainActivity).showFragment(BannerFragment(), CATEGORY_FRUITS_ID,null)
        }
    }
}