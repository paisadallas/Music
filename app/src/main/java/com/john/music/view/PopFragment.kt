package com.john.music.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.music.MusicApp
import com.john.music.adapter.SongAdapter
import com.john.music.adapter.TrackAdapter
import com.john.music.adapter.TrackAdapterListener
import com.john.music.data.Song
import com.john.music.databinding.FragmentPopBinding
import com.john.music.di.Offline
import com.john.music.presenter.PopPresenter
import com.john.music.presenter.PopViewContract
import com.john.music.view.ui.SongViewModel
import com.john.music.view.ui.ViewModelFactory
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

class PopFragment : Fragment() , PopViewContract {

    private lateinit var trackAdapterListener: TrackAdapterListener

    //DAO
    private lateinit var viewModelFactory : ViewModelFactory
    private val viewModel : SongViewModel by viewModels { viewModelFactory }
    private val disposable =  CompositeDisposable()

    var mySong = Song(3,"chisp","papa","15.0","nn","pop")
    @Inject
    lateinit var popPresenter : PopPresenter
    private val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }

    private val trackAdapter by lazy {
        TrackAdapter(trackAdapterListener)
    }

    private val songAdapter by lazy {
        SongAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        trackAdapterListener = activity as TrackAdapterListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       // MusicApp.musicComponent.inject(RockFragment())

        super.onCreate(savedInstanceState)
        MusicApp.musicComponent.inject(this)
        viewModelFactory = Offline.provideViewModelFactory(this)

        binding.btUpdate.setOnClickListener { addSong() }
    }

    fun addSong(){
        binding.btUpdate.isEnabled = false
        disposable.add(viewModel.updateCollection(mySong)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({binding.btUpdate.isEnabled=true},
                {error -> Log.d("error",error.toString())}))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding.rvData.apply {
//            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//       //     adapter = trackAdapter
//            adapter = songAdapter
//        }

        return (binding.root)
    }

    override fun onStart() {
        super.onStart()

        for (i in 0..3) {
            disposable.add(viewModel.collectionId(i)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    songAdapter.update(it)
                    Log.d("ADAPTER", it.gender)
//                    this.binding.rvData.apply {
//                        layoutManager = LinearLayoutManager(
//                            requireContext(),
//                            LinearLayoutManager.VERTICAL,
//                            false
//                        )
//                        adapter = songAdapter
//                    }
                }, { error -> Log.d("Error", error.toString()) })
            )
        }


//        disposable.add(viewModel.allCollection()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d("ADAPTER","ENTER")
//                       songAdapter.update(it)
////                                       this.binding.rvData.apply {
////                           layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
////                           adapter = songAdapter
////                       }
//            },{error -> Log.d("Error",error.toString())}))

        this.binding.rvData.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = songAdapter
        }

    }
    override fun onResume() {
        super.onResume()
        popPresenter.viewContract = this
        popPresenter.checkNetwork()
        popPresenter.getPop()

//        binding.swipereLayout.apply {
//            setOnRefreshListener {
//                popPresenter.getPop()
//                isRefreshing = false
//            }
//        }
    }

    companion object {
        fun newInstance() = PopFragment()
    }

    override fun popLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun popSuccessfull(song: Song) {
        binding.pbLoading.visibility = View.GONE
      //  trackAdapter.updateTrack(track)
        songAdapter.update(song)
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