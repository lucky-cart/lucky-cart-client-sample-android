package com.luckycart.samplesdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.luckycart.model.BannerDetails
import com.luckycart.model.Banners
import com.luckycart.model.LCAuthorization
import com.luckycart.sdk.LuckCartSDK
import com.luckycart.sdk.LuckyCartListenerCallback

class MainActivity : AppCompatActivity(), LuckyCartListenerCallback {
    var luckyCartSDK: LuckCartSDK? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth = LCAuthorization(AUTH_KEY, "")
        luckyCartSDK = LuckCartSDK(this)
        luckyCartSDK?.init(auth, CUSTOMER_ID)
        luckyCartSDK?.setUser(CUSTOMER_ID)
        luckyCartSDK?.setActionListener(this)
        luckyCartSDK?.listAvailableBanners()
        luckyCartSDK?.getBannerDetails("categories","banner_100")
    }

    override fun listAvailableBanners(banners: Banners) {
        Log.d("listAvailableBanners", "" + banners)
    }

    override fun getBannerDetails(banners: BannerDetails) {
        Log.d("getBannerDetails", "" + banners)

    }


}
