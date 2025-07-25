package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * Scores class to hold various score and counter variables related to the application.
 * These variables represent total scores, points added through videos, elements added, and quiz completion counters.
 */
object Scores {
    // Total scores and counters

    var totalScore: Int = 0
    var totalPointsAddedVideo: Int = 0
    var totalElementsAdded: Int = 0
    var totalElementsAddedVideo: Int = 0
    var totalQuizCompleted: Int = 0
    var totalQuizCompletedCorrectly: Int = 0

    // Scores for individual categories
    var verbScore: Int = 0
    var sentenceScore: Int = 0
    var phrasalScore: Int = 0
    var nounScore: Int = 0
    var adjScore: Int = 0
    var advScore: Int = 0
    var idiomScore: Int = 0

    // Points added through videos for each category
    var verbPointsAddedVideo: Int = 0
    var sentencePointsAddedVideo: Int = 0
    var phrasalPointsAddedVideo: Int = 0
    var nounPointsAddedVideo: Int = 0
    var adjPointsAddedVideo: Int = 0
    var advPointsAddedVideo: Int = 0
    var idiomPointsAddedVideo: Int = 0

    // Counters for elements added in each category

    var verbAdded: Int = 0
    var sentenceAdded: Int = 0
    var phrasalAdded: Int = 0
    var nounAdded: Int = 0
    var adjAdded: Int = 0
    var advAdded: Int = 0
    var idiomAdded: Int = 0

    // Counters for elements added via video in each category
    var verbAddedVideo: Int = 0
    var sentenceAddedVideo: Int = 0
    var phrasalAddedVideo: Int = 0
    var nounAddedVideo: Int = 0
    var adjAddedVideo: Int = 0
    var advAddedVideo: Int = 0
    var idiomAddedVideo: Int = 0

    // Quiz completion counters for each category

    var verbQuizCompleted: Int = 0

    var sentenceQuizCompleted: Int = 0

    var phrasalQuizCompleted: Int = 0

    var nounQuizCompleted: Int = 0

    var adjQuizCompleted: Int = 0

    var advQuizCompleted: Int = 0

    var idiomQuizCompleted: Int = 0

    // Quiz completion counters for each category correctly
    var verbQuizCompletedCorrectly: Int = 0

    var sentenceQuizCompletedCorrectly: Int = 0

    var phrasalQuizCompletedCorrectly: Int = 0

    var nounQuizCompletedCorrectly: Int = 0

    var adjQuizCompletedCorrectly: Int = 0

    var advQuizCompletedCorrectly: Int = 0

    var idiomQuizCompletedCorrectly: Int = 0
}
