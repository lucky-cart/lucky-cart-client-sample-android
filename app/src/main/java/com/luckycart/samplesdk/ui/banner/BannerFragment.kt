package com.luckycart.samplesdk.ui.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.*
import com.luckycart.samplesdk.ui.GetBannerState
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.ui.ShoppingFragment
import com.luckycart.samplesdk.utils.*
import kotlinx.android.synthetic.main.fragment_banner.*

class BannerFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var pageType = ""
    private var shopId = ""
    private var listProduct = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_banner, container, false)
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        shopId = arguments?.getString(INTENT_FRAGMENT_SHOP_ID).toString()
        pageType = arguments?.getString(INTENT_FRAGMENT_SHOP).toString()
        initView()
        initClickListener()
        listProduct.addAll(mainViewModel.updateListProduct(shopId))
        if (pageType == "homepage")
            mainViewModel.loadShopBanner(shopId)
        else mainViewModel.loadBannerCategory(shopId)
        mainViewModel.getBannerCategoryDetails.observe(viewLifecycleOwner, Observer { bannerState ->
            when (bannerState) {
                is GetBannerState.OnSuccess -> {
                    listProduct.add(
                        Product(
                            "banner",
                            "banner",
                            bannerState.response.image_url,
                            null,
                            0F
                        )
                    )
                    recycleBanner.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycleBanner.adapter = context?.let { BannerAdapter(it, listProduct) }
                }
                is GetBannerState.OnError -> {
                    recycleBanner.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycleBanner.adapter = context?.let { BannerAdapter(it, listProduct) }
                }
            }

        })
    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.getContext(it) }
    }

    private fun initView() {
        when (shopId) {
            CATEGORY_COFFE_ID -> title.text = Coffees().name
            CATEGORY_FRUITS_ID -> title.text = Fruit().name
            SHOP_HOME_PAGE_ID -> {
                title.text = CoffeeBrothers().brand.name
                btnCheckOut.visibility = View.GONE
                btnShop.visibility = View.VISIBLE
                txtPrice.visibility = View.GONE
            }
        }
    }

    private fun initClickListener() {
        btnShop.setOnClickListener {
            (context as MainActivity).showFragment(ShoppingFragment(), null, null)
        }
    }
}