package com.example.demo.infra.spotify.model

class SpotifyUser (
    val id: String?,
    val display_name: String?,
    val uri: String?,
    val email: String?,
    val followers: Followers?
)

class Followers (
    val href: String?,
    val total: Int
)