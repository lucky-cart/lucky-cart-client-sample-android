package com.luckycart.samplesdk.ui.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luckycart.model.BannerDetails
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Product
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.home.WebViewFragment
import kotlinx.android.synthetic.main.item_home.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAndBannerAdapter(
    var context: Context,
    private var pageType: String?,
    private var listProduct: ArrayList<Product>,
    private var listBanner: ArrayList<BannerDetails>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    var listener: AddProductToCart? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) ProductViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
        else BannerViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false),
        )
    }

    override fun getItemCount(): Int {
        return listProduct.size + (listBanner?.size ?: 0)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < listProduct.size) return 0 else 1
    }

    inner class ProductViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewProduct(item: Product) {
            itemView.btnAddCart.setOnClickListener {
                listener?.onItemChoose(item)
            }
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
                    ContextCompat.getDrawable(
                        context, R.drawable.applegreen
                    )
                )
            }
        }
    }

    inner class BannerViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewBanner(item: BannerDetails) {
            Glide.with(context).load(item.image_url).into(itemView.imgBanner)
            itemView.imgBanner.setOnClickListener {
                if (item.action?.ref.isNullOrEmpty()) {
                    (context as MainActivity).showFragment(
                        WebViewFragment(), null, item.redirect_url, null, null
                    )
                } else (context as MainActivity).showFragment(
                    ProductsAndBannerFragment(), item.action?.ref, pageType, null, null
                )
            }
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ProductViewModel) viewHolder.bindViewProduct(listProduct[position])
        else if (viewHolder is BannerViewModel) listBanner?.get(position - listProduct.size)?.let { viewHolder.bindViewBanner(it) }
    }

    interface AddProductToCart {
        fun onItemChoose(product: Product)
    }
}