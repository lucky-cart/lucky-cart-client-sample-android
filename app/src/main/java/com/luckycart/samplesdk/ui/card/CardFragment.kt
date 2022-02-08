package com.luckycart.samplesdk.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Product
import com.luckycart.samplesdk.model.Transaction
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.utils.*
import kotlinx.android.synthetic.main.fragment_card.*

class CardFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var listProduct = ArrayList<Product>()
    private var productPrice: Float = 0.0f
    private var productsName = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.getContext(it) }
    }

    private fun initClickListener() {
        btnCheckOut.setOnClickListener {
            mainViewModel.sendCard(productPrice)
        }
    }

    private fun initView() {
        val listProductAddedToCard = ArrayList<Transaction>()
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
        rvProduct.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvProduct.adapter =
            context?.let { CardAdapter(it, listProductAddedToCard.toSet().toList()) }
        txtProduct.text = getString(R.string.product, productsName.size.toString())
        txtPrice.text = getString(R.string.price, productPrice.toString())
        txtTTc.text = getString(R.string.price, productPrice.toString())
    }
}