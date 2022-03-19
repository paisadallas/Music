package com.john.music

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.john.music.adapter.TrackAdapterListener
import com.john.music.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class MainActivity : AppCompatActivity(), TrackAdapterListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.buttonNavigation.setupWithNavController(navController)

    }

    /**
     * Listening the adapter for to play music
     */
    override fun playMusic(linkSong: String) {

        var netWorkState: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(true)

        netWorkState.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                val intent = Intent()
                intent.apply {
                    action = ACTION_VIEW
                    setDataAndType(Uri.parse(linkSong), "audio/*")
                    startActivity(intent)
                }

            }, {
                val toast = Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG)
            }).apply {
            }

    }

}