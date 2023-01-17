package com.luckycart.samplesdk.ui.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.luckycart.model.Banner
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Product
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.utils.BANNER_CATEGORIES
import kotlinx.android.synthetic.main.item_banner_sample.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAndBannerAdapter(
    var context: Context,
    private var listProduct: ArrayList<Product>,
    private var listBanner: ArrayList<Banner>?,
    val  listener: (Banner) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var addProductlistener: AddProductToBasket? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) ProductViewModel(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
        else BannerViewModel(LayoutInflater.from(parent.context).inflate(R.layout.item_banner_sample, parent, false),)
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
                addProductlistener?.onItemChoose(item)
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

        fun bindViewBanner(item: Banner) {
            val clickListener = View.OnClickListener {
                (context as MainActivity).showFragment(ProductsAndBannerFragment(), item.operationId, BANNER_CATEGORIES, null, null)
            }
            itemView.bannerView.setBannerParams(item, clickListener, listener)
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ProductViewModel) viewHolder.bindViewProduct(listProduct[position])
        else if (viewHolder is BannerViewModel) listBanner?.get(position - listProduct.size)
            ?.let { viewHolder.bindViewBanner(it) }
    }

    interface AddProductToBasket {
        fun onItemChoose(product: Product)
    }
}