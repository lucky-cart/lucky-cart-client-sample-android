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
import com.luckycart.samplesdk.utils.AUTH_KEY
import com.luckycart.samplesdk.utils.BANNER_CATEGORIES
import com.luckycart.samplesdk.utils.BANNER_HOMEPAGE
import com.luckycart.samplesdk.utils.CUSTOMER_ID
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
        if (getBannerCategory) {
            if (banners.action.ref == shopID)
            getBannerCategoryDetails.value = GetBannerState.OnSuccess(banners)
        }else getBannerDetails.value = GetBannerState.OnSuccess(banners)
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
        for (i in 0 until Prefs(mContext).banners.categories.size) {
            luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, Prefs(mContext).banners.categories[i])
        }
    }

    fun loadShopBanner(pageId: String){
        shopID = pageId
        getBannerCategory = true
        if (luckyCartSDK == null)
            luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.setActionListener(this)
        Prefs(mContext).banners.categories.forEach{
            if (it.contains("banner"))
            luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, it)
        }
    }

    fun getContext(context: Context) {
        mContext = context
    }
}