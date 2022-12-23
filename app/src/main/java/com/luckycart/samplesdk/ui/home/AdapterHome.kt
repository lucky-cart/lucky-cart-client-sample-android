package com.luckycart.samplesdk.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckycart.model.Banner
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.banner.ProductsAndBannerFragment
import com.luckycart.samplesdk.utils.BANNER_HOMEPAGE
import kotlinx.android.synthetic.main.item_banner_sample.view.*

class AdapterHome(var context: Context, private var listBanner: ArrayList<Banner>,val  listener: (Banner) -> Unit) :
    RecyclerView.Adapter<AdapterHome.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_banner_sample, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewBanner(listBanner[position])
    }

    override fun getItemCount(): Int {
        return listBanner.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewBanner(item: Banner) {
            val clickListener = View.OnClickListener {
                (context as MainActivity).showFragment(ProductsAndBannerFragment(), item.operationId, BANNER_HOMEPAGE, null, null)
            }
            itemView.bannerView.setBannerParams(item, clickListener, listener)
        }
    }
}