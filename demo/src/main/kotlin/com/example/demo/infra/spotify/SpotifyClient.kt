package com.example.demo.infra.spotify

import com.example.demo.infra.configuration.FeignConfiguration
import com.example.demo.infra.spotify.model.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader


@FeignClient(name = "spotifyv2", url = "https://api.spotify.com/v1",  configuration = [FeignConfiguration::class])
interface SpotifyClient {
    @GetMapping("/me")
    fun getUser(
        @RequestHeader(name = "Authorization") authorization: String
    ): SpotifyUser

    @GetMapping("/me/following?type=artist&limit=50")
    fun getFollowedArtists(
        @RequestHeader(name = "Authorization") authorization: String
    ): SpotifyFollowedArtists

    @GetMapping("/artists/{idArtist}/top-tracks")
    fun getArtistsTopTracks(
        @PathVariable idArtist : String,
        @RequestHeader(name = "Authorization") authorization: String
    ): SpotifyTracks

    @GetMapping("/artists/{idArtist}/related-artists")
    fun getRelatedArtistsByArtistId(
        @PathVariable idArtist : String,
        @RequestHeader(name = "Authorization") authorization: String
    ): SpotifyArtists
}