package com.luckycart.samplesdk.ui.banner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.*
import com.luckycart.samplesdk.ui.GetBannerState
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.ui.ShoppingFragment
import com.luckycart.samplesdk.ui.card.CardFragment
import com.luckycart.samplesdk.utils.*
import kotlinx.android.synthetic.main.fragment_banner.*

class ProductsAndBannerFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private var pageType = ""
    private var shopId = ""
    private var listProduct = ArrayList<Product>()
    private var priceProduct: Float = 0.0F
    private var productAddedToCard:Int = 0
    private var listShopping = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_banner, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        val productsHomeName = arguments?.getStringArrayList(INTENT_FRAGMENT_CARD)
        val productHomePrice = arguments?.getFloat(INTENT_FRAGMENT_CARD_TTC)
        shopId = arguments?.getString(INTENT_FRAGMENT_SHOP_ID).toString()
        pageType = arguments?.getString(INTENT_FRAGMENT_SHOP).toString()
        listProduct.addAll(mainViewModel.updateProductOfShopId(shopId))
        if (productsHomeName != null) {
            productAddedToCard = productsHomeName.size
            listShopping.addAll(productsHomeName)
        }
        if (productHomePrice != null) {
            priceProduct = productHomePrice
        }
        initView()
        initClickListener()
        if (pageType == "homepage")
            mainViewModel.loadShopBanner(shopId)
        else mainViewModel.loadBannerCategory(shopId)
        mainViewModel.getBannerCategoryDetails.observe(viewLifecycleOwner, { bannerState ->
            when (bannerState) {
                is GetBannerState.OnSuccess -> {
                    listProduct.add(
                        Product(
                            "banner",
                            "banner",
                            bannerState.response.image_url,
                            null,
                            0F
                        )
                    )
                    val adapter = context?.let { ProductsAndBannerAdapter(it, listProduct) }
                    recycleBanner.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycleBanner.adapter = adapter
                    adapter?.listener = object : ProductsAndBannerAdapter.AddProductToCard {
                        override fun onItemChoose(product: Product) {
                            priceProduct += product.price
                            productAddedToCard+=1
                            txtPrice.text = getString(R.string.price,priceProduct.toString())
                            txtProduct.text= getString(R.string.product,productAddedToCard.toString())
                            listShopping.add(product.name)
                        }

                    }
                }
                is GetBannerState.OnError -> {
                    Log.d("Error", bannerState.error)
                }
            }

        })
    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.getContext(it) }
    }

    private fun initView() {
        when (shopId) {
            CATEGORY_COFFE_ID -> title.text = FakeData.coffees.name
            CATEGORY_FRUITS_ID -> title.text = FakeData.fruits.name
            SHOP_HOME_PAGE_ID -> {
                title.text = getString(R.string.coffee_promotion, FakeData.coffeeBrothers.name)
                context?.let { title.setTextColor(ContextCompat.getColor(it, R.color.blue1)) }
                btnCheckOut.visibility = View.GONE
                btnShop.visibility = View.VISIBLE
                txtPrice.visibility = View.GONE
                txtProduct.visibility = View.GONE

            }
        }
        val adapter = context?.let { ProductsAndBannerAdapter(it, listProduct) }
        recycleBanner.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycleBanner.adapter = adapter
        adapter?.listener = object : ProductsAndBannerAdapter.AddProductToCard {
            override fun onItemChoose(product: Product) {
                priceProduct += product.price
                productAddedToCard+=1
                txtPrice.text = getString(R.string.price,priceProduct.toString())
                txtProduct.text= getString(R.string.product,productAddedToCard.toString())
                listShopping.add(product.name)
            }

        }
    }

    private fun initClickListener() {
        btnShop.setOnClickListener {
            (context as MainActivity).showFragment(ShoppingFragment(),null,null, listShopping, priceProduct)
        }
        btnCheckOut.setOnClickListener {
            (context as MainActivity).showFragment(CardFragment(),null,null,listShopping ,priceProduct)
        }
    }
}