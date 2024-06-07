package com.example.demo.infra.spotify.model

import com.example.demo.model.Artist

class SpotifyArtist (
    val id: String,
    val name: String,
    val genres: List<String>,
    val popularity: Int
)  {
    fun getArtist() : Artist {
        return Artist(id, name, genres, popularity)
    }
}