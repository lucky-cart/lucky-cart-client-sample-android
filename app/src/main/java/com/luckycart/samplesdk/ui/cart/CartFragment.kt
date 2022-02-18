package com.luckycart.samplesdk.ui.cart

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
import com.luckycart.samplesdk.model.Transaction
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.utils.CART_ID
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_CART
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_CART_TTC
import kotlinx.android.synthetic.main.fragment_card.*
import java.util.*
import kotlin.collections.ArrayList

class CartFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var listProduct = ArrayList<Product>()
    private var productPrice: Float = 0.0f
    private var productsName = ArrayList<String>()
    private val listProductAddedToCard = ArrayList<Transaction>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        arguments?.getStringArrayList(INTENT_FRAGMENT_CART)?.let { productsName.addAll(it) }
        productPrice = arguments?.getFloat(INTENT_FRAGMENT_CART_TTC) ?: 0F
        initView()
        initClickListener()
    }

    private fun setUpViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.getContext(it) }
    }

    private fun initClickListener() {
        val products = JsonArray()
        val cart = JsonObject()
        val cartId = CART_ID + Random().nextInt(9999999)
        listProductAddedToCard.toSet().toList().forEach {
            val product = JsonObject()
            product.addProperty("productId", it.product.name)
            product.addProperty("ttc", it.product.price.toString())
            product.addProperty("quantity", it.numberOfProduct.toString())
            products.add(product)
        }
        cart.addProperty("cartId", cartId)
        cart.addProperty("ttc", productPrice)
        cart.add("products", products)
        btnCheckOut.setOnClickListener {
            mainViewModel.sendCart(cart)
        }
    }

    private fun initView() {
        listProduct.addAll(mainViewModel.updateAllProduct())
        productsName.forEach { name ->
            listProduct.forEach { product ->
                if (product.name == name) {
                    listProductAddedToCard.add(
                        Transaction(
                            product,
                            productsName.count { it == product.name })
                    )
                }
            }
        }
        rvProduct.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvProduct.adapter =
            context?.let { CartAdapter(it, listProductAddedToCard.toSet().toList()) }
        txtProduct.text = getString(R.string.product, productsName.size.toString())
        txtPrice.text = getString(R.string.price, productPrice.toString())
        txtTTc.text = getString(R.string.price, productPrice.toString())
    }
}