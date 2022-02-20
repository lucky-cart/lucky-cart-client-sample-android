package com.luckycart.samplesdk.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.luckycart.local.Prefs
import com.luckycart.model.*
import com.luckycart.samplesdk.ApplicationSampleLuckyCart
import com.luckycart.samplesdk.model.Product
import com.luckycart.samplesdk.utils.*
import com.luckycart.sdk.LuckCartSDK
import com.luckycart.sdk.LuckyCartListenerCallback
import kotlin.collections.ArrayList

class MainViewModel : ViewModel(), LuckyCartListenerCallback {

    private lateinit var mContext: Context
    var luckyCartSDK: LuckCartSDK? = null
    var getBannerDetails: MutableLiveData<GetBannerState> = MediatorLiveData()
    var getBannerCategoryDetails: MutableLiveData<GetBannerState> = MediatorLiveData()
    private var getBannerCategory: Boolean = false
    private var cartID: String = ""

    fun setContext(context: Context) {
        mContext = context
        luckyCartSDK = (mContext.applicationContext as ApplicationSampleLuckyCart).luckyCartSDK
        luckyCartSDK?.setActionListener(this)
    }

    override fun onRecieveListAvailableBanners(banners: Banners) {
        loadBannerHomePage()
    }

    override fun onRecieveBannerDetails(bannerDetails: BannerDetails) {
        if (bannerDetails.name != null) {
            if (getBannerCategory) {
                getBannerCategoryDetails.value = GetBannerState.OnSuccess(bannerDetails)
            } else getBannerDetails.value = GetBannerState.OnSuccess(bannerDetails)
        } else {
            getBannerCategoryDetails.value = GetBannerState.OnError("error")
        }
    }

    override fun onRecieveSendCartTransactionResponse(transactionResponse: TransactionResponse) {
        luckyCartSDK?.getGame(cartID)
    }

    override fun onRecieveListGames(gameResponse: GameResponse) {
        (mContext as MainActivity).showFragmentGame(gameResponse.games)
    }

    override fun onError(error: String?) {
        Toast.makeText(mContext, "Error: $error", Toast.LENGTH_SHORT).show()
    }

    private fun loadBannerHomePage() {
        getBannerCategory = false
        Prefs(mContext).banners?.homepage?.forEach { format ->
            luckyCartSDK?.getBannerDetails(BANNER_HOMEPAGE, format, "")
        }
    }

    fun loadBannerCategory(pageId: String) {
        getBannerCategory = true
        Prefs(mContext).banners?.categories?.forEach {
            if (it.contains(pageId) && !it.contains("search")) luckyCartSDK?.getBannerDetails(
                BANNER_CATEGORIES,
                it,
                ""
            )
        }
    }

    fun loadShopBanner(pageId: String, pageType: String) {
        getBannerCategory = true
        if (pageType == BANNER_HOMEPAGE)
            Prefs(mContext).banners?.homepage?.forEach {
                luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, it, pageId)
            }
        else {
            Prefs(mContext).banners?.categories?.forEach {
                if (it.contains(pageId)) luckyCartSDK?.getBannerDetails(
                    BANNER_CATEGORIES,
                    it, pageId
                )
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

    fun sendCart(cart: JsonObject) {
        cartID = cart.get("cartId").asString
        luckyCartSDK?.sendCart(cart)
    }
}