package com.luckycart.samplesdk.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckycart.model.BannerDetails
import com.luckycart.samplesdk.utils.CUSTOMER_ID
import com.luckycart.samplesdk.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listBannerDetails: ArrayList<BannerDetails>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCustomer.text = getString(R.string.customer, CUSTOMER_ID)
        setUpViewModel()
        mainViewModel.initLuckyCart()
        mainViewModel.getBannerDetails.observe(this, Observer { bannerState ->
            listBannerDetails = ArrayList()
            when (bannerState) {
                is GetBannerState.OnSuccess -> {
                    listBannerDetails.add(bannerState.response)
                    recycle.visibility = View.VISIBLE
                    recycle.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    recycle.adapter = AdapterHome(this, listBannerDetails)
                }
                is GetBannerState.OnError -> recycle.visibility = View.GONE
            }

        })

    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getContext(this)
    }


}
