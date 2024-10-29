package com.example.demo.service
import com.example.demo.infra.spotify.model.SpotifyArtist
import kotlin.test.Test
import kotlin.test.assertEquals
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.*

class ArtistServiceTest {
    @Test
    fun `GIVEN uma lista vazia WHEN transformar para Artist THEN retorne nulo`() {

    }

    @Test
    fun `GIVEN uma lista com um item WHEN transformar para Artist THEN retorne lista de Artist com um item`() {

    }

    @Test
    fun `GIVEN uma lista com mais de um item WHEN transformar para Artist THEN retorne lista de Artist com mais de um item`() {

    }

    @Test
    fun `get User Artists By Popularity`() {
        val artistService: ArtistService = ArtistService()
        val artistJoao: SpotifyArtist = SpotifyArtist("123", "Joao", null, 13)
        val artistEmmanuel: SpotifyArtist = SpotifyArtist("321", "Emmanuel", null, 5)
        val list: List<SpotifyArtist> = listOf(artistJoao, artistEmmanuel)

        val usersPop1 = artistService.getUserArtistsByPopularity(list, 1)?.size
        assertEquals(2, usersPop1)

        val usersPop10 = artistService.getUserArtistsByPopularity(list, 10)?.size
        assertEquals(1, usersPop10)

        val usersPop30 = artistService.getUserArtistsByPopularity(list, 30)?.size
        assertEquals(0, usersPop30)
    }
}