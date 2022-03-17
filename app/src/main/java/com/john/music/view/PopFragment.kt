package com.john.music.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.music.MainActivity
import com.john.music.MusicApp
import com.john.music.R
import com.john.music.adapter.TrackAdapter
import com.john.music.databinding.FragmentPopBinding
import com.john.music.di.DaggerMusicComponent
import com.john.music.di.MusicComponent
import com.john.music.model.Track
import com.john.music.presenter.PopPresenter
import com.john.music.presenter.PopViewContract
import com.john.music.presenter.RockViewContract
import dagger.android.DaggerActivity
import javax.inject.Inject


class PopFragment : Fragment() , PopViewContract {

    @Inject
    lateinit var popPresenter : PopPresenter
    private val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }

    private val trackAdapter by lazy {
        TrackAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       // MusicApp.musicComponent.inject(RockFragment())

        super.onCreate(savedInstanceState)
        MusicApp.musicComponent.inject(this)
       // MusicApp.musicComponent.inject(PopFragment)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvData.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = trackAdapter
        }

        return (binding.root)
    }

    override fun onResume() {
        super.onResume()
        popPresenter.viewContract = this
        popPresenter.checkNetwork()
        popPresenter.getPop()
    }

    companion object {
        fun newInstance() = PopFragment()
    }

    override fun popLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun popSuccessfull(track: Track) {
        binding.pbLoading.visibility = View.GONE
        trackAdapter.updateTrack(track)
    }

    override fun popError(throwable: Throwable) {
        binding.pbLoading.visibility = View.GONE
        AlertDialog.Builder(requireContext())
            .setTitle("ERROR")
            .setMessage(throwable.localizedMessage)
            .setPositiveButton("OK"){dialogInterface,i->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

}