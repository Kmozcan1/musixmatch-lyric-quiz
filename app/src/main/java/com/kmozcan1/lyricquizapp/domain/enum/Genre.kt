package com.kmozcan1.lyricquizapp.domain.enum

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
enum class Genre {
    @JsonProperty("mixed")
    MIXED,

    @JsonProperty("pop")
    POP,

    @JsonProperty("rock")
    ROCK
}
