package com.john.music.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.music.MusicApp
import com.john.music.adapter.TrackAdapter
import com.john.music.adapter.TrackAdapterListener
import com.john.music.databinding.FragmentPopBinding
import com.john.music.model.Track
import com.john.music.presenter.PopPresenter
import com.john.music.presenter.PopViewContract
import javax.inject.Inject

class PopFragment : Fragment() , PopViewContract {

    private lateinit var trackAdapterListener: TrackAdapterListener
    @Inject
    lateinit var popPresenter : PopPresenter
    private val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }

    private val trackAdapter by lazy {
        TrackAdapter(trackAdapterListener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        trackAdapterListener = activity as TrackAdapterListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       // MusicApp.musicComponent.inject(RockFragment())

        super.onCreate(savedInstanceState)
        MusicApp.musicComponent.inject(this)
       // MusicApp.musicComponent.inject(PopFragment)
        trackAdapterListener.playMusic("hello")
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

        binding.swipereLayout.apply {
            setOnRefreshListener {
                popPresenter.getPop()
                isRefreshing = false
            }
        }
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