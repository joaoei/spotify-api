package com.example.demo

import org.springframework.web.servlet.ModelAndView

interface MessageClientInterface {
    fun login() : ModelAndView
    fun saveCode(code: String)
    fun getCode() : String
    fun getGenres() : String
    fun getCurrentUserProfile() : String
    fun getFollowedArtists() : String
}