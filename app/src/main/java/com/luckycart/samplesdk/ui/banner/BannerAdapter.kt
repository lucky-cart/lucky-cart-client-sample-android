package com.luckycart.samplesdk.ui.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luckycart.model.BannerDetails
import com.luckycart.samplesdk.R
import kotlinx.android.synthetic.main.item_banner.view.*

class BannerAdapter (var context: Context, var listBanner: ArrayList<BannerDetails>) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewBanner(listBanner[position])
    }

    override fun getItemCount(): Int {
        return listBanner.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViewBanner(item: BannerDetails) {
            if (item.id.contains("banner")){
                itemView.txtName.visibility = View.GONE
                itemView.btnAddCart.visibility = View.GONE
                Glide.with(context)
                    .load(item.image_url)
                    .into(itemView.imgBanner)
            }else{
                itemView.txtName.visibility = View.VISIBLE
                itemView.btnAddCart.visibility = View.VISIBLE
                itemView.txtName.text = item.name
                Glide.with(context)
                    .load(item.image_url)
                    .into(itemView.imgSearch)
            }


        }
    }
}