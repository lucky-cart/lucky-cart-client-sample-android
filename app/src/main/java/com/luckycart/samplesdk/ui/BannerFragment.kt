package com.luckycart.samplesdk.ui

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
import kotlinx.android.synthetic.main.fragment_banner.*

class BannerFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listBannerDetails: ArrayList<BannerDetails>

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
        mainViewModel.loadBannerCategory()
        listBannerDetails = ArrayList()
        mainViewModel.getBannerCategoryDetails.observe(viewLifecycleOwner, Observer { bannerState ->
            when (bannerState) {
                is GetBannerState.OnSuccess -> {
                    listBannerDetails.add(bannerState.response)
                    recycleBanner.visibility = View.VISIBLE
                    recycleBanner.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycleBanner.adapter = context?.let { AdapterHome(it, listBannerDetails) }
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
}