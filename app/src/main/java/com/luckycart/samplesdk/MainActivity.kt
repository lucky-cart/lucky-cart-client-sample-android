package com.luckycart.samplesdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luckycart.sdk.LuckCartSDK

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LuckCartSDK().init()
    }

}
