package com.example.demo.service

import com.example.demo.infra.spotify.model.SpotifyArtist
import com.example.demo.model.Artist

interface ArtistServiceInterface {
    fun getUserArtistsByPopularity(spotifyArtists: List<SpotifyArtist>, popularity: Int) : List<Artist>?
}
