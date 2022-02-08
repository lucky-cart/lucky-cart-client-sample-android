package com.luckycart.samplesdk.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckycart.model.BannerDetails
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.GetBannerState
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.ui.ShoppingFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listBannerDetails: ArrayList<BannerDetails>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        mainViewModel.initLuckyCart()
        mainViewModel.getBannerDetails.observe(viewLifecycleOwner, { bannerState ->
            listBannerDetails = ArrayList()
            when (bannerState) {
                is GetBannerState.OnSuccess -> {
                    listBannerDetails.add(bannerState.response)
                    recycle.visibility = View.VISIBLE
                    recycle.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycle.adapter = context?.let { AdapterHome(it, listBannerDetails) }
                }
                is GetBannerState.OnError -> recycle.visibility = View.GONE
            }

        })

        btnShopping.setOnClickListener {
            (context as MainActivity).showFragment(ShoppingFragment(), null, null, null, null)
        }
    }

    private fun setUpViewModel() {
        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.getContext(it) }
    }
}