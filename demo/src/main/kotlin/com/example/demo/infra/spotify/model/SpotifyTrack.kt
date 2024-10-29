package com.example.demo.infra.spotify.model

class SpotifyTrack (
    val id : String,
    val name : String,
    val popularity : Int,
    val is_playable : Boolean,
    val uri : String,
    val album : SpotifyAlbum,
    val artists: MutableList<SpotifyArtist>
)

class SpotifyAlbum (
    val release_date : String,
    val release_date_precision : String
)