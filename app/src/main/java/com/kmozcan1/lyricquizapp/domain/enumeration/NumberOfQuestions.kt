package com.kmozcan1.lyricquizapp.domain.enumeration

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
enum class NumberOfQuestions(val amount: Int) {
    @JsonProperty("10")
    TEN(10),

    @JsonProperty("20")
    TWELVE(20),

    @JsonProperty("30")
    THIRTY(30)
}