package com.luckycart.samplesdk.ui.game

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luckycart.model.Game
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_GAME_URL
import kotlinx.android.synthetic.main.item_home.view.*

class GameAdapter(var context: Context, private var listGame: ArrayList<Game>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewBanner(listGame[position])
    }

    override fun getItemCount(): Int {
        return listGame.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewBanner(item: Game) {
            Glide.with(context).load(item.mobileGameImage).into(itemView.imgBanner)
            itemView.imgBanner.setOnClickListener {
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra(INTENT_FRAGMENT_GAME_URL, item.mobileGameUrl)
                (context as MainActivity).startActivity(intent)
            }
        }
    }
}