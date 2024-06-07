package com.example.demo.infra.spotify

import org.springframework.web.servlet.ModelAndView

interface MessageClientInterface {
    fun login() : ModelAndView
    fun requestUserInfoToken() : String
    fun saveCode(code: String)
    fun getCurrentUserProfile() : String
    fun getFollowedArtists() : String
}