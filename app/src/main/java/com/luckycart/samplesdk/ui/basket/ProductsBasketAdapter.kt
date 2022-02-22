package com.luckycart.samplesdk.ui.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Basket
import kotlinx.android.synthetic.main.item_product_basket.view.*

class ProductsBasketAdapter(var context: Context, private var listProduct: List<Basket>) :
    RecyclerView.Adapter<ProductsBasketAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_basket, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewProduct(listProduct[position])
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewProduct(item: Basket) {
            itemView.txtName.text = item.product.name
            itemView.txtPrice.text =
                context.getString(R.string.price, item.product.price.toString())
            itemView.txtProduct.text = item.numberOfProduct.toString()
        }
    }
}