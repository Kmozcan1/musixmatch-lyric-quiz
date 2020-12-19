package com.kmozcan1.lyricquizapp.domain.model.domainmodel

/**
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */
data class Question(val id: Int, val lyric: String, val correctAnswer: ArtistDomainModel, val options: List<ArtistDomainModel>)
