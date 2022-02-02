package com.luckycart.samplesdk.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.luckycart.samplesdk.CUSTOMER_ID
import com.luckycart.samplesdk.R
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCustomer.text = getString(R.string.customer, CUSTOMER_ID)
        setUpViewModel()
        mainViewModel.initLuckyCart()
    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getContext(this)
    }


}
