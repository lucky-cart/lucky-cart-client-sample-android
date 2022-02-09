package com.luckycart.samplesdk.ui.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luckycart.model.BannerDetails
import com.luckycart.samplesdk.model.Product
import kotlinx.android.synthetic.main.item_home.view.*
import kotlinx.android.synthetic.main.item_product.view.*


class ProductsAndBannerAdapter(
    var context: Context,
    var listProduct: ArrayList<Product>,
    var listBanner: ArrayList<BannerDetails>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    var listener: AddProductToCard? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewProduct: View =
            LayoutInflater.from(parent.context)
                .inflate(com.luckycart.samplesdk.R.layout.item_product, parent, false)
        val viewBanner: View =
            LayoutInflater.from(parent.context)
                .inflate(com.luckycart.samplesdk.R.layout.item_home, parent, false)
        return if (viewType == 0) {
            ProductViewModel(viewProduct)
        } else BannerViewModel(viewBanner)

    }


    override fun getItemCount(): Int {
        return listProduct.size + (listBanner?.size ?: 0)
    }

    override fun getItemViewType(position: Int): Int {
        if (position < listProduct.size) {
            return 0
        }
        return if (position - listProduct.size < listBanner?.size ?: 0) {
            1
        } else -1
    }

    inner class ProductViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViewProduct(item: Product) {
            itemView.btnAddCart.setOnClickListener {
                listener?.onItemChoose(item)
            }
            itemView.txtName.text = item.name
            when (item.imageName) {
                "coffee" -> itemView.imgSearch.setImageDrawable(
                    ContextCompat.getDrawable(context, com.luckycart.samplesdk.R.drawable.coffee)
                )
                "coffee2" -> itemView.imgSearch.setImageDrawable(
                    ContextCompat.getDrawable(context, com.luckycart.samplesdk.R.drawable.coffee2)
                )
                "bananas" -> itemView.imgSearch.setImageDrawable(
                    ContextCompat.getDrawable(context, com.luckycart.samplesdk.R.drawable.bananas)
                )
                "coconut" -> itemView.imgSearch.setImageDrawable(
                    ContextCompat.getDrawable(context, com.luckycart.samplesdk.R.drawable.coconut)
                )
                "applered" -> itemView.imgSearch.setImageDrawable(
                    ContextCompat.getDrawable(context, com.luckycart.samplesdk.R.drawable.applered)
                )
                "applegreen" -> itemView.imgSearch.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        com.luckycart.samplesdk.R.drawable.applegreen
                    )
                )
            }

        }
    }

    inner class BannerViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViewBanner(item: BannerDetails) {
            Glide.with(context).load(item.image_url).into(itemView.imgBanner)
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ProductViewModel) {
            viewHolder.bindViewProduct(listProduct[position])
        }
        if (viewHolder is BannerViewModel) {
            listBanner?.get(position - listProduct.size)?.let { viewHolder.bindViewBanner(it) }
        }
    }

    interface AddProductToCard {
        fun onItemChoose(product: Product)
    }
}