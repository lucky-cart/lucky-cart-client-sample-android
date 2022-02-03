package com.luckycart.samplesdk.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.luckycart.samplesdk.utils.CUSTOMER_ID
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.home.HomeFragment
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCustomer.text = getString(R.string.customer, CUSTOMER_ID)
        setUpViewModel()
        showFragment(HomeFragment())


    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getContext(this)
    }

    fun showFragment(fragment: Fragment) {
        if (fragment == HomeFragment())
            supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
                .commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
    }


}
