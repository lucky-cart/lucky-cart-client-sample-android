package com.luckycart.samplesdk.ui

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luckycart.local.Prefs
import com.luckycart.model.*
import com.luckycart.retrofit.BannerExperienceState
import com.luckycart.samplesdk.ApplicationSampleLuckyCart
import com.luckycart.samplesdk.model.Product
import com.luckycart.model.Product as SDKProduct
import com.luckycart.samplesdk.utils.*
import com.luckycart.sdk.LuckCartSDK
import com.luckycart.sdk.LuckyCartListenerCallback
import java.util.Date
import kotlin.collections.ArrayList

class MainViewModel : ViewModel(), LuckyCartListenerCallback {

    private lateinit var mContext: Context
    var luckyCartSDK: LuckCartSDK? = null

    var getBannerDetails: MutableLiveData<GetBannerState> = MediatorLiveData()
    var bannerExperienceState: MutableLiveData<BannerExperienceState> = MediatorLiveData()
    private var getBannerCategory: Boolean = false
    private var cardID: String = ""

    fun setContext(context: Context) {
        mContext = context
        luckyCartSDK = (mContext.applicationContext as ApplicationSampleLuckyCart).luckyCartSDK
        luckyCartSDK?.setActionListener(this)
    }

    override fun onBannerListReceived(bannerList: List<Banner>) {
        bannerExperienceState.value = BannerExperienceState.OnSuccess(bannerList)
    }

    override fun onBannerDetailReceived(banner: Banner) {
        getBannerDetails.value = GetBannerState.OnSuccess(banner)
    }

    override fun onGameListReceived(gameList: List<GameExperience>) {

        (mContext as MainActivity).showFragmentGame(gameList)
    }

    override fun onError(error: String?) {
        getBannerDetails.value = GetBannerState.OnError("error")
    }

    override fun onPostEvent(success: String?) {
        if(cardID == "cartValidated"){
            val filters = arrayListOf<Filter>()
            filters.add(Filter(filterProperty = "cartId", "tom_221122_2250"))
            luckyCartSDK?.getGamesAccess(siteKey = "A2ei4iyi", count = 1, filters = GameFilter(requestFilter = filters))
        }
    }

    fun loadBannerHomePage() {
        getBannerCategory = false
        Prefs(mContext).banners?.homepage?.forEach { format ->
            luckyCartSDK?.getBannerExperienceDetail(BANNER_HOMEPAGE, format)
        }
    }

    fun loadBannerCategory(pageId: String) {
        getBannerCategory = true
        luckyCartSDK?.getBannerExperienceDetail(page_type = BANNER_CATEGORIES, format = "banner", pageId = pageId)
    }

    fun loadShopBanner(pageId: String, pageType: String) {
        getBannerCategory = true
        if (pageType == BANNER_HOMEPAGE)
            Prefs(mContext).banners?.homepage?.forEach {
                luckyCartSDK?.getBannerExperienceDetail(BANNER_CATEGORIES, "banner", pageId)
            }
        else {
            Prefs(mContext).banners?.categories?.forEach {
                if (it.contains(pageId)) luckyCartSDK?.getBannerExperienceDetail(
                    BANNER_CATEGORIES, "banner", pageId)
            }
        }
    }

    fun updateProductOfShopId(shopId: String, pageType: String?): ArrayList<Product> {
        val listProduct = ArrayList<Product>()
        when (shopId) {
            CATEGORY_COFFEE_ID -> listProduct.addAll(FakeData.coffees.products)
            CATEGORY_FRUITS_ID -> listProduct.addAll(FakeData.fruits.products)
            else -> {
                if (pageType == SHOP_COFFEE || pageType == BANNER_HOMEPAGE) {
                    for (product in FakeData.coffees.products) {
                        if (product.brand == FakeData.coffeeBrothers) listProduct.add(product)
                    }
                } else {
                    for (product in FakeData.fruits.products) {
                        if (product.brand == FakeData.queensBeverages) listProduct.add(product)
                    }
                }
            }
        }
        return listProduct
    }

    fun sendShopperEvent(){
        cardID = "cartValidated"
        val timesTamp = (Date().time / 1000)
        val products = arrayListOf<SDKProduct>()
        products.add(SDKProduct("MPX_4123241", 11.13F, 11.13F, "1", null, brand = "corona"))
        products.add(SDKProduct("MPX_4798320", 11.13F, 11.13F, "1", null, brand = "corona"))
        val eventPayload =EventPayload(cartId = timesTamp,"EUR", "web-optin", products = products)

        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "cartValidated", eventPayload)
    }
    fun bannerViewed(banner : Banner){
        cardID = ""
        val eventPayload =EventPayload(pageType = "homepage",pageId = null,bannerType = "banner", bannerPosition = "homepage card", operationId = banner.operationId)
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "bannerViewed", eventPayload)
    }
    fun bannerDisplayed(){
        cardID = ""
        val eventPayload =EventPayload(pageType = "homepage",pageId = null,bannerType = "banner", bannerPosition = "homepage card")
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "pagerView", eventPayload)
    }
    fun bannerClicked(banner : Banner){
        cardID = ""
        val eventPayload =EventPayload(pageType = "homepage",pageId = null,bannerType = "banner", bannerPosition = "homepage card", operationId = banner.operationId)
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "bannerClicked", eventPayload)
    }
}

