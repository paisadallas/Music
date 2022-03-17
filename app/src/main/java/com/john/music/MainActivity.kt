package com.john.music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.john.music.databinding.ActivityMainBinding
import com.john.music.di.MusicApplication

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
     //   MusicApplication.musicComponent.inject(this)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

       binding.buttonNavigation.setupWithNavController(navController)

    }

    //API

    //https://itunes.apple.com/search?term=rock&amp;media=music&amp;entity=song&amp;limit=50
    //https://itunes.apple.com/search?term=classick&amp;media=music&amp;entity=song&amp;limit=50
    //https://itunes.apple.com/search?term=pop&amp;media=music&amp;entity=song&amp;limit=50

//    "artistName":
//    "collectionName":
//    "artworkUrl60":
//    "trackPrice":

}