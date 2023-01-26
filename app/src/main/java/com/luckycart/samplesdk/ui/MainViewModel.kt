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
    var postEventState: MutableLiveData<CartEventName> = MediatorLiveData()
    private var getBannerCategory: Boolean = false
    var cartEventName: CartEventName = CartEventName.PageViewed

    fun setContext(context: Context) {
        mContext = context
        luckyCartSDK = (mContext.applicationContext as ApplicationSampleLuckyCart).luckyCartSDK
        luckyCartSDK?.setActionListener(this)
    }

    fun getBannerList(){
        // get list of available banners when application start
        luckyCartSDK?.getBannersExperience(page_type = "Homepage", format = "banner")
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
        getBannerDetails.postValue(GetBannerState.OnError("error"))
    }

    override fun onPostEvent(success: String?) {
        if(cartEventName == CartEventName.CartValidated){
            val filters = arrayListOf<Filter>()
            filters.add(Filter(filterProperty = "cartId", cartIDTesting))
            luckyCartSDK?.getGamesAccess(siteKey = AUTH_KEY, count = 1, filters = GameFilter(filters = filters))
        }else if(cartEventName == CartEventName.PageViewed){
            postEventState.postValue(CartEventName.PageViewed)
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

            luckyCartSDK?.getBannerExperienceDetail(BANNER_CATEGORIES, "banner", pageId)
        else {
            luckyCartSDK?.getBannerExperienceDetail(BANNER_CATEGORIES, "banner", pageId)
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

    var cartIDTesting =""
    fun sendShopperEvent(productsShopping: ArrayList<Product>, totalPrice: Float){
        cartEventName = CartEventName.CartValidated
        if(productsShopping.size >0) {
            val timesTamp = (Date().time / 1000)
            val products = arrayListOf<SDKProduct>()
            cartIDTesting = "cart_$timesTamp"
            productsShopping.forEach {customProduct ->
                products.add(
                    SDKProduct(
                        productId = "${customProduct.identifier}",
                        unitAtiAmount = customProduct.price,
                        finalAtiAmount = customProduct.price,
                        quantity = 1,
                        brand = "${customProduct.brand?.name}"
                    )
                )
            }
            val eventPayload = EventPayload(
                cartId = cartIDTesting,
                currency = "EUR",
                device = "mobile",
                transactionDate = Date().toString(),
                finalAtiAmount = totalPrice,
                finalTfAmount = totalPrice,
                products = products
            )

            luckyCartSDK?.sendShopperEvent(AUTH_KEY, "cartValidated", eventPayload)
        }
    }

    fun bannerViewed(banner : Banner){
        cartEventName = CartEventName.BannerViewed
        val eventPayload =EventPayload(pageType = "homepage",pageId = null,bannerType = "banner", bannerPosition = "homepage card", operationId = banner.operationId)
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "bannerViewed", eventPayload)
    }
    fun pageDisplayed(pageType: String, bannerPosition:String){
        cartEventName = CartEventName.PageViewed
        val eventPayload =EventPayload(pageType = pageType,pageId = null,bannerType = "banner", bannerPosition = bannerPosition)
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "pageViewed", eventPayload)
    }
    fun bannerClicked(banner : Banner){
        cartEventName = CartEventName.BannerClicked
        val eventPayload =EventPayload(pageType = "homepage",pageId = null,bannerType = "banner", bannerPosition = "homepage card", operationId = banner.operationId)
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "bannerClicked", eventPayload)
    }
}

enum class CartEventName {
    PageViewed, BannerViewed, BannerClicked, CartValidated
}

