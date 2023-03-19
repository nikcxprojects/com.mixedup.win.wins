package com.mixedup.win.wins

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import com.mixedup.win.wins.Music.mediaplayer_music
import kotlinx.android.synthetic.main.activity_choose_difficulty.*

class ChooseDifficulty : AppCompatActivity() {

    var click_index1 = 0
    var click_index2 = 0
    var click_index3 = 0
    var click_index4 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_difficulty)

        val music = PreferenceManager.getDefaultSharedPreferences(this).getInt("music", 1)
        if(music == 1){
            mediaplayer_music!!.start()
            mediaplayer_music!!.isLooping = true
        }else{

        }

        val games = PreferenceManager.getDefaultSharedPreferences(this).getInt("Games", 1)
        if(games == 1){
            card1.setBackgroundResource(R.drawable.card_backgraund)
            card2.setBackgroundResource(R.drawable.card_backgraund2)
            card3.setBackgroundResource(R.drawable.card_backgraund2)
            card4.setBackgroundResource(R.drawable.card_backgraund2)
            textView2__.setTextColor(Color.BLACK)
            textView25.setTextColor(Color.WHITE)
            textView26.setTextColor(Color.WHITE)
            textView2.setTextColor(Color.WHITE)
        }else if(games == 2){
            card1.setBackgroundResource(R.drawable.card_backgraund2)
            card2.setBackgroundResource(R.drawable.card_backgraund)
            card3.setBackgroundResource(R.drawable.card_backgraund2)
            card4.setBackgroundResource(R.drawable.card_backgraund2)
            textView2__.setTextColor(Color.WHITE)
            textView25.setTextColor(Color.BLACK)
            textView26.setTextColor(Color.WHITE)
            textView2.setTextColor(Color.WHITE)
        }else if(games == 3){
            card1.setBackgroundResource(R.drawable.card_backgraund2)
            card2.setBackgroundResource(R.drawable.card_backgraund2)
            card3.setBackgroundResource(R.drawable.card_backgraund)
            card4.setBackgroundResource(R.drawable.card_backgraund2)
            textView2__.setTextColor(Color.WHITE)
            textView25.setTextColor(Color.WHITE)
            textView26.setTextColor(Color.BLACK)
            textView2.setTextColor(Color.WHITE)
        }else if(games == 4){
            card1.setBackgroundResource(R.drawable.card_backgraund2)
            card2.setBackgroundResource(R.drawable.card_backgraund2)
            card3.setBackgroundResource(R.drawable.card_backgraund2)
            card4.setBackgroundResource(R.drawable.card_backgraund)
            textView2__.setTextColor(Color.WHITE)
            textView25.setTextColor(Color.WHITE)
            textView26.setTextColor(Color.WHITE)
            textView2.setTextColor(Color.BLACK)
        }

        Handler(Looper.myLooper()!!).postDelayed(
            {
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("onStops", 0).apply()
            }, 1000)

        imageView3.setOnClickListener {
            val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
            if(sound == 1){
                Music.mediaplayer_sound!!.start()
            }else{

            }
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt("onStops", 1).apply()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        cardView.setOnClickListener {
            if(click_index1 == 0){
                val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
                if(sound == 1){
                    Music.mediaplayer_sound!!.start()
                }else{

                }
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("Games", 1).apply()
                card1.setBackgroundResource(R.drawable.card_backgraund)
                card2.setBackgroundResource(R.drawable.card_backgraund2)
                card3.setBackgroundResource(R.drawable.card_backgraund2)
                card4.setBackgroundResource(R.drawable.card_backgraund2)
                textView2__.setTextColor(Color.BLACK)
                textView25.setTextColor(Color.WHITE)
                textView26.setTextColor(Color.WHITE)
                textView2.setTextColor(Color.WHITE)
                click_index1 = 1
                click_index2 = 0
                click_index3 = 0
                click_index4 = 0
            }
        }

        cardView2.setOnClickListener {
            if(click_index2 == 0){
                val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
                if(sound == 1){
                    Music.mediaplayer_sound!!.start()
                }else{

                }
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("Games", 2).apply()
                card1.setBackgroundResource(R.drawable.card_backgraund2)
                card2.setBackgroundResource(R.drawable.card_backgraund)
                card3.setBackgroundResource(R.drawable.card_backgraund2)
                card4.setBackgroundResource(R.drawable.card_backgraund2)
                textView2__.setTextColor(Color.WHITE)
                textView25.setTextColor(Color.BLACK)
                textView26.setTextColor(Color.WHITE)
                textView2.setTextColor(Color.WHITE)
                click_index1 = 0
                click_index2 = 1
                click_index3 = 0
                click_index4 = 0
            }
        }

        cardView3.setOnClickListener {
            if(click_index3 == 0){
                val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
                if(sound == 1){
                    Music.mediaplayer_sound!!.start()
                }else{

                }
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("Games", 3).apply()
                card1.setBackgroundResource(R.drawable.card_backgraund2)
                card2.setBackgroundResource(R.drawable.card_backgraund2)
                card3.setBackgroundResource(R.drawable.card_backgraund)
                card4.setBackgroundResource(R.drawable.card_backgraund2)
                textView2__.setTextColor(Color.WHITE)
                textView25.setTextColor(Color.WHITE)
                textView26.setTextColor(Color.BLACK)
                textView2.setTextColor(Color.WHITE)
                click_index1 = 0
                click_index2 = 0
                click_index3 = 1
                click_index4 = 0
            }
        }

        cardview4.setOnClickListener {
            if(click_index4 == 0){
                val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
                if(sound == 1){
                    Music.mediaplayer_sound!!.start()
                }else{

                }
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("Games", 4).apply()
                card1.setBackgroundResource(R.drawable.card_backgraund2)
                card2.setBackgroundResource(R.drawable.card_backgraund2)
                card3.setBackgroundResource(R.drawable.card_backgraund2)
                card4.setBackgroundResource(R.drawable.card_backgraund)
                textView2__.setTextColor(Color.WHITE)
                textView25.setTextColor(Color.WHITE)
                textView26.setTextColor(Color.WHITE)
                textView2.setTextColor(Color.BLACK)
                click_index1 = 0
                click_index2 = 0
                click_index3 = 0
                click_index4 = 1
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val onStops = PreferenceManager.getDefaultSharedPreferences(this).getInt("onStops", 0)
        if(onStops == 0){
            mediaplayer_music!!.pause()
        }
    }
}