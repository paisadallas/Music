package com.john.music.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.music.adapter.TrackAdapter
import com.john.music.databinding.FragmentRockBinding
import com.john.music.model.Track
import com.john.music.presenter.RockPresenter
import com.john.music.presenter.RockPresenterContract
import com.john.music.presenter.RockViewContract


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RockFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RockFragment : Fragment(), RockViewContract {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

  private val binding by lazy {
      FragmentRockBinding.inflate(layoutInflater)
  }

   private val trackAdapter by lazy {
        TrackAdapter()
    }

   private val rockPresenter : RockPresenterContract by lazy {
        RockPresenter(requireContext(),this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        rockPresenter.checkNetwork()
        rockPresenter.getRock()


        //trackAdapter.updateTrack(simpleObjet)
/*
        TracksServices.retrofitServices.getTracks().enqueue(object : Callback<TracksItem>{

            override fun onResponse(call: Call<TracksItem>, response: Response<TracksItem>) {
                if (response.isSuccessful){

                    Log.d("Error","succestul")
                    Log.d("Error",response.toString())
                    //Log.d("Error",)
                    response.body()?.let {
                       val yo = TracksItem(50,it.tracks)
                        Log.d("Error",yo.tracks[0].artistName)

                        //Inflate layout
                        for (i in 0..49){

                            trackAdapter.updateTrack(yo.tracks[i])
                        }
                     //   trackAdapter.updateTrack(it.tracks)
                    }
                }
            }


            override fun onFailure(call: Call<TracksItem>, t: Throwable) {
                Log.d("Error","error")
            }



        }


        )*/
    }

    override fun onDestroy() {
        super.onDestroy()
       // rockPresenter.destroyPresent()
        rockPresenter.onDestroy()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RockFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RockFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
}

