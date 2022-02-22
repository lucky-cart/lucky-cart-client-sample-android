package com.luckycart.samplesdk.ui.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckycart.model.Game
import com.luckycart.samplesdk.R
import kotlinx.android.synthetic.main.item_banner_sample.view.*

class GamesAdapter(var context: Context, private var listGame: ArrayList<Game>) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_banner_sample, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewBanner(listGame[position])
    }

    override fun getItemCount(): Int {
        return listGame.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewBanner(item: Game) {
            itemView.bannerView.setGame(item)
        }
    }
}