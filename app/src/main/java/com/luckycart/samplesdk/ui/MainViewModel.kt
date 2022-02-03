package com.luckycart.samplesdk.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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
    private var luckyCartSDK: LuckCartSDK? = null
    private var availableBanners: Banners? = null
    var getBannerDetails: MutableLiveData<GetBannerState> = MediatorLiveData()


    fun initLuckyCart() {
        val auth = LCAuthorization(AUTH_KEY, "")
        luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.init(auth, CUSTOMER_ID)
        luckyCartSDK?.setUser(CUSTOMER_ID)
        luckyCartSDK?.setActionListener(this)
        luckyCartSDK?.listAvailableBanners()
    }

    override fun listAvailableBanners(banners: Banners) {
        availableBanners = banners
        loadBannerHomePage()
        Log.d("listAvailableBanners", "" + banners)
    }

    override fun getBannerDetails(banners: BannerDetails) {
        Log.d("getBannerDetails", "" + banners)
        if (banners.image_url != null)
            getBannerDetails.value = GetBannerState.OnSuccess(banners)
        else GetBannerState.OnError("error")

    }

    private fun loadBannerHomePage() {
        if (luckyCartSDK == null)
            luckyCartSDK = LuckCartSDK(mContext)
        availableBanners?.homepage?.forEach { homePage ->
            luckyCartSDK?.getBannerDetails("homepage", homePage)

        }
    }

    fun getContext(context: Context) {
        mContext = context
    }
}