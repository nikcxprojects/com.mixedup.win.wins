package com.mixedup.win.wins

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val music = PreferenceManager.getDefaultSharedPreferences(this).getInt("music", 1)
        if(music == 1){
            Music.mediaplayer_music!!.start()
            Music.mediaplayer_music!!.isLooping = true
            switch1.isChecked = true
        }else{
            switch1.isChecked = false
        }

        val sounds = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
        if(sounds == 1){
            switch1.isChecked = true
        }else{
            switch1.isChecked = false
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

        switch1.setOnClickListener {
            if(switch1.isChecked){
                val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
                if(sound == 1){
                    Music.mediaplayer_sound!!.start()
                }else{

                }
                switch1.isChecked = true
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("sound", 1).apply()
            }else{
                switch1.isChecked = false
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("sound", 0).apply()
            }
        }

        switch2.setOnClickListener {
            if(switch2.isChecked == true){
                val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
                if(sound == 1){
                    Music.mediaplayer_sound!!.start()
                }else{

                }
                Music.mediaplayer_music!!.start()
                Music.mediaplayer_music!!.isLooping = true
                switch2.isChecked = true
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("music", 1).apply()
            }else{
                val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
                if(sound == 1){
                    Music.mediaplayer_sound!!.start()
                }else{

                }
                Music.mediaplayer_music!!.pause()
                switch2.isChecked = false
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("music", 0).apply()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val onStops = PreferenceManager.getDefaultSharedPreferences(this).getInt("onStops", 0)
        if(onStops == 0){
            Music.mediaplayer_music!!.pause()
        }
    }
}