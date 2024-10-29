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

    // Teste 1: Lista nula
    // Teste 2: Lista com n itens
    // Teste 3: Popularidade negativa
    // Teste 4: Popularidade zero
    // Teste 5: Popularidade maior que 0
    // Teste 6: Garantir ordenação
    override fun getUserArtistsByPopularity(spotifyArtists: List<SpotifyArtist>, popularity: Int) : List<Artist>? {
        var artists = getArtistsBySpotifyArtists(spotifyArtists)
        artists = artists?.filter { it.popularidade > popularity }
        artists = artists?.sortedBy { it.popularidade }
        return artists
    }
}