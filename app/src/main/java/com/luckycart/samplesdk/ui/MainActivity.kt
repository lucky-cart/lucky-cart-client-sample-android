package com.luckycart.samplesdk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.luckycart.model.Game
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Product
import com.luckycart.samplesdk.ui.game.GamesFragment
import com.luckycart.samplesdk.ui.home.HomeFragment
import com.luckycart.samplesdk.utils.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCustomer.text = getString(R.string.customer, CUSTOMER_ID)
        showFragment(HomeFragment(), null, null, null, null)
    }

    fun showFragment(
        fragment: Fragment,
        shopID: String?,
        shopType: String?,
        products: ArrayList<Product>?,
        ttc: Float?
    ) {
        val args = Bundle()
        args.putParcelableArrayList(INTENT_FRAGMENT_CART, products)
        ttc?.let { args.putFloat(INTENT_FRAGMENT_CART_TTC, it) }
        args.putString(INTENT_FRAGMENT_SHOP_ID, shopID)
        args.putString(INTENT_FRAGMENT_SHOP_TYPE, shopType)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().add(R.id.fragment, fragment)
            .addToBackStack(fragment.javaClass.name).commit()
    }

    fun showFragmentGame(games: ArrayList<Game>?) {
        val listGame = ArrayList<String>()
        val listUrlGame = ArrayList<String>()
        games?.forEach { item ->
            item.mobileGameImage?.let { listGame.add(it) }
            item.mobileGameUrl?.let { listUrlGame.add(it) }
        }
        val fragment = GamesFragment()
        val args = Bundle()
        args.putStringArrayList(INTENT_FRAGMENT_GAME_IMG, listGame)
        args.putStringArrayList(INTENT_FRAGMENT_GAME_URL, listUrlGame)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().add(R.id.fragment, fragment)
            .addToBackStack(fragment.javaClass.name).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.findFragmentById(R.id.fragment) ?: exitProcess(0)
    }
}
