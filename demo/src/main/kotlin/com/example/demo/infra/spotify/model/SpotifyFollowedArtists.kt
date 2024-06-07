package com.example.demo.infra.spotify.model

class SpotifyFollowedArtists (
    val artists: ArtistsList
)

class ArtistsList (
    val total: Int,
    val limit: Int,
    val items: List<SpotifyArtist>
)