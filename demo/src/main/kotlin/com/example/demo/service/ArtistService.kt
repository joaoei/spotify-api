package com.example.demo.service

import com.example.demo.infra.spotify.model.SpotifyArtist
import com.example.demo.model.Artist
import org.springframework.stereotype.Service

@Service
class ArtistService : ArtistServiceInterface {
    private fun getArtistsBySpotifyArtists(spotifyArtists: List<SpotifyArtist>?) : List<Artist>? {
        if (spotifyArtists != null) {
            return spotifyArtists.map { artist->
                artist.getArtist()
            }
        }

        return null
    }

    override fun getUserArtistsByPopularity(spotifyArtists: List<SpotifyArtist>, popularity: Int) : List<Artist>? {
        var artists = getArtistsBySpotifyArtists(spotifyArtists)
        artists = artists?.filter { it.popularidade > popularity }
        artists = artists?.sortedBy { it.popularidade }
        return artists
    }
}