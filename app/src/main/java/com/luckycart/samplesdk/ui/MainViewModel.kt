package com.luckycart.samplesdk.ui

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luckycart.local.Prefs
import com.luckycart.model.*
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
        luckyCartSDK?.getGame(CARD_ID)
    }

    override fun getGame(game: GameResponse) {
        val listGame = ArrayList<String>()
        val listUrlGame = ArrayList<String>()
        game.games?.forEach { game ->
            game.mobileGameImage?.let { listGame.add(it) }
            game.mobileGameUrl?.let { listUrlGame.add(it) }
        }
        (mContext as MainActivity).showFragmentGame(
            listGame,
            listUrlGame
        )
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

    fun updateProductOfShopId(shopId: String): ArrayList<Product> {
        val listProduct = ArrayList<Product>()
        when (shopId) {
            CATEGORY_COFFE_ID -> listProduct.addAll(FakeData.coffees.products)
            CATEGORY_FRUITS_ID -> listProduct.addAll(FakeData.fruits.products)
            SHOP_HOME_PAGE_ID -> {
                for (product in FakeData.coffees.products) {
                    if (product.brand == FakeData.coffeeBrothers) listProduct.add(product)
                }
            }
        }
        return listProduct
    }

    fun updateAllProduct(): ArrayList<Product> {
        val listProduct = ArrayList<Product>()
        listProduct.addAll(FakeData.coffees.products)
        listProduct.addAll(FakeData.fruits.products)
        return listProduct
    }

    fun sendCard(ttc: Float) {
        if (luckyCartSDK == null)
            luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.setActionListener(this)
        luckyCartSDK?.sendCard(CARD_ID, ttc)
    }
}