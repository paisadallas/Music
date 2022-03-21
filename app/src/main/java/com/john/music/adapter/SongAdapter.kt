package com.john.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.john.music.R
import com.john.music.data.Song

class SongAdapter (private val songList : MutableList<Song> = mutableListOf()): RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    fun update(song:Song){
        songList.add(song)
        notifyItemChanged(0)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val songTrack = LayoutInflater.from(parent.context).inflate(R.layout.track_item,parent,false)
        return ViewHolder(songTrack)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var songItem = songList[position]

       holder.bind(songItem)
    }

    override fun getItemCount(): Int = songList.size

    class ViewHolder (view:View):RecyclerView.ViewHolder(view){
        var id : Int = 0
        var gender:String = ""
        var collection:TextView = view.findViewById(R.id.tv_title)
        var name:TextView = view.findViewById(R.id.tv_song)
        var price:TextView = view.findViewById(R.id.tv_price)
        var image :String =""
        fun bind(song:Song){
            id = song.collectionId
            collection.text = song.collectionName
            name.text = song.songName
            price.text = song.songPrice
            image = song.songImage
            gender = song.gender
        }
    }

}