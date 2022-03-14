package com.john.music.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.music.R
import com.john.music.adapter.TrackAdapter
import com.john.music.databinding.FragmentRockBinding
import com.john.music.model.Track
import com.john.music.model.TracksItem
import com.john.music.res.TracksAPI
import com.john.music.res.TracksServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RockFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RockFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

  private val binding by lazy {
      FragmentRockBinding.inflate(layoutInflater)
  }

    val trackAdapter by lazy {
        TrackAdapter()
    }
    var simpleObjet:Track =
        Track(
     486597,
     "169003304",
     "169003415",
     "Journey",
    "Greatest Hits",
    "Don't Stop Believin'",
     "Greatest Hits",
     "Don't Stop Believin'",
     80,
     "https://music.apple.com/us/album/dont-stop-believin/169003304?i=169003415&uo=4",
     9.9,
     "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/e4/6c/ad/e46cad13-317a-3074-8d0f-a41af0bb2437/mzaf_5207796602846861401.plus.aac.p.m4a",
     "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/01/69/5f/01695f6c-541d-faef-ac67-d1033b11c79a/source/30x30bb.jpg",
    "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/01/69/5f/01695f6c-541d-faef-ac67-d1033b11c79a/source/60x60bb.jpg",
     "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/01/69/5f/01695f6c-541d-faef-ac67-d1033b11c79a/source/100x100bb.jpg",
     2,
     1,
     true,
     "notExplicit",
     "notExplicit",
     "1",
    "1",
     "16",
    2,
     "250880",
    909,
     "USD",
     98,
     34.0,9,"","")

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

        //trackAdapter.updateTrack(simpleObjet)

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
        )
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
}

