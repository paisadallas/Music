package com.john.music.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.music.MusicApp
import com.john.music.adapter.TrackAdapter
import com.john.music.adapter.TrackAdapterListener
import com.john.music.databinding.FragmentRockBinding
import com.john.music.di.PresenterModule
import com.john.music.model.Track
import com.john.music.presenter.RockPresenter
import com.john.music.presenter.RockPresenterContract
import com.john.music.presenter.RockViewContract
import com.john.music.res.TracksAPI
import javax.inject.Inject


class RockFragment : Fragment(), RockViewContract {

    private lateinit var trackAdapterListener:TrackAdapterListener
    private var _binding : FragmentRockBinding? = null
    @Inject
     lateinit var rockPresenter :RockPresenter
    private val bindig: FragmentRockBinding? get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        trackAdapterListener = activity as TrackAdapterListener
    }

  private val binding by lazy {
      FragmentRockBinding.inflate(layoutInflater)
  }

   private val trackAdapter by lazy {
        TrackAdapter(trackAdapterListener)
    }

//   private val rockPresenter : RockPresenterContract by lazy {
//        RockPresenter(requireContext(),this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MusicApp.musicComponent.inject(this)
     //   DaggerMusicComponent.create().inject(this)

        trackAdapterListener.playMusic("HELLO")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        _binding = FragmentRockBinding.inflate(inflater, container,false)
//        bindig?.rvData?.apply {
//            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//            adapter = trackAdapter
//
//        }
//
        binding.rvData.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = trackAdapter
        }
        return (binding.root)
    }

    override fun onResume() {
        super.onResume()
        rockPresenter.viewContract = this
        rockPresenter.checkNetwork()
        rockPresenter.getRock()

        binding.swipereLayout.apply {
            setOnRefreshListener {
                Log.d("REFRESH", "Refreshin")
                rockPresenter.getRock()
                isRefreshing = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
       // rockPresenter.destroyPresent()
        rockPresenter.onDestroy()
    }


    override fun rockLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun rockSuccessfull(track: Track) {
        binding.pbLoading.visibility = View.GONE
        trackAdapter.updateTrack(track)
    }

    override fun rockError(throwable: Throwable) {
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

    companion object {

        fun newInstance() = RockFragment()
    }
}

