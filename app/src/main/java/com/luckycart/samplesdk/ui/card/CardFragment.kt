package com.luckycart.samplesdk.ui.card

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
import com.luckycart.samplesdk.utils.CARD_ID
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_CARD
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_CARD_TTC
import kotlinx.android.synthetic.main.fragment_card.*
import java.util.*
import kotlin.collections.ArrayList

class CardFragment : Fragment() {

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
        arguments?.getStringArrayList(INTENT_FRAGMENT_CARD)?.let { productsName.addAll(it) }
        productPrice = arguments?.getFloat(INTENT_FRAGMENT_CARD_TTC) ?: 0F
        initView()
        initClickListener()
    }

    private fun setUpViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.getContext(it) }
    }

    private fun initClickListener() {
        val products = JsonArray()
        val card = JsonObject()
        val cardId = CARD_ID + Random().nextInt(9999999)
        listProductAddedToCard.toSet().toList().forEach {
            val product = JsonObject()
            product.addProperty("productId", it.product.name)
            product.addProperty("ttc", it.product.price.toString())
            product.addProperty("quantity", it.numberOfProduct.toString())
            products.add(product)
        }
        card.addProperty("cartId", cardId)
        card.addProperty("ttc", productPrice)
        card.add("products", products)
        btnCheckOut.setOnClickListener {
            mainViewModel.sendCard(card)
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
            context?.let { CardAdapter(it, listProductAddedToCard.toSet().toList()) }
        txtProduct.text = getString(R.string.product, productsName.size.toString())
        txtPrice.text = getString(R.string.price, productPrice.toString())
        txtTTc.text = getString(R.string.price, productPrice.toString())
    }
}