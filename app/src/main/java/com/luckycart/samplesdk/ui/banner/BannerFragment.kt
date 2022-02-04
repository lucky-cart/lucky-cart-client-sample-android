package com.luckycart.samplesdk.ui.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckycart.model.BannerDetails
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.GetBannerState
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.ui.ShoppingFragment
import com.luckycart.samplesdk.ui.home.AdapterHome
import com.luckycart.samplesdk.utils.*
import kotlinx.android.synthetic.main.fragment_banner.*

class BannerFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listBannerDetails: ArrayList<BannerDetails>
    private var pageType = ""
    private var shopId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_banner, container, false)
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        shopId = arguments?.getString(INTENT_FRAGMENT_SHOP_ID).toString()
        pageType = arguments?.getString(INTENT_FRAGMENT_SHOP).toString()
        initView()
        initClickListener()
        if (pageType == "homepage")
            mainViewModel.loadShopBanner(shopId)
        else mainViewModel.loadBannerCategory(shopId)

        listBannerDetails = ArrayList()
        mainViewModel.getBannerCategoryDetails.observe(viewLifecycleOwner, Observer { bannerState ->
            when (bannerState) {
                is GetBannerState.OnSuccess -> {
                    listBannerDetails.add(bannerState.response)
                    recycleBanner.visibility = View.VISIBLE
                    recycleBanner.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycleBanner.adapter = context?.let { BannerAdapter(it, listBannerDetails) }
                }
                is GetBannerState.OnError -> recycleBanner.visibility = View.GONE
            }

        })
    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.getContext(it) }
    }

    private fun initView() {
        if (pageType.contains("homepage") && shopId == SHOP_HOME_PAGE_ID) {
            title.text = getString(R.string.coffee_promotion)
            txtPrice.visibility = View.GONE
            btnCheckOut.visibility = View.GONE
            btnShop.visibility = View.VISIBLE
        } else if (shopId == CATEGORY_COFFE_ID) {
            title.text = getString(R.string.coffee)
            txtPrice.visibility = View.VISIBLE
            btnCheckOut.visibility = View.VISIBLE
            btnShop.visibility = View.GONE
        }else title.text = getString(R.string.fruit)

    }

    private fun initClickListener() {
        btnShop.setOnClickListener {
            (context as MainActivity).showFragment(ShoppingFragment(), null, null)
        }
    }
}