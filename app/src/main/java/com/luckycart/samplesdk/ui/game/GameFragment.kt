package com.luckycart.samplesdk.ui.game

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.home.HomeFragment
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_GAME_IMG
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_GAME_URL
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imgGame = arguments?.getString(INTENT_FRAGMENT_GAME_IMG)
        val urlGame = arguments?.getString(INTENT_FRAGMENT_GAME_URL)
        context?.let {
            Glide.with(it)
                .load(imgGame)
                .into(imgViewGame)
        }
        imgViewGame.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(INTENT_FRAGMENT_GAME_URL, urlGame)
            startActivity(intent)
        }
        btnGame.setOnClickListener {
            (context as MainActivity).showFragment(HomeFragment(), null, null, null, null)
        }
    }
}