package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.content.SharedPreferences
import android.net.Uri

/**
 * SharedPrefsManager class manages shared preferences for storing and retrieving application data.
 * It handles saving and loading scores, profile information, theme preferences, and quiz completion data.
 */
class SharedPrefsManager(private val sharedPreferences: SharedPreferences) {
    val sharedPreferencesData: Unit
        /**
         * Retrieves shared preferences data related to scores, elements added, quiz completion, and other settings.
         */
        get() {
            QuizUtils.profileUri = Uri.parse(
                sharedPreferences.getString(
                    Constants.PREF_PROFILE_IMAGE_URI,
                    ""
                )
            )
            Scores.totalScore = sharedPreferences.getInt(
                Constants.TAG_PREF_SCORES_TOTAL_SCORE,
                0
            )

            Scores.verbScore = sharedPreferences.getInt(
                Constants.PREF_SCORE_VERBS,
                0
            )
            Scores.sentenceScore = sharedPreferences.getInt(
                Constants.PREF_SCORE_SENTENCES,
                0
            )
            Scores.phrasalScore = sharedPreferences.getInt(
                Constants.PREF_SCORE_PHRASAL,
                0
            )
            Scores.nounScore = sharedPreferences.getInt(
                Constants.PREF_SCORE_NOUNS,
                0
            )
            Scores.adjScore = sharedPreferences.getInt(
                Constants.PREF_SCORE_ADJECTIVES,
                0
            )
            Scores.advScore = sharedPreferences.getInt(
                Constants.PREF_SCORE_ADVERBS,
                0
            )
            Scores.idiomScore = sharedPreferences.getInt(
                Constants.PREF_SCORE_IDIOMS,
                0
            )

            // Elements added
            Scores.verbAdded = sharedPreferences.getInt(
                Constants.PREF_VERB_ADDED,
                0
            )
            Scores.sentenceAdded = sharedPreferences.getInt(
                Constants.PREF_SENTENCE_ADDED,
                0
            )
            Scores.phrasalAdded = sharedPreferences.getInt(
                Constants.PREF_PHRASAL_ADDED,
                0
            )
            Scores.nounAdded = sharedPreferences.getInt(
                Constants.PREF_NOUN_ADDED,
                0
            )
            Scores.adjAdded = sharedPreferences.getInt(
                Constants.PREF_ADJECTIVE_ADDED,
                0
            )
            Scores.advAdded = sharedPreferences.getInt(
                Constants.PREF_ADVERB_ADDED,
                0
            )
            Scores.idiomAdded = sharedPreferences.getInt(
                Constants.PREF_IDIOM_ADDED,
                0
            )

            // Elements added via video
            Scores.verbAddedVideo = sharedPreferences.getInt(
                Constants.PREF_VERB_ADDED_VIDEO,
                0
            )
            Scores.sentenceAddedVideo = sharedPreferences.getInt(
                Constants.PREF_SENTENCE_ADDED_VIDEO,
                0
            )
            Scores.phrasalAddedVideo = sharedPreferences.getInt(
                Constants.PREF_PHRASAL_ADDED_VIDEO,
                0
            )
            Scores.nounAddedVideo = sharedPreferences.getInt(
                Constants.PREF_NOUN_ADDED_VIDEO,
                0
            )
            Scores.adjAddedVideo = sharedPreferences.getInt(
                Constants.PREF_ADJECTIVE_ADDED_VIDEO,
                0
            )
            Scores.advAddedVideo = sharedPreferences.getInt(
                Constants.PREF_ADVERB_ADDED_VIDEO,
                0
            )
            Scores.idiomAddedVideo = sharedPreferences.getInt(
                Constants.PREF_IDIOM_ADDED_VIDEO,
                0
            )

            // Points added via video
            Scores.verbPointsAddedVideo = sharedPreferences.getInt(
                Constants.PREF_VERB_POINT_ADDED_VIDEO,
                0
            )
            Scores.sentencePointsAddedVideo = sharedPreferences.getInt(
                Constants.PREF_SENTENCE_POINT_ADDED_VIDEO,
                0
            )
            Scores.phrasalPointsAddedVideo = sharedPreferences.getInt(
                Constants.PREF_PHRASAL_POINT_ADDED_VIDEO,
                0
            )
            Scores.nounPointsAddedVideo = sharedPreferences.getInt(
                Constants.PREF_NOUN_POINT_ADDED_VIDEO,
                0
            )
            Scores.adjPointsAddedVideo = sharedPreferences.getInt(
                Constants.PREF_ADJECTIVE_POINT_ADDED_VIDEO,
                0
            )
            Scores.advPointsAddedVideo = sharedPreferences.getInt(
                Constants.PREF_ADVERB_POINT_ADDED_VIDEO,
                0
            )
            Scores.idiomPointsAddedVideo = sharedPreferences.getInt(
                Constants.PREF_IDIOM_POINT_ADDED_VIDEO,
                0
            )

            // Quiz completion counters
            Scores.verbQuizCompleted = sharedPreferences.getInt(
                Constants.PREF_VERB_QUIZ_COMPLETED,
                0
            )
            Scores.sentenceQuizCompleted = sharedPreferences.getInt(
                Constants.PREF_SENTENCE_QUIZ_COMPLETED,
                0
            )
            Scores.phrasalQuizCompleted = sharedPreferences.getInt(
                Constants.PREF_PHRASAL_QUIZ_COMPLETED,
                0
            )
            Scores.nounQuizCompleted = sharedPreferences.getInt(
                Constants.PREF_NOUN_QUIZ_COMPLETED,
                0
            )
            Scores.adjQuizCompleted = sharedPreferences.getInt(
                Constants.PREF_ADJECTIVE_QUIZ_COMPLETED,
                0
            )
            Scores.advQuizCompleted = sharedPreferences.getInt(
                Constants.PREF_ADVERB_QUIZ_COMPLETED,
                0
            )
            Scores.idiomQuizCompleted = sharedPreferences.getInt(
                Constants.PREF_IDIOM_QUIZ_COMPLETED,
                0
            )

            // Quiz completion counters correctly
            Scores.verbQuizCompletedCorrectly = sharedPreferences.getInt(
                Constants.PREF_VERB_QUIZ_COMPLETED_CORRECT,
                0
            )
            Scores.sentenceQuizCompletedCorrectly = sharedPreferences.getInt(
                Constants.PREF_SENTENCE_QUIZ_COMPLETED_CORRECT,
                0
            )
            Scores.phrasalQuizCompletedCorrectly = sharedPreferences.getInt(
                Constants.PREF_PHRASAL_QUIZ_COMPLETED_CORRECT,
                0
            )
            Scores.nounQuizCompletedCorrectly = sharedPreferences.getInt(
                Constants.PREF_NOUN_QUIZ_COMPLETED_CORRECT,
                0
            )
            Scores.adjQuizCompletedCorrectly = sharedPreferences.getInt(
                Constants.PREF_ADJECTIVE_QUIZ_COMPLETED_CORRECT,
                0
            )
            Scores.advQuizCompletedCorrectly = sharedPreferences.getInt(
                Constants.PREF_ADVERB_QUIZ_COMPLETED_CORRECT,
                0
            )
            Scores.idiomQuizCompletedCorrectly = sharedPreferences.getInt(
                Constants.PREF_IDIOM_QUIZ_COMPLETED_CORRECT,
                0
            )

            // Allowed maximum numbers for each category
            QuizUtils.maxVerbs =
                sharedPreferences.getInt(
                    Constants.PREF_ALLOWED_VERBS_COUNT,
                    100
                )
            QuizUtils.maxSentences =
                sharedPreferences.getInt(
                    Constants.PREF_ALLOWED_SENTENCES_COUNT,
                    50
                )
            QuizUtils.maxPhrasal =
                sharedPreferences.getInt(
                    Constants.PREF_ALLOWED_PHRASAL_COUNT,
                    50
                )
            QuizUtils.maxNouns =
                sharedPreferences.getInt(
                    Constants.PREF_ALLOWED_NOUNS_COUNT,
                    50
                )
            QuizUtils.maxAdjectives =
                sharedPreferences.getInt(
                    Constants.PREF_ALLOWED_ADJECTIVES_COUNT,
                    50
                )
            QuizUtils.maxAdverbs =
                sharedPreferences.getInt(
                    Constants.PREF_ALLOWED_ADVERBS_COUNT,
                    50
                )
            QuizUtils.maxIdioms =
                sharedPreferences.getInt(
                    Constants.PREF_ALLOWED_IDIOMS_COUNT,
                    50
                )

            QuizUtils.translationLanguage =
                sharedPreferences.getString(
                    Constants.PREF_NATIVE_LANGUAGE_DEFAULT,
                    Constants.LANGUAGE_NATIVE_FRENCH
                ).toString()
        }

