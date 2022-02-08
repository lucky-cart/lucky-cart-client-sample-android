package com.luckycart.samplesdk.ui.card

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.model.Transaction
import kotlinx.android.synthetic.main.item_card.view.*

class CardAdapter(var context: Context, var listProduct: List<Transaction>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewProduct(listProduct[position])
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViewProduct(item: Transaction) {
            itemView.txtName.text = item.product.name
            itemView.txtPrice.text =
                context.getString(R.string.price, item.product.price.toString())
            itemView.txtProduct.text = item.numberOfProduct.toString()

        }
    }
}