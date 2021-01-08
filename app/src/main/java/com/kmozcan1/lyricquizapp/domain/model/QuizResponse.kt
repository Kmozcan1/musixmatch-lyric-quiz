package com.kmozcan1.lyricquizapp.domain.model

/**
 * Created by Kadir Mert Özcan on 08-Jan-21.
 */
data class QuizResponse(
    val responseType: ResponseType,
    val timeLimit: Long? = null,
    val question: Question? = null,
    val answer: Answer? = null,
    val finalScore: Int? = null
) {
    companion object {
        fun quizGenerated(timeLimit: Long): QuizResponse = QuizResponse(
            responseType = ResponseType.QUIZ_GENERATED,
            timeLimit = timeLimit
        )

        fun question(question: Question): QuizResponse = QuizResponse(
            responseType = ResponseType.QUESTION,
            question = question
        )

        fun answer(answer: Answer): QuizResponse = QuizResponse(
            responseType = ResponseType.ANSWER,
            answer = answer
        )

        fun finalizeQuiz(finalScore: Int): QuizResponse = QuizResponse(
            responseType = ResponseType.FINALIZE_QUIZ,
            finalScore = finalScore
        )
    }

    enum class ResponseType {
        QUIZ_GENERATED,
        QUESTION,
        ANSWER,
        FINALIZE_QUIZ
    }
}