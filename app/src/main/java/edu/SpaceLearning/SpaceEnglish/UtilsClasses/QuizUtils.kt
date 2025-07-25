package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.net.Uri
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.AppDatabase

/**
 * QuizUtils class contains various utility methods and constants used throughout the application.
 * It includes static fields for language settings, quiz names, lists for correct/incorrect answers,
 * database table names mapping, maximum allowed numbers, user profile URI, user name,
 * and methods to fill lists and mappings used in different parts of the application.
 */
object QuizUtils {
    //------ the main qst in native language----------

    //var userNativeLanguage: String = "French"
    var translationLanguage: String = "French"


    var isDarkThemeEnabled: Boolean = false

    // Lists for quiz responses
    var correctAnswerMessages: ArrayList<String> = ArrayList()
    var incorrectAnswerMessages: ArrayList<String> = ArrayList()

    // Lists and mappings for database tables and UI elements
    var quizCategoryNames: ArrayList<String> = ArrayList()
    var pdfHeaderColumns: ArrayList<String> = ArrayList()

    var quizNavigationItems: ArrayList<ButtonItem> = ArrayList()
    var categoryToTableMap: HashMap<String, String> = HashMap()

    var isRewardedAdEnabled: Boolean = true
    var maxQuestionsInQuiz: Int = 10

    // Maximum allowed numbers for different categories

    var maxVerbs: Int = 100
    var maxPhrasal: Int = 50
    var maxSentences: Int = 50
    var maxNouns: Int = 50
    var maxAdjectives: Int = 50
    var maxAdverbs: Int = 50
    var maxIdioms: Int = 50

    // Total numbers for different categories

    var totalVerbs: Int = 0
    var totalPhrasal: Int = 0
    var totalSentences: Int = 0
    var totalNouns: Int = 0
    var totalAdjectives: Int = 0
    var totalAdverbs: Int = 0
    var totalIdioms: Int = 0

    // User profile information
    var profileUri: Uri? = null

    var userDisplayName: String = "User 01"
    var isFirstTimeUser: Boolean = false

    /**
     * Clears and fills the phrasesCorrectAnswers and phrasesIncorrectAnswers lists with default responses.
     */

    fun fillAnswerFeedbackMessages() {
        correctAnswerMessages.also {
            it.clear()
            it.add("Fantastic! You've got it right!")
            it.add("Correctamundo! You're on fire!")
            it.add("Bingo! Nailed it!")
            it.add("Well done! You're a quiz whiz!")
            it.add("You're absolutely correct! Keep it up!")
            it.add("You're on a roll! That's the right answer!")
            it.add("Gold star! You've chosen the correct option!")
            it.add("Bravo! You're proving your expertise!")
            it.add("Excellent choice! You're acing this quiz!")
            it.add("That's the ticket! You're cruising through these questions!")
        }

        incorrectAnswerMessages.also {
            it.clear()
            it.add("Oops! Not quite, but don't give up!")
            it.add("Almost there, but not quite. Try again!")
            it.add("Oh no! That's not the right answer.")
            it.add("Close, but not exactly. Give it another shot!")
            it.add("Not quite this time. Keep trying!")
            it.add("Darn! That's not the correct answer.")
            it.add("Oopsie daisy! Keep guessing, you'll get it!")
            it.add("Don't worry, you'll get it next time! Try again!")
            it.add("That's not it, but you're learning with every try!")
            it.add("Keep going! Mistakes are part of the learning process.")
        }
    }

    /**
     * Clears and fills the tableHashNames mapping with category names and their corresponding database table names.
     */

    fun mapCategoryToDatabaseTable() {

        for (i in 0 until Constants.CATEGORY_NAME_LIST.size)  {
            categoryToTableMap[Constants.CATEGORY_NAME_LIST[i]] = AppDatabase.dataBaseTablesNames[i]
        }
    }

    /**
     * Clears and fills the tableListNames list with category names used in the application.
     */

    fun populateCategoryNameList() {
        quizCategoryNames.also {
            it.clear()
            it.add(Constants.CATEGORY_NAME_ALL)
            it.add(Constants.CATEGORY_NAME_VERB)
            it.add(Constants.CATEGORY_NAME_SENTENCE)
            it.add(Constants.CATEGORY_NAME_PHRASAL)
            it.add(Constants.CATEGORY_NAME_NOUNS)
            it.add(Constants.CATEGORY_NAME_ADJECTIVE)
            it.add(Constants.CATEGORY_NAME_ADVERBS)
            it.add(Constants.CATEGORY_NAME_IDIOMS)
        }

    }

    /**
     * Clears and fills the headerTablePdfList list with column header names for PDF tables.
     */

    fun populatePdfHeaders() {
        pdfHeaderColumns.also {
            it.clear()
            it.add("ID")
            it.add("ENGLISH")
            it.add("Native language")
        }
    }

    /**
     * Clears and fills the itemRecyclerQuizNavList list with navigation items for quizzes.
     * Uses predefined quiz categories and their requirements.
     */

    fun populateQuizNavigationItems() {
        quizNavigationItems.clear()
        quizNavigationItems.add(
            ButtonItem(
                Constants.CATEGORY_NAME_LIST[0], "opened"
            )
        )
        quizNavigationItems.add(
            ButtonItem(
                Constants.CATEGORY_NAME_LIST[1], "opened"
            )
        )
        quizNavigationItems.add(
            ButtonItem(
                Constants.CATEGORY_NAME_LIST[2], "Phrasal verbs required 80 points"
            )
        )
        quizNavigationItems.add(
            ButtonItem(
                Constants.CATEGORY_NAME_LIST[3], "Nouns required 150 points"
            )
        )
        quizNavigationItems.add(
            ButtonItem(
                Constants.CATEGORY_NAME_LIST[4], "Adjectives required 250 points"
            )
        )
        quizNavigationItems.add(
            ButtonItem(
                Constants.CATEGORY_NAME_LIST[5], "Adverbs required 400 points"
            )
        )
        quizNavigationItems.add(
            ButtonItem(
                Constants.CATEGORY_NAME_LIST[6], "Idioms required 600 points"
            )
        )
    }
}