    val sharedPrefTheme: Unit
        /**
         * Retrieves shared preferences data related to theme settings.
         */
        get() {
            QuizUtils.isDarkThemeEnabled =
                sharedPreferences.getBoolean(
                    Constants.PREF_IS_THEME_DARK_MODE,
                    false
                )
            QuizUtils.isFirstTimeUser =
                sharedPreferences.getBoolean(
                    Constants.PREF_IS_FIRST_LAUNCH,
                    false
                )
        }

    /**
     * Saves shared preferences data related to theme settings.
     *
     * @param editor SharedPreferences.Editor instance to apply changes.
     */
    fun putSharedPrefTheme(editor: SharedPreferences.Editor) {
        editor.putBoolean(
            Constants.PREF_IS_THEME_DARK_MODE,
            QuizUtils.isDarkThemeEnabled
        ) // Set the theme mode
        editor.putBoolean(Constants.PREF_IS_FIRST_LAUNCH, QuizUtils.isFirstTimeUser)
        editor.apply()
    }

    /**
     * Saves shared preferences data related to scores, elements added, quiz completion, and other settings.
     *
     * @param editor SharedPreferences.Editor instance to apply changes.
     */
    fun putSharedPreferencesScores(editor: SharedPreferences.Editor) {
        // Put category main scores in shared preferences
        editor.putInt(Constants.PREF_SCORE_VERBS, Scores.verbScore)
        editor.putInt(Constants.PREF_SCORE_SENTENCES, Scores.sentenceScore)
        editor.putInt(Constants.PREF_SCORE_PHRASAL, Scores.phrasalScore)
        editor.putInt(Constants.PREF_SCORE_NOUNS, Scores.nounScore)
        editor.putInt(Constants.PREF_SCORE_ADJECTIVES, Scores.adjScore)
        editor.putInt(Constants.PREF_SCORE_ADVERBS, Scores.advScore)
        editor.putInt(Constants.PREF_SCORE_IDIOMS, Scores.idiomScore)

        // Elements added
        editor.putInt(Constants.PREF_VERB_ADDED, Scores.verbAdded)
        editor.putInt(Constants.PREF_SENTENCE_ADDED, Scores.sentenceAdded)
        editor.putInt(Constants.PREF_PHRASAL_ADDED, Scores.phrasalAdded)
        editor.putInt(Constants.PREF_NOUN_ADDED, Scores.nounAdded)
        editor.putInt(Constants.PREF_ADJECTIVE_ADDED, Scores.adjAdded)
        editor.putInt(Constants.PREF_ADVERB_ADDED, Scores.advAdded)
        editor.putInt(Constants.PREF_IDIOM_ADDED, Scores.idiomAdded)

        // Elements added via video
        editor.putInt(Constants.PREF_VERB_ADDED_VIDEO, Scores.verbAddedVideo)
        editor.putInt(Constants.PREF_SENTENCE_ADDED_VIDEO, Scores.sentenceAddedVideo)
        editor.putInt(Constants.PREF_PHRASAL_ADDED_VIDEO, Scores.phrasalAddedVideo)
        editor.putInt(Constants.PREF_NOUN_ADDED_VIDEO, Scores.nounAddedVideo)
        editor.putInt(Constants.PREF_ADJECTIVE_ADDED_VIDEO, Scores.adjAddedVideo)
        editor.putInt(Constants.PREF_ADVERB_ADDED_VIDEO, Scores.advAddedVideo)
        editor.putInt(Constants.PREF_IDIOM_ADDED_VIDEO, Scores.idiomAddedVideo)

        // Number of quizzes completed
        editor.putInt(Constants.PREF_VERB_QUIZ_COMPLETED, Scores.verbQuizCompleted)
        editor.putInt(Constants.PREF_SENTENCE_QUIZ_COMPLETED, Scores.sentenceQuizCompleted)
        editor.putInt(Constants.PREF_PHRASAL_QUIZ_COMPLETED, Scores.phrasalQuizCompleted)
        editor.putInt(Constants.PREF_NOUN_QUIZ_COMPLETED, Scores.nounQuizCompleted)
        editor.putInt(Constants.PREF_ADJECTIVE_QUIZ_COMPLETED, Scores.adjQuizCompleted)
        editor.putInt(Constants.PREF_ADVERB_QUIZ_COMPLETED, Scores.advQuizCompleted)
        editor.putInt(Constants.PREF_IDIOM_QUIZ_COMPLETED, Scores.idiomQuizCompleted)

        // Number of quizzes completed correctly
        editor.putInt(
            Constants.PREF_VERB_QUIZ_COMPLETED_CORRECT,
            Scores.verbQuizCompletedCorrectly
        )
        editor.putInt(
            Constants.PREF_SENTENCE_QUIZ_COMPLETED_CORRECT,
            Scores.sentenceQuizCompletedCorrectly
        )
        editor.putInt(
            Constants.PREF_PHRASAL_QUIZ_COMPLETED_CORRECT,
            Scores.phrasalQuizCompletedCorrectly
        )
        editor.putInt(
            Constants.PREF_NOUN_QUIZ_COMPLETED_CORRECT,
            Scores.nounQuizCompletedCorrectly
        )
        editor.putInt(
            Constants.PREF_ADJECTIVE_QUIZ_COMPLETED_CORRECT,
            Scores.adjQuizCompletedCorrectly
        )
        editor.putInt(
            Constants.PREF_ADVERB_QUIZ_COMPLETED_CORRECT,
            Scores.advQuizCompletedCorrectly
        )
        editor.putInt(
            Constants.PREF_IDIOM_QUIZ_COMPLETED_CORRECT,
            Scores.idiomQuizCompletedCorrectly
        )

        // Points added via video
        editor.putInt(Constants.PREF_VERB_POINT_ADDED_VIDEO, Scores.verbPointsAddedVideo)
        editor.putInt(
            Constants.PREF_SENTENCE_POINT_ADDED_VIDEO,
            Scores.sentencePointsAddedVideo
        )
        editor.putInt(Constants.PREF_PHRASAL_POINT_ADDED_VIDEO, Scores.phrasalPointsAddedVideo)
        editor.putInt(Constants.PREF_NOUN_POINT_ADDED_VIDEO, Scores.nounPointsAddedVideo)
        editor.putInt(Constants.PREF_ADJECTIVE_POINT_ADDED_VIDEO, Scores.adjPointsAddedVideo)
        editor.putInt(Constants.PREF_ADVERB_POINT_ADDED_VIDEO, Scores.advPointsAddedVideo)
        editor.putInt(Constants.PREF_IDIOM_POINT_ADDED_VIDEO, Scores.idiomPointsAddedVideo)

        // Put allowed max numbers in shared preferences
        editor.putInt(Constants.PREF_ALLOWED_VERBS_COUNT, QuizUtils.maxVerbs)
        editor.putInt(Constants.PREF_ALLOWED_SENTENCES_COUNT, QuizUtils.maxSentences)
        editor.putInt(Constants.PREF_ALLOWED_PHRASAL_COUNT, QuizUtils.maxPhrasal)
        editor.putInt(Constants.PREF_ALLOWED_NOUNS_COUNT, QuizUtils.maxNouns)
        editor.putInt(Constants.PREF_ALLOWED_ADJECTIVES_COUNT, QuizUtils.maxAdjectives)
        editor.putInt(Constants.PREF_ALLOWED_ADVERBS_COUNT, QuizUtils.maxAdverbs)
        editor.putInt(Constants.PREF_ALLOWED_IDIOMS_COUNT, QuizUtils.maxIdioms)

        editor.putString(
            Constants.PREF_NATIVE_LANGUAGE_DEFAULT,
            QuizUtils.translationLanguage
        ) // Set preferences of current native language

        editor.putString(Constants.PREF_PROFILE_IMAGE_URI, QuizUtils.profileUri.toString())
        editor.putInt(Constants.TAG_PREF_SCORES_TOTAL_SCORE, Scores.totalScore)

        editor.apply()
    }
}
