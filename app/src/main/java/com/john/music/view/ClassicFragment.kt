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
import com.john.music.R
import com.john.music.adapter.TrackAdapter
import com.john.music.adapter.TrackAdapterListener
import com.john.music.databinding.FragmentClassicBinding
import com.john.music.model.Track
import com.john.music.presenter.ClassicPresenter
import com.john.music.presenter.ClassicViewContract
import javax.inject.Inject

class ClassicFragment : Fragment(), ClassicViewContract {

    private lateinit var trackAdapterListener: TrackAdapterListener
    @Inject
    lateinit var classicPresenter: ClassicPresenter

    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        trackAdapterListener = activity as TrackAdapterListener
    }

    private val trackAdapter by lazy {
        TrackAdapter(trackAdapterListener)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MusicApp.musicComponent.inject(this)
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
        classicPresenter.viewContract = this
        classicPresenter.checkNetwork()
        classicPresenter.getClassic()

        binding.swipereLayout.apply {
            setOnRefreshListener {
                classicPresenter.getClassic()
                isRefreshing = false
            }
        }
    }


    override fun classicLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun classicSuccessfull(track: Track) {
        binding.pbLoading.visibility = View.GONE
        trackAdapter.updateTrack(track)
    }

    override fun classicError(throwable: Throwable) {
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

        fun newInstance() =
            ClassicFragment()
    }
}