package com.luckycart.samplesdk.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.luckycart.model.Banner
import com.luckycart.retrofit.BannerExperienceState
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.extension.dpToPx
import com.luckycart.samplesdk.ui.CartEventName
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.MainViewModel
import com.luckycart.samplesdk.ui.ShoppingFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()

        mainViewModel.pageDisplayed("homepage",null, "homepage card")

        mainViewModel.postEventState.observe(viewLifecycleOwner) { eventName->
            if(eventName == CartEventName.PageViewed){
                mainViewModel.getBannerList()
            }
        }

        mainViewModel.bannerExperienceState.observe(viewLifecycleOwner) { bannerState ->

            when (bannerState) {
                is BannerExperienceState.OnSuccess -> {
                    val bannerList = arrayListOf<Banner>()
                    bannerList.addAll(bannerState.bannerList)

                    banner_view_pager.visibility = View.VISIBLE

                    val offsetPx = resources.getDimension(R.dimen.pager_padding).toInt().dpToPx(resources.displayMetrics)
                    banner_view_pager.setPadding(offsetPx, 0, offsetPx, 0)
                    val pageMarginPx = resources.getDimension(R.dimen.pager_margin).toInt().dpToPx(resources.displayMetrics)

                    banner_view_pager.apply {
                        offscreenPageLimit = 2
                        setPageTransformer(MarginPageTransformer(pageMarginPx))
                        adapter = context?.let { AdapterHome(it, bannerList){ banner ->
                            mainViewModel.bannerClicked(banner)
                            }
                        }
                    }

                    banner_view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            mainViewModel.bannerViewed(bannerList[position])
                        }
                    })
                }

                is BannerExperienceState.OnError -> banner_view_pager.visibility = View.GONE
            }
        }

        btnShopping.setOnClickListener {
            (context as MainActivity).showFragment(ShoppingFragment(), null, null, null, null)
        }
    }

    private fun setUpViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let { mainViewModel.setContext(it) }
    }
}