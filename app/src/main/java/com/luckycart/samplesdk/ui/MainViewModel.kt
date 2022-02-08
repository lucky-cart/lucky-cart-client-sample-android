package com.luckycart.samplesdk.ui

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luckycart.local.Prefs
import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import com.luckycart.model.LCAuthorization
import com.luckycart.model.TransactionResponse
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
    private var getBannerCategory: Boolean = false
    private var shopID: String = ""

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
        if (banners.name != null) {
            if (getBannerCategory) {
                getBannerCategoryDetails.value = GetBannerState.OnSuccess(banners)
            } else getBannerDetails.value = GetBannerState.OnSuccess(banners)
        } else {
            getBannerCategoryDetails.value = GetBannerState.OnError("error")
        }
    }

    override fun sendCard(transactionResponse: TransactionResponse) {
    }

    private fun loadBannerHomePage() {
        getBannerCategory = false
        availableBanners.homepage?.forEach { homePage ->
            luckyCartSDK?.getBannerDetails(BANNER_HOMEPAGE, homePage)

        }
    }

    fun loadBannerCategory(pageId: String) {
        shopID = pageId
        getBannerCategory = true
        if (luckyCartSDK == null)
            luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.setActionListener(this)
        Prefs(mContext).banners.categories?.forEach {
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
        Prefs(mContext).banners.homepage?.forEach {
            luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, it + "_$shopID")
        }


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
                listProduct.add(productCoffee.fourthProduct)
            }
            CATEGORY_FRUITS_ID -> {
                val productFruit = Fruits()
                listProduct.add(productFruit.firstProduct)
                listProduct.add(productFruit.secondProduct)
                listProduct.add(productFruit.thirdProduct)
                listProduct.add(productFruit.fourthProduct)
            }
            SHOP_HOME_PAGE_ID -> {
                if (Coffees().firstProduct.brand == CoffeeBrothers().brand)
                    listProduct.add(Coffees().firstProduct)
                if (Coffees().secondProduct.brand == CoffeeBrothers().brand)
                    listProduct.add(Coffees().secondProduct)
                if (Coffees().thirdProduct.brand == CoffeeBrothers().brand)
                    listProduct.add(Coffees().thirdProduct)
                if (Coffees().fourthProduct.brand == CoffeeBrothers().brand)
                    listProduct.add(Coffees().fourthProduct)
            }
        }
        return listProduct
    }
}