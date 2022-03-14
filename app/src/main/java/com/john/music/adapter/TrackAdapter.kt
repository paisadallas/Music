package com.john.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.john.music.R
import com.john.music.model.Track
import com.squareup.picasso.Picasso

class TrackAdapter (
    private val trackList : MutableList<Track> = mutableListOf()
        ):RecyclerView.Adapter<TrackAdapter.TrackHolder>(){

    fun updateTrack(track: Track){
        trackList.add(track)
        notifyItemInserted(0)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val trackView = LayoutInflater.from(parent.context).inflate(R.layout.track_item,parent,false)
        return TrackHolder(trackView)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        var data = trackList[position]

        holder.bind(data)
    }

    override fun getItemCount(): Int = trackList.size

    class TrackHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var collectionId :Int = 0
        private val image :ImageView= itemView.findViewById(R.id.img_track)
        private val title :TextView = itemView.findViewById(R.id.tv_title)
        private val song :TextView = itemView.findViewById(R.id.tv_song)
        private val price :TextView= itemView.findViewById(R.id.tv_price)

        fun bind(track: Track){

            collectionId = track.collectionId
            title.text = track.collectionName
            song.text = track.trackName
            price.text = track.trackPrice.toString()

            if (track.artworkUrl60 != ""){
                Picasso.get().load(track.artworkUrl60)
                    .resize(200,200)
                    .centerCrop()
                    .into(image)
            }
        }
    }
}

