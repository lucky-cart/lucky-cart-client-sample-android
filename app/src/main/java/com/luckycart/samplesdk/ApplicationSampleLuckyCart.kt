package com.luckycart.samplesdk

import android.app.Application
import com.luckycart.model.LCAuthorization
import com.luckycart.samplesdk.utils.AUTH_KEY
import com.luckycart.samplesdk.utils.CUSTOMER_ID
import com.luckycart.sdk.LuckCartSDK

class ApplicationSampleLuckyCart: Application() {

    var luckyCartSDK: LuckCartSDK? = null

    override fun onCreate() {
        super.onCreate()
        // initialize Lucky SDK here
        val auth = LCAuthorization(AUTH_KEY, "")
        luckyCartSDK = LuckCartSDK(applicationContext)
        luckyCartSDK?.init(auth, null)
        luckyCartSDK?.setUser(CUSTOMER_ID)
        // set PollingConfig (retryAfter and maxAttempts by API)
        luckyCartSDK?.setPollingConfig(500L, 5)

    }
}