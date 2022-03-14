package com.john.music.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.john.music.R
import com.john.music.model.TracksItem
import com.john.music.res.TracksAPI
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rock, container, false)
    }

    override fun onResume() {
        super.onResume()

        TracksAPI.retrofitServices.getTracks().enqueue(object : Callback<TracksItem>{

            override fun onResponse(call: Call<TracksItem>, response: Response<TracksItem>) {
                if (response.isSuccessful){

                    Log.d("Error","succestul")
                    Log.d("Error",response.toString())
                    //Log.d("Error",)
                    response.body()?.let {
                       val yo = TracksItem(50,it.tracks)
                        Log.d("Error",yo.tracks[0].artistName)
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

