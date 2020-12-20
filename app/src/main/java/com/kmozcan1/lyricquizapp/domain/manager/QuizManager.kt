package com.kmozcan1.lyricquizapp.domain.manager


import com.kmozcan1.lyricquizapp.domain.Constants.DISCLAIMER_LINE
import com.kmozcan1.lyricquizapp.domain.Constants.NUMBER_OF_OPTIONS
import com.kmozcan1.lyricquizapp.domain.Constants.NUMBER_OF_TRACKS_TO_FETCH
import com.kmozcan1.lyricquizapp.domain.enumeration.QuizDifficulty
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_MINIMUM_LINE_LENGTH
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_NUMBER_OF_QUESTIONS
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
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager.DefaultQuizRuleConstants.DEFAULT_TIME_LIMIT
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.properties.Delegates
import kotlin.random.Random


/**
 * Contains Quiz business logic
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
class QuizManager constructor(private val difficulty: QuizDifficulty) {

    private val artistMap = mutableMapOf<Int, ArtistDomainModel>()
    private val chosenTracksMap = mutableMapOf<Int, TrackDomainModel>()
    private val questionList = mutableListOf<Question>()
    private var quizRules: QuizRules

    init {
        quizRules = QuizRules(difficulty)
    }

    // Method for generating questions.
    // To create a fair quiz each time, same amount of tracks are chosen from index ranges
    // designated in the QuestRules.
    fun selectTracks(
        trackList: List<TrackDomainModel>
    ) : MutableMap<Int, TrackDomainModel> {
        // Extracts the artists from trackList. They'll be later used as options in the questions.
        setArtistMap(trackList)

        // Take distinct random Track objects for each tier from designated indexes
        // Number depends tier weight. Since the range is small, it's okay to use shuffled take
        for (tier in quizRules.tierList) {
            val tierQuestionCount = (quizRules.numberOfQuestions * tier.weight / quizRules.totalTierWeight).toInt()

            // It's not a big deal since the list size isn't big,
            // but it's still better to avoid shuffling here when possible (SDK v24 and above).
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                // Random indices to take random tracks for a tier
                val randomIndices = ThreadLocalRandom.current()
                        .ints(tier.startIndex, tier.endIndex)
                        .distinct().limit(tierQuestionCount.toLong())
                for (i in randomIndices) {
                    trackList[i].let { track ->
                        chosenTracksMap[track.trackId!!] = track
                    }
                }
            } else {
                trackList.subList(tier.startIndex, tier.endIndex).shuffled()
                        .take(tierQuestionCount)
                        .forEach { track ->
                            chosenTracksMap[track.trackId!!] = track
                        }
            }
        }

        // Not all chunks will be divisible by their percentage.
        // I complete the list by adding random songs from the list
        while (chosenTracksMap.size < quizRules.numberOfQuestions) {
            var rand = Random.nextInt(0, trackList.size)
            while (chosenTracksMap.containsKey(trackList[rand].trackId )) {
                rand = Random.nextInt(0, trackList.size)
            }
            var randomTrack = trackList[rand]
            chosenTracksMap[randomTrack.trackId!!] = randomTrack
        }

        return chosenTracksMap
    }

    private fun setArtistMap(trackList: List<TrackDomainModel>) {
        for (i in trackList.indices) {
            val track = trackList[i]
            track.artistId?.let{ artistId ->
                artistMap[artistId] = ArtistDomainModel(track.artistId, track.artistName!!)
            }

        }
    }

    fun generateQuiz(lyricsList: List<LyricsDomainModel>): Quiz {
        for (i in lyricsList.indices) {
            val track = chosenTracksMap[lyricsList[i].trackId]
            val answer = artistMap[track!!.artistId]
            val optionList = generateOptions(track.artistId)
            val selectedQuestion = selectQuestionLyrics(lyricsList[i])

            val question = Question(i, selectedQuestion, answer!!, optionList)
            questionList.add(question)
        }

        return Quiz(questionList, quizRules.timeLimit)
    }

    // Selects 3 random artists and the artist of the question lyric to present the user as options
    private fun generateOptions(excludedArtistId: Int?) : List<ArtistDomainModel> {
        val optionList = mutableListOf<ArtistDomainModel>()
        while (optionList.size < NUMBER_OF_OPTIONS - 1) {
            var rand = Random.nextInt(artistMap.size)

            // Take random artist until it's not in the option list
            // or it is not equal to correct artist's id
            while (optionList.any { it.id == artistMap.entries.elementAt(rand).key } ||
                artistMap.entries.elementAt(rand).key == excludedArtistId) {
                rand = Random.nextInt(artistMap.size)
            }
            optionList.add(artistMap.entries.elementAt(rand).value)
        }
        optionList.add(artistMap[excludedArtistId]!!)
        optionList.shuffle()
        return optionList
    }

    // Returns a random lyric to ask as a question
    private fun selectQuestionLyrics(lyrics: LyricsDomainModel): String {
        val lyricLines = lyrics.lyricsBody?.lines()!!.toMutableList()
        lyricLines.remove(DISCLAIMER_LINE)
        val possibleQuestions = lyricLines?.filter {
                line -> line.length >= quizRules.minimumLineLength
        }?.toMutableList()

        // If a song has only one or no lyric lines that satisfy the minimum length requirement
        // add longest 5 lyrics to the list of possible questions
        if (possibleQuestions?.size!! <= 1) {
            possibleQuestions.addAll(lyricLines.sortedBy { line -> line.length }.take(5))
        }

        return possibleQuestions[Random.nextInt(0, possibleQuestions.size)]
    }

    /**
     * QuizRules class for the possibility of introducing additional difficulty modes in the future.
     * It's an inner class because it should access the outer class's objects, but should not be used by any other class.
     * */
    private inner class QuizRules (difficulty: QuizDifficulty) {

        val tierList: MutableList<Tier> = mutableListOf()

        // These are Delegates, so the developer that has to set them when initializing the rules
        var totalTierWeight by Delegates.notNull<Float>()
            private set

        var numberOfQuestions by Delegates.notNull<Int>()
            private set

        var minimumLineLength by Delegates.notNull<Int>()
            private set

        var timeLimit by Delegates.notNull<Long>()
            private set

        init {
            when (difficulty) {
                QuizDifficulty.DEFAULT -> withDefaultRules()
            }
        }

        fun withDefaultRules() {
            numberOfQuestions = DEFAULT_NUMBER_OF_QUESTIONS
            minimumLineLength = DEFAULT_MINIMUM_LINE_LENGTH
            timeLimit = DEFAULT_TIME_LIMIT
            tierList.add(Tier(0, DEFAULT_TIER_ONE_MAX_INDEX, DEFAULT_TIER_ONE_WEIGHT))
            tierList.add(
                Tier(
                    DEFAULT_TIER_ONE_MAX_INDEX,
                    DEFAULT_TIER_TWO_MAX_INDEX,
                    DEFAULT_TIER_TWO_WEIGHT
                )
            )
            tierList.add(
                Tier(
                    DEFAULT_TIER_TWO_MAX_INDEX,
                    DEFAULT_TIER_THREE_MAX_INDEX,
                    DEFAULT_TIER_THREE_WEIGHT
                )
            )
            tierList.add(
                Tier(
                    DEFAULT_TIER_THREE_MAX_INDEX,
                    DEFAULT_TIER_FOUR_MAX_INDEX,
                    DEFAULT_TIER_FOUR_WEIGHT
                )
            )
            tierList.add(
                Tier(
                    DEFAULT_TIER_FOUR_MAX_INDEX,
                    DEFAULT_TIER_FIVE_MAX_INDEX,
                    DEFAULT_TIER_FIVE_WEIGHT
                )
            )
            setTotalTierWeight()
        }


        private fun setTotalTierWeight() {
            totalTierWeight = 0f
            for (tier in tierList) {
                totalTierWeight = totalTierWeight.plus(tier.weight)
            }
        }
    }

    // Nested class that has Tier attributes. endIndex is exclusive.
    class Tier(internal val startIndex: Int, internal val endIndex: Int, internal val weight: Float)


    // Different difficulty modes can be added in the future
    object DefaultQuizRuleConstants {
        // Default umber of questions that will be presented to the player
        const val DEFAULT_NUMBER_OF_QUESTIONS = 15

        // Minimum lyric line character count,
        // so that the user doesn't have to guess which artist said "Yeah!"
        const val DEFAULT_MINIMUM_LINE_LENGTH = 15

        // Default time limit per question
        const val DEFAULT_TIME_LIMIT = 20L

        // Max tier indexes designate in which index a tier will end
        const val DEFAULT_TIER_ONE_MAX_INDEX = 25
        const val DEFAULT_TIER_TWO_MAX_INDEX = 50
        const val DEFAULT_TIER_THREE_MAX_INDEX = 100
        const val DEFAULT_TIER_FOUR_MAX_INDEX = 125
        const val DEFAULT_TIER_FIVE_MAX_INDEX = NUMBER_OF_TRACKS_TO_FETCH

        // Tier weights determine how the questions will be distributed between tiers
        const val DEFAULT_TIER_ONE_WEIGHT = 0.1f
        const val DEFAULT_TIER_TWO_WEIGHT = 0.5f
        const val DEFAULT_TIER_THREE_WEIGHT = 0.2f
        const val DEFAULT_TIER_FOUR_WEIGHT = 0.1f
        const val DEFAULT_TIER_FIVE_WEIGHT = 0.1f
        const val TOTAL_WEIGHT = DEFAULT_TIER_ONE_WEIGHT +
                DEFAULT_TIER_TWO_WEIGHT + DEFAULT_TIER_THREE_WEIGHT + DEFAULT_TIER_FOUR_WEIGHT + DEFAULT_TIER_FIVE_WEIGHT
    }
}