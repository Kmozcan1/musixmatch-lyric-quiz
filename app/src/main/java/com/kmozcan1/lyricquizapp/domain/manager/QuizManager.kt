package com.kmozcan1.lyricquizapp.domain.manager


import com.kmozcan1.lyricquizapp.domain.Constants.NUMBER_OF_TRACKS_TO_FETCH
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_FIVE_MAX_INDEX
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_FIVE_WEIGHT
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_FOUR_MAX_INDEX
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_FOUR_WEIGHT
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_ONE_MAX_INDEX
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_ONE_WEIGHT
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_THREE_MAX_INDEX
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_THREE_WEIGHT
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_TWO_MAX_INDEX
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIER_TWO_WEIGHT
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Artist
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
class QuizManager @Inject constructor() {

    private val artistList = mutableListOf<Artist>()

    // Method for generating questions.
    // To create a fair quiz each time, same amount of tracks are chosen from index ranges
    // designated in the QuestRules.
    fun selectTracks(
            trackList: List<TrackDomainModel>, numberOfQuestions: Int
    ) : List<TrackDomainModel> {
        val quizRules = QuizRules()
        val quizTiers = quizRules.tierList
        val totalTierWeight = quizRules.totalTierWeight
        val chosenTrackList = mutableListOf<TrackDomainModel>()

        // Extracts the artists from trackList. They'll be later used as options in the questions.
        setArtistList(trackList)

        // Take distinct random Track objects for each tier from designated indexes
        // Number depends tier weight. Since the range is small, it's okay to use shuffled take
        for (tier in quizTiers) {
            val tierQuestionCount = (numberOfQuestions * tier.weight / totalTierWeight).toInt()

            // It's not a big deal since the list size isn't big,
            // but I prefer not shuffling when it's possible.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                // Random indices to take random tracks for a tier
                val randomIndices = ThreadLocalRandom.current()
                        .ints(tier.startIndex, tier.endIndex)
                        .distinct().limit(tierQuestionCount.toLong())
                for (i in randomIndices) {
                    chosenTrackList.add(trackList[i])
                }
            } else {
                trackList.subList(tier.startIndex, tier.endIndex).shuffled()
                        .take(tierQuestionCount)
                        .forEach {
                            chosenTrackList.add(it)
                        }
            }
        }

        chosenTrackList.shuffle()

        //TODO buraya bak
        return chosenTrackList
    }

    private fun setArtistList(trackList: List<TrackDomainModel>) {
        for (i in trackList.indices) {
            val track = trackList[i]
            artistList.add(Artist(track.artistId!!, track.artistName!!))
        }
    }


    /**
     * QuizRules class for the possibility of introducing additional difficulty modes in the future.
     * It's an inner class because it should access the outer class's objects, but should not be used by any other class.
     * */
    private inner class QuizRules {
        val tierList: MutableList<Tier> = mutableListOf()

        var totalTierWeight by Delegates.notNull<Float>()
            private set

        init {
            withDefaultTiers()
        }

        private fun withDefaultTiers() {
            tierList.add(Tier(0, DEFAULT_TIER_ONE_MAX_INDEX, DEFAULT_TIER_ONE_WEIGHT))
            tierList.add(Tier(DEFAULT_TIER_ONE_MAX_INDEX, DEFAULT_TIER_TWO_MAX_INDEX, DEFAULT_TIER_TWO_WEIGHT))
            tierList.add(Tier(DEFAULT_TIER_TWO_MAX_INDEX, DEFAULT_TIER_THREE_MAX_INDEX, DEFAULT_TIER_THREE_WEIGHT))
            tierList.add(Tier(DEFAULT_TIER_THREE_MAX_INDEX , DEFAULT_TIER_FOUR_MAX_INDEX, DEFAULT_TIER_FOUR_WEIGHT))
            tierList.add(Tier(DEFAULT_TIER_FOUR_MAX_INDEX , DEFAULT_TIER_FIVE_MAX_INDEX, DEFAULT_TIER_FIVE_WEIGHT))
            setTotalTierWeight()
        }

        private fun setTotalTierWeight() {
            for (tier in tierList) {
                totalTierWeight = totalTierWeight.plus(tier.weight)
            }
        }
    }

    // Nested class that has Tier attributes. endIndex is exclusive.
    class Tier (internal val startIndex: Int, internal val endIndex: Int, internal val weight: Float)


    object DefaultQuizRuleConstants {
        const val DEFAULT_TIER_ONE_MAX_INDEX = 25
        const val DEFAULT_TIER_TWO_MAX_INDEX = 100
        const val DEFAULT_TIER_THREE_MAX_INDEX = 150
        const val DEFAULT_TIER_FOUR_MAX_INDEX = 250
        const val DEFAULT_TIER_FIVE_MAX_INDEX = NUMBER_OF_TRACKS_TO_FETCH

        const val DEFAULT_TIER_ONE_WEIGHT = 0.1f
        const val DEFAULT_TIER_TWO_WEIGHT = 0.5f
        const val DEFAULT_TIER_THREE_WEIGHT = 0.2f
        const val DEFAULT_TIER_FOUR_WEIGHT = 0.1f
        const val DEFAULT_TIER_FIVE_WEIGHT = 0.1f
        const val TOTAL_WEIGHT = DEFAULT_TIER_ONE_WEIGHT +
                DEFAULT_TIER_TWO_WEIGHT + DEFAULT_TIER_THREE_WEIGHT + DEFAULT_TIER_FOUR_WEIGHT + DEFAULT_TIER_FIVE_WEIGHT
    }
}