package com.luckycart.samplesdk.ui.banner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckycart.model.BannerDetails
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.*
import com.luckycart.samplesdk.ui.GetBannerState
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.ui.ShoppingFragment
import com.luckycart.samplesdk.ui.card.CardFragment
import com.luckycart.samplesdk.utils.*
import kotlinx.android.synthetic.main.fragment_banner.*

class ProductsAndBannerFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var pageType = ""
    private var shopId = ""
    private var listProduct = ArrayList<Product>()
    private var priceProduct: Float = 0.0F
    private var productAddedToCard: Int = 0
    private var listShopping = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_banner, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        val shopProductName = arguments?.getStringArrayList(INTENT_FRAGMENT_CARD)
        val shopProductPrice = arguments?.getFloat(INTENT_FRAGMENT_CARD_TTC)
        shopId = arguments?.getString(INTENT_FRAGMENT_SHOP_ID).toString()
        pageType = arguments?.getString(INTENT_FRAGMENT_SHOP_TYPE).toString()
        listProduct.addAll(mainViewModel.updateProductOfShopId(shopId, pageType))
        if (shopProductName != null) {
            productAddedToCard = shopProductName.size
            listShopping.addAll(shopProductName)
        }
        if (shopProductPrice != null) {
            priceProduct = shopProductPrice
        }
        initView()
        initClickListener()
        val listBanner = ArrayList<BannerDetails>()
        mainViewModel.getBannerCategoryDetails.observe(viewLifecycleOwner, { bannerState ->
            when (bannerState) {
                is GetBannerState.OnSuccess -> {
                    listBanner.add(bannerState.response)
                    val adapter =
                        context?.let {
                            ProductsAndBannerAdapter(
                                it,
                                pageType,
                                listProduct,
                                listBanner
                            )
                        }
                    recycleBanner.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycleBanner.adapter = adapter
                    adapter?.listener = object : ProductsAndBannerAdapter.AddProductToCard {
                        override fun onItemChoose(product: Product) {
                            priceProduct += product.price
                            productAddedToCard += 1
                            txtPrice.text = getString(R.string.price, priceProduct.toString())
                            txtProduct.text =
                                getString(R.string.product, productAddedToCard.toString())
                            listShopping.add(product.name)
                        }

                    }
                }
                is GetBannerState.OnError -> {
                    Log.d("Error", bannerState.error)
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
        if (pageType == BANNER_HOMEPAGE || pageType == SHOP_COFFEE || pageType == SHOP_FRUITS)
            mainViewModel.loadShopBanner(shopId, pageType)
        else mainViewModel.loadBannerCategory(shopId)
        when (pageType) {
            BANNER_CATEGORIES -> {
                if (shopId == CATEGORY_COFFEE_ID) {
                    title.text = FakeData.coffees.name
                    pageType = SHOP_COFFEE
                } else {
                    title.text = FakeData.fruits.name
                    pageType = SHOP_FRUITS
                }
            }
            BANNER_HOMEPAGE -> {
                title.text = getString(R.string.coffee_promotion, FakeData.coffeeBrothers.name)
                context?.let { title.setTextColor(ContextCompat.getColor(it, R.color.blue1)) }
                btnCheckOut.visibility = View.GONE
                btnShop.visibility = View.VISIBLE
                txtPrice.visibility = View.GONE
                txtProduct.visibility = View.GONE
            }
            SHOP_COFFEE -> {
                title.text = getString(R.string.coffee_promotion, FakeData.coffeeBrothers.name)
                context?.let { title.setTextColor(ContextCompat.getColor(it, R.color.blue1)) }
                btnCheckOut.visibility = View.GONE
                btnShop.visibility = View.VISIBLE
                txtPrice.visibility = View.GONE
                txtProduct.visibility = View.GONE
            }
            SHOP_FRUITS -> {
                title.text = getString(R.string.coffee_promotion, FakeData.queensBeverages.name)
                context?.let { title.setTextColor(ContextCompat.getColor(it, R.color.blue1)) }
                btnCheckOut.visibility = View.GONE
                btnShop.visibility = View.VISIBLE
                txtPrice.visibility = View.GONE
                txtProduct.visibility = View.GONE
            }
        }
        val adapter = context?.let { ProductsAndBannerAdapter(it, pageType, listProduct, null) }
        recycleBanner.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycleBanner.adapter = adapter
        adapter?.listener = object : ProductsAndBannerAdapter.AddProductToCard {
            override fun onItemChoose(product: Product) {
                priceProduct += product.price
                productAddedToCard += 1
                txtPrice.text = getString(R.string.price, priceProduct.toString())
                txtProduct.text = getString(R.string.product, productAddedToCard.toString())
                listShopping.add(product.name)
            }

        }
    }

    private fun initClickListener() {
        btnShop.setOnClickListener {
            (context as MainActivity).showFragment(
                ShoppingFragment(),
                null,
                null,
                listShopping,
                priceProduct
            )
        }
        btnCheckOut.setOnClickListener {
            (context as MainActivity).showFragment(
                CardFragment(),
                null,
                null,
                listShopping,
                priceProduct
            )
        }
    }
}