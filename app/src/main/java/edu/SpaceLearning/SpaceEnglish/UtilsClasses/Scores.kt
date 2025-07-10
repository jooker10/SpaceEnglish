package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * Scores class to hold various score and counter variables related to the application.
 * These variables represent total scores, points added through videos, elements added, and quiz completion counters.
 */
object Scores {
    // Total scores and counters
    @JvmField
    var totalScore: Int = 0
    var totalPointsAddedVideo: Int = 0
    var totalElementsAdded: Int = 0
    var totalElementsAddedVideo: Int = 0
    var totalQuizCompleted: Int = 0
    var totalQuizCompletedCorrectly: Int = 0

    // Scores for individual categories
    @JvmField
    var verbScore: Int = 0
    @JvmField
    var sentenceScore: Int = 0
    @JvmField
    var phrasalScore: Int = 0
    @JvmField
    var nounScore: Int = 0
    @JvmField
    var adjScore: Int = 0
    @JvmField
    var advScore: Int = 0
    @JvmField
    var idiomScore: Int = 0

    // Points added through videos for each category
    @JvmField
    var verbPointsAddedVideo: Int = 0
    @JvmField
    var sentencePointsAddedVideo: Int = 0
    @JvmField
    var phrasalPointsAddedVideo: Int = 0
    @JvmField
    var nounPointsAddedVideo: Int = 0
    @JvmField
    var adjPointsAddedVideo: Int = 0
    @JvmField
    var advPointsAddedVideo: Int = 0
    @JvmField
    var idiomPointsAddedVideo: Int = 0

    // Counters for elements added in each category
    @JvmField
    var verbAdded: Int = 0
    @JvmField
    var sentenceAdded: Int = 0
    @JvmField
    var phrasalAdded: Int = 0
    @JvmField
    var nounAdded: Int = 0
    @JvmField
    var adjAdded: Int = 0
    @JvmField
    var advAdded: Int = 0
    @JvmField
    var idiomAdded: Int = 0

    // Counters for elements added via video in each category
    @JvmField
    var verbAddedVideo: Int = 0
    @JvmField
    var sentenceAddedVideo: Int = 0
    @JvmField
    var phrasalAddedVideo: Int = 0
    @JvmField
    var nounAddedVideo: Int = 0
    @JvmField
    var adjAddedVideo: Int = 0
    @JvmField
    var advAddedVideo: Int = 0
    @JvmField
    var idiomAddedVideo: Int = 0

    // Quiz completion counters for each category
    @JvmField
    var verbQuizCompleted: Int = 0
    @JvmField
    var sentenceQuizCompleted: Int = 0
    @JvmField
    var phrasalQuizCompleted: Int = 0
    @JvmField
    var nounQuizCompleted: Int = 0
    @JvmField
    var adjQuizCompleted: Int = 0
    @JvmField
    var advQuizCompleted: Int = 0
    @JvmField
    var idiomQuizCompleted: Int = 0

    // Quiz completion counters for each category correctly
    @JvmField
    var verbQuizCompletedCorrectly: Int = 0
    @JvmField
    var sentenceQuizCompletedCorrectly: Int = 0
    @JvmField
    var phrasalQuizCompletedCorrectly: Int = 0
    @JvmField
    var nounQuizCompletedCorrectly: Int = 0
    @JvmField
    var adjQuizCompletedCorrectly: Int = 0
    @JvmField
    var advQuizCompletedCorrectly: Int = 0
    @JvmField
    var idiomQuizCompletedCorrectly: Int = 0
}
