package com.luckycart.samplesdk.ui.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Product
import com.luckycart.samplesdk.model.Basket
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.utils.CART_ID
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_CART
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_CART_TTC
import kotlinx.android.synthetic.main.fragment_products_basket.*
import java.util.*
import kotlin.collections.ArrayList

class ProductsBasketFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var totalPrice: Float = 0.0f
    private var productsShooping = ArrayList<Product>()
    private val listProductsBasket = ArrayList<Basket>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        arguments?.getParcelableArrayList<Product>(INTENT_FRAGMENT_CART)
            ?.let { productsShooping.addAll(it) }
        totalPrice = arguments?.getFloat(INTENT_FRAGMENT_CART_TTC) ?: 0F
        initView()
        initClickListener()
    }

    private fun setUpViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.setContext(it) }
    }

    private fun initClickListener() {
        val products = JsonArray()
        val cart = JsonObject()
        val cartId = CART_ID + Random().nextInt(9999999)
        listProductsBasket.toSet().toList().forEach {
            val product = JsonObject()
            product.addProperty("productId", it.product.name)
            product.addProperty("ttc", it.product.price.toString())
            product.addProperty("quantity", it.numberOfProduct.toString())
            products.add(product)
        }
        cart.addProperty("cartId", cartId)
        cart.addProperty("ttc", totalPrice)
        cart.add("products", products)
        btnCheckOut.setOnClickListener {
            mainViewModel.sendCart(cart)
        }
    }

    private fun initView() {
        productsShooping.forEach { it ->
            listProductsBasket.add(
                Basket(
                    it,
                    Collections.frequency(productsShooping, it)
                )
            )
        }
        rvProduct.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvProduct.adapter =
            context?.let { ProductsBasketAdapter(it, listProductsBasket.toSet().toList()) }
        txtProduct.text = getString(R.string.product, productsShooping.size.toString())
        txtPrice.text = getString(R.string.price, totalPrice.toString())
        txtTTc.text = getString(R.string.price, totalPrice.toString())
    }
}