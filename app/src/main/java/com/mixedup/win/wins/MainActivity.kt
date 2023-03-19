package com.mixedup.win.wins

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import com.mixedup.win.wins.Music.mediaplayer_music
import com.mixedup.win.wins.Music.mediaplayer_sound
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaplayer_music = MediaPlayer.create(this,R.raw.music)
        mediaplayer_sound = MediaPlayer.create(this,R.raw.sounds)

        val music = PreferenceManager.getDefaultSharedPreferences(this).getInt("music", 1)
        if(music == 1){
            mediaplayer_music!!.start()
            mediaplayer_music!!.isLooping = true
        }else{

        }

        Handler(Looper.myLooper()!!).postDelayed(
            {
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("onStops", 0).apply()
            }, 1000)

        button.setOnClickListener {
            val random = Random.nextInt(1..4)
            val intent = Intent(this,PuzzleActivity::class.java)
            val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
            if(sound == 1){
                mediaplayer_sound!!.start()
            }else{

            }
            if(random == 1){
                intent.putExtra("image","image1.jpg")
                intent.putExtra("index",1)
                startActivity(intent)
            }else if(random == 2){
                intent.putExtra("image","image2.jpg")
                intent.putExtra("index",1)
                startActivity(intent)
            }else if(random == 3){
                intent.putExtra("image","image3.jpg")
                intent.putExtra("index",1)
                startActivity(intent)
            }else if(random == 4){
                intent.putExtra("image","image4.jpg")
                intent.putExtra("index",1)
                startActivity(intent)
            }else if(random == 5){
                intent.putExtra("image","image5.jpg")
                intent.putExtra("index",1)
                startActivity(intent)
            }else if(random == 6){
                intent.putExtra("image","image6.jpg")
                intent.putExtra("index",1)
                startActivity(intent)
            }
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt("onStops", 1).apply()
            startActivity(intent)
        }

        button2.setOnClickListener {
            val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
            if(sound == 1){
                mediaplayer_sound!!.start()
            }else{

            }
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt("onStops", 1).apply()
            startActivity(Intent(this,ChooseDifficulty::class.java))
        }

        button4.setOnClickListener {
            val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
            if(sound == 1){
                mediaplayer_sound!!.start()
            }else{

            }
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt("onStops", 1).apply()
            startActivity(Intent(this,Settings::class.java))
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