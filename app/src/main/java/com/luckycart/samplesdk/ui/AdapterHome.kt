package com.luckycart.samplesdk.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luckycart.model.BannerDetails
import com.luckycart.samplesdk.R
import kotlinx.android.synthetic.main.item_home.view.*

class AdapterHome(var context: Context, var listBanner: ArrayList<BannerDetails>) :
    RecyclerView.Adapter<AdapterHome.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterHome.ViewHolder, position: Int) {
        holder.bindViewBanner(listBanner[position])
    }

    override fun getItemCount(): Int {
        return listBanner.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViewBanner(item: BannerDetails) {

            Glide.with(context)
                .load(item.image_url)
                .into(itemView.imgBanner)

        }
    }

}