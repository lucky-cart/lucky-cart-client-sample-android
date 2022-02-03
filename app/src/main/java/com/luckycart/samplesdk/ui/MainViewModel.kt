package com.luckycart.samplesdk.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import com.luckycart.model.LCAuthorization
import com.luckycart.samplesdk.utils.AUTH_KEY
import com.luckycart.samplesdk.utils.CUSTOMER_ID
import com.luckycart.sdk.LuckCartSDK
import com.luckycart.sdk.LuckyCartListenerCallback

class MainViewModel : ViewModel(), LuckyCartListenerCallback {
    private lateinit var mContext: Context
    var luckyCartSDK: LuckCartSDK? = null

    fun initLuckyCart() {
        val auth = LCAuthorization(AUTH_KEY, "")
        luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.init(auth, CUSTOMER_ID)
        luckyCartSDK?.setUser(CUSTOMER_ID)
        luckyCartSDK?.setActionListener(this)
        luckyCartSDK?.listAvailableBanners()
        luckyCartSDK?.getBannerDetails("categories", "banner_100")
    }

    override fun listAvailableBanners(banners: Banners) {
        Log.d("listAvailableBanners", "" + banners)
    }

    override fun getBannerDetails(banners: BannerDetails) {
        Log.d("getBannerDetails", "" + banners)

    }

    fun getContext(context: Context) {
        mContext = context
    }
}