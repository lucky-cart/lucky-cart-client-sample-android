package com.luckycart.samplesdk.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.luckycart.samplesdk.utils.CUSTOMER_ID
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.home.HomeFragment
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_SHOP
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_SHOP_ID
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCustomer.text = getString(R.string.customer, CUSTOMER_ID)
        setUpViewModel()
        showFragment(HomeFragment(), null, null)


    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getContext(this)
    }

    fun showFragment(fragment: Fragment, shopID: String?, shopType: String?) {
        val args = Bundle()
        args.putString(INTENT_FRAGMENT_SHOP_ID, shopID)
        args.putString(INTENT_FRAGMENT_SHOP, shopType)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().add(R.id.fragment, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.findFragmentById(R.id.fragment) ?: exitProcess(0)
    }
}
