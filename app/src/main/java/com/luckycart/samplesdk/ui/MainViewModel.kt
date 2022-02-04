package com.luckycart.samplesdk.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luckycart.local.Prefs
import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import com.luckycart.model.LCAuthorization
import com.luckycart.samplesdk.model.*
import com.luckycart.samplesdk.utils.*
import com.luckycart.sdk.LuckCartSDK
import com.luckycart.sdk.LuckyCartListenerCallback

class MainViewModel : ViewModel(), LuckyCartListenerCallback {
    private lateinit var mContext: Context
    private var luckyCartSDK: LuckCartSDK? = null
    private lateinit var availableBanners: Banners
    var getBannerDetails: MutableLiveData<GetBannerState> = MediatorLiveData()
    var getBannerCategoryDetails: MutableLiveData<GetBannerState> = MediatorLiveData()
    var getBannerCategory = false
    var shopID = ""


    fun initLuckyCart() {
        val auth = LCAuthorization(AUTH_KEY, "")
        luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.init(auth, null)
        luckyCartSDK?.setUser(CUSTOMER_ID)
        luckyCartSDK?.setActionListener(this)
        luckyCartSDK?.listAvailableBanners()
    }

    override fun listAvailableBanners(banners: Banners) {
        availableBanners = banners
        loadBannerHomePage()
    }

    override fun getBannerDetails(banners: BannerDetails) {
        Log.d("getBannerDetails", "" + banners)
        if (banners.name != null) {
            if (getBannerCategory) {
                getBannerCategoryDetails.value = GetBannerState.OnSuccess(banners)
            } else getBannerDetails.value = GetBannerState.OnSuccess(banners)
        } else {
            getBannerCategoryDetails.value = GetBannerState.OnError("error")
        }
    }

    private fun loadBannerHomePage() {
        getBannerCategory = false
        availableBanners.homepage.forEach { homePage ->
            luckyCartSDK?.getBannerDetails(BANNER_HOMEPAGE, homePage)

        }
    }

    fun loadBannerCategory(pageId: String) {
        shopID = pageId
        getBannerCategory = true
        if (luckyCartSDK == null)
            luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.setActionListener(this)
        Prefs(mContext).banners.categories.forEach {
            if (it.contains(shopID))
                luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, it)

        }

    }

    fun loadShopBanner(pageId: String) {
        shopID = pageId
        getBannerCategory = true
        if (luckyCartSDK == null)
            luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.setActionListener(this)
        luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, "banner_$shopID")

    }

    fun getContext(context: Context) {
        mContext = context
    }

    fun updateListProduct(shopId: String): ArrayList<Product> {
        val listProduct = ArrayList<Product>()
        when (shopId) {
            CATEGORY_COFFE_ID -> {
                val productCoffee = Coffees()
                listProduct.add(productCoffee.firstProduct)
                listProduct.add(productCoffee.secondProduct)
                listProduct.add(productCoffee.thirdProduct)
                listProduct.add(productCoffee.firthProduct)
            }
            CATEGORY_FRUITS_ID -> {
                val productFruit = Fruit()
                listProduct.add(productFruit.firstProduct)
                listProduct.add(productFruit.secondProduct)
                listProduct.add(productFruit.thirdProduct)
                listProduct.add(productFruit.firthProduct)
            }
            SHOP_HOME_PAGE_ID -> {
                if (Coffees().firstProduct.brand == CoffeeBrothers().brand)
                    listProduct.add(Coffees().firstProduct)
                if (Coffees().secondProduct.brand == CoffeeBrothers().brand)
                    listProduct.add(Coffees().secondProduct)
                if (Coffees().thirdProduct.brand == CoffeeBrothers().brand)
                    listProduct.add(Coffees().thirdProduct)
                if (Coffees().firthProduct.brand == CoffeeBrothers().brand)
                    listProduct.add(Coffees().firthProduct)
            }
        }
        return listProduct
    }
}