package com.luckycart.samplesdk.ui.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Coffees
import com.luckycart.samplesdk.model.Fruit
import com.luckycart.samplesdk.model.Product
import kotlinx.android.synthetic.main.item_banner.view.*

class BannerAdapter(var context: Context, var listProduct: ArrayList<Product>) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewBanner(listProduct[position])
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViewBanner(item: Product) {
            if (item.name.contains("banner")) {
                itemView.txtName.visibility = View.GONE
                itemView.btnAddCart.visibility = View.GONE
                Glide.with(context)
                    .load(item.imageName)
                    .into(itemView.imgBanner)
            } else {
                itemView.txtName.visibility = View.VISIBLE
                itemView.btnAddCart.visibility = View.VISIBLE
                itemView.txtName.text = item.name
                when (item.imageName) {
                    Coffees().firstProduct.imageName -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.coffee)
                    )
                    Coffees().secondProduct.imageName -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.coffee)
                    )
                    Coffees().thirdProduct.imageName -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.coffee2)
                    )
                    Coffees().firthProduct.imageName -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.coffee2)
                    )
                    Fruit().firstProduct.imageName -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.bananas)
                    )
                    Fruit().secondProduct.imageName -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.coconut)
                    )
                    Fruit().thirdProduct.imageName -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.applered)
                    )
                    Fruit().firthProduct.imageName -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.applegreen)
                    )
                }

            }


        }
    }
}
