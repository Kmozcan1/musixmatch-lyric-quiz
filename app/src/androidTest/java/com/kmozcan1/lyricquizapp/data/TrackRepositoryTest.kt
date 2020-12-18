package com.kmozcan1.lyricquizapp.data

import com.kmozcan1.lyricquizapp.data.api.musixmatch.TrackApi
import com.kmozcan1.lyricquizapp.data.repository.TrackRepositoryImpl
import com.kmozcan1.lyricquizapp.domain.Constants
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */

@RunWith(MockitoJUnitRunner::class)
class TrackRepositoryTest {

    @Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var trackApi: TrackApi

    @InjectMocks
    private lateinit var trackRepository: TrackRepository

    @Test
    fun testFetchTracks() {
        var asd = trackApi.chartTracksGetGet(
                page = 1.toBigDecimal(),
                pageSize = Constants.MAX_PAGE_SIZE.toBigDecimal(),
                country = "us",
                fHasLyrics = "1",
                callback = null,
                format = null,
        )

    }
}