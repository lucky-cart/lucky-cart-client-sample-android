package com.luckycart.samplesdk.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckycart.model.Game
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.ui.MainActivity
import com.luckycart.samplesdk.ui.home.HomeFragment
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_GAME_IMG
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_GAME_URL
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.recycle

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
        val imgGame = arguments?.getStringArrayList(INTENT_FRAGMENT_GAME_IMG)
        val urlGame = arguments?.getStringArrayList(INTENT_FRAGMENT_GAME_URL)
        val listGame = ArrayList<Game>()
        if (imgGame != null) {
            for (i in 0 until imgGame.size)
                listGame.add(Game(null, null, null, null, null, urlGame?.get(i), imgGame[i]))
        }
        recycle.visibility = View.VISIBLE
        recycle.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycle.adapter = context?.let { GameAdapter(it, listGame) }

        btnAgain.setOnClickListener {
            (context as MainActivity).showFragment(HomeFragment(), null, null, null, null)
        }
    }
}