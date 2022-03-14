package com.john.music.model


import com.google.gson.annotations.SerializedName

data class TracksItem(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val tracks: List<Track>
)