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
import com.luckycart.model.Banner
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.*
import com.luckycart.samplesdk.ui.CartEventName
import com.luckycart.samplesdk.ui.GetBannerState
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.ui.ShoppingFragment
import com.luckycart.samplesdk.ui.basket.ProductsBasketFragment
import com.luckycart.samplesdk.utils.*
import kotlinx.android.synthetic.main.fragment_banner.*

class ProductsAndBannerFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var pageType = ""
    private var pageId = ""
    private var listProducts = ArrayList<Product>()
    private var totalPrice: Float? = 0.0F
    private var productsBasket: Int = 0
    private var listProductsBasket = ArrayList<Product>()

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
        arguments?.getParcelableArrayList<Product>(INTENT_FRAGMENT_CART)?.let {
            listProductsBasket.addAll(
                it
            )
        }
        totalPrice = arguments?.getFloat(INTENT_FRAGMENT_CART_TTC)
        pageId = arguments?.getString(INTENT_FRAGMENT_SHOP_ID).toString()
        pageType = arguments?.getString(INTENT_FRAGMENT_SHOP_TYPE).toString()
        listProducts.addAll(mainViewModel.updateProductOfShopId(pageId, pageType))
        productsBasket = listProductsBasket.size
        val listBanner = ArrayList<Banner>()
        mainViewModel.pageDisplayed(BANNER_CATEGORIES,pageId,  "categories card")
        mainViewModel.postEventState.observe(viewLifecycleOwner) { eventName->
        if(eventName == CartEventName.PageViewed){

            initView()
            initClickListener()
         }
        }

        mainViewModel.getBannerDetails.observe(viewLifecycleOwner) { bannerState ->
            when (bannerState) {
                is GetBannerState.OnSuccess -> {
                    listBanner.add(bannerState.banner)

                    val adapter =  ProductsAndBannerAdapter(requireActivity(), listProducts, listBanner){
                        mainViewModel.bannerClicked(it)
                    }

                    recycleBanner.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycleBanner.adapter = adapter
                    adapter.addProductlistener = object : ProductsAndBannerAdapter.AddProductToBasket {
                        override fun onItemChoose(product: Product) {
                            totalPrice = totalPrice?.plus(product.price)
                            productsBasket += 1
                            txtPrice.text = getString(R.string.price, totalPrice.toString())
                            txtProduct.text =
                                getString(R.string.product, productsBasket.toString())
                            listProductsBasket.add(product)
                        }
                    }
                }

                is GetBannerState.OnError -> {
                    Log.d("Error", bannerState.error)
                }
            }

        }
    }

    private fun setUpViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.setContext(it) }
    }

    private fun initView() {
            if (pageType == BANNER_HOMEPAGE || pageType == SHOP_COFFEE || pageType == SHOP_FRUITS) {
                btnCheckOut.visibility = View.GONE
                btnShop.visibility = View.VISIBLE
                txtPrice.visibility = View.GONE
                txtProduct.visibility = View.GONE
                mainViewModel.loadShopBanner(pageId, pageType)
            } else {
                mainViewModel.loadBannerCategory(pageId)
            }
        when (pageType) {
            BANNER_CATEGORIES -> {
                if (pageId == CATEGORY_COFFEE_ID) {
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
            }
            SHOP_COFFEE -> {
                title.text = getString(R.string.coffee_promotion, FakeData.coffeeBrothers.name)
                context?.let { title.setTextColor(ContextCompat.getColor(it, R.color.blue1)) }
            }
            SHOP_FRUITS -> {
                title.text = getString(R.string.coffee_promotion, FakeData.queensBeverages.name)
                context?.let { title.setTextColor(ContextCompat.getColor(it, R.color.blue1)) }
            }
        }

        val adapter = ProductsAndBannerAdapter(requireActivity(), listProducts, null){

        }
        recycleBanner.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycleBanner.adapter = adapter
        adapter.addProductlistener = object : ProductsAndBannerAdapter.AddProductToBasket {
            override fun onItemChoose(product: Product) {
                totalPrice = totalPrice?.plus(product.price)
                productsBasket += 1
                txtPrice.text = getString(R.string.price, totalPrice.toString())
                txtProduct.text = getString(R.string.product, productsBasket.toString())
                listProductsBasket.add(product)
            }
        }
    }

    private fun initClickListener() {
        btnShop.setOnClickListener {
            (context as MainActivity).showFragment(
                ShoppingFragment(),
                null,
                null,
                listProductsBasket,
                totalPrice
            )

        }
        btnCheckOut.setOnClickListener {
            (context as MainActivity).showFragment(
                ProductsBasketFragment(),
                null,
                null,
                listProductsBasket,
                totalPrice
            )
        }
    }
}