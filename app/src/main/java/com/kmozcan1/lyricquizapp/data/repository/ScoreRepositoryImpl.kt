package com.kmozcan1.lyricquizapp.data.repository

import com.kmozcan1.lyricquizapp.data.db.QuizDatabase
import com.kmozcan1.lyricquizapp.data.db.entity.ScoreEntity
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.ScoreRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(private val quizDatabase: QuizDatabase)
    : ScoreRepository {
    override fun insertScoreToDatabase(scoreModel: ScoreDomainModel): Completable {
        return quizDatabase.scoreDao().insertScoreEntity(
            ScoreEntity(userName = scoreModel.userName, score = scoreModel.score)
        )
    }

    override fun getLastTenUserScoresFromDatabase(userName: String): Single<List<ScoreEntity>> {
        return quizDatabase.scoreDao().getLastTenUserScores(userName = userName)
    }

    override fun getTopScoresFromDatabase(): Single<List<ScoreEntity>> {
        return quizDatabase.scoreDao().getTopScores()
    }
}