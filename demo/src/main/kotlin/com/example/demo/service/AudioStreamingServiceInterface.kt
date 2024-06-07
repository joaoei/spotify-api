package com.example.demo.service
import com.example.demo.infra.spotify.model.SpotifyArtists
import com.example.demo.infra.spotify.model.SpotifyTrack
import com.example.demo.infra.spotify.model.SpotifyTracks
import com.example.demo.infra.spotify.model.SpotifyUser
import com.example.demo.model.Artist
import org.springframework.web.servlet.ModelAndView

interface AudioStreamingServiceInterface {
    fun login() : ModelAndView
    fun requestUserInfoToken() : String
    fun saveCode(code: String)
    fun getCurrentUserProfile() : String
    fun getCurrentUserProfileV2() : SpotifyUser
    fun getFollowedArtists() : String
    fun getUserArtistsByPopularity(popularity: Int) : List<Artist>?
    fun createPlaylistByArtist(id : String) : List<SpotifyTrack>
}