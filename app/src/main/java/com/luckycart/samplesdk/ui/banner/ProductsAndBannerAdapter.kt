package com.luckycart.samplesdk.ui.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Product
import kotlinx.android.synthetic.main.item_banner.view.*

class ProductsAndBannerAdapter(var context: Context, var listProduct: ArrayList<Product>) :
    RecyclerView.Adapter<ProductsAndBannerAdapter.ViewHolder>() {

    var listener: AddProductToCard? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
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
            itemView.btnAddCart.setOnClickListener {
                listener?.onItemChoose(item)
            }
            if (item.name.contains("banner")) {
                itemView.txtName.visibility = View.GONE
                itemView.btnAddCart.visibility = View.GONE
                Glide.with(context).load(item.imageName).into(itemView.imgBanner)
            } else {
                itemView.txtName.visibility = View.VISIBLE
                itemView.btnAddCart.visibility = View.VISIBLE
                itemView.txtName.text = item.name
                when (item.imageName) {
                    "coffee" -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.coffee)
                    )
                    "coffee2" -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.coffee2)
                    )
                    "bananas" -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.bananas)
                    )
                    "coconut" -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.coconut)
                    )
                    "applered" -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.applered)
                    )
                    "applegreen" -> itemView.imgSearch.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.applegreen)
                    )
                }

            }


        }
    }

    interface AddProductToCard {

        fun onItemChoose(product: Product)
    }
}
