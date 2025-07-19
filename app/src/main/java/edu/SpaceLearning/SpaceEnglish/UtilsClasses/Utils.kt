package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.net.Uri
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.MyDatabase

/**
 * Utils class contains various utility methods and constants used throughout the application.
 * It includes static fields for language settings, quiz names, lists for correct/incorrect answers,
 * database table names mapping, maximum allowed numbers, user profile URI, user name,
 * and methods to fill lists and mappings used in different parts of the application.
 */
object Utils {
    //------ the main qst in native language----------
    @JvmField
    var nativeLanguage: String = "French"

    @JvmField
    var isThemeNight: Boolean = false

    // Quiz names
    var QUIZ_COMPLETED_NAME: String = "Quiz completed"
    var QUIZ_COMPLETED_CORRECTLY_NAME: String = "Quiz completed successfully"
    var QUIZ_ELEMENT_ADDED_NAME: String = "Elements added"
    var QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME: String = "Elements added (Ads)"
    var QUIZ_POINT_ADDED_VIDEO_NAME: String = "Points added (Ads)"

    // Lists for quiz responses
    @JvmField
    var phrasesCorrectAnswers: ArrayList<String> = ArrayList()
    @JvmField
    var phrasesIncorrectAnswers: ArrayList<String> = ArrayList()

    // Lists and mappings for database tables and UI elements
    @JvmField
    var tableCategoriesListNames: ArrayList<String> = ArrayList()
    var headerTablePdfList: ArrayList<String> = ArrayList()
    @JvmField
    var itemRecyclerQuizNavList: ArrayList<ItemButtonsRecycler> = ArrayList()
    @JvmField
    var tableHashNames: HashMap<String, String> = HashMap()

    @JvmField
    var switchSimpleToVideoAds: Boolean = true
    @JvmField
    var maxQuestionsPerQuiz: Int = 10

    // Maximum allowed numbers for different categories
    @JvmField
    var allowedVerbsNumber: Int = 100
    @JvmField
    var allowedPhrasalsNumber: Int = 50
    @JvmField
    var allowedSentencesNumber: Int = 50
    @JvmField
    var allowedNounsNumber: Int = 50
    @JvmField
    var allowedAdjsNumber: Int = 50
    @JvmField
    var allowedAdvsNumber: Int = 50
    @JvmField
    var allowedIdiomsNumber: Int = 50

    // Total numbers for different categories
    @JvmField
    var totalVerbsNumber: Int = 0
    @JvmField
    var totalPhrasalsNumber: Int = 0
    @JvmField
    var totalSentencesNumber: Int = 0
    @JvmField
    var totalNounsNumber: Int = 0
    @JvmField
    var totalAdjsNumber: Int = 0
    @JvmField
    var totalAdvsNumber: Int = 0
    @JvmField
    var totalIdiomsNumber: Int = 0

    // User profile information
    @JvmField
    var uriProfile: Uri? = null
    @JvmField
    var userName: String = "User 1"
    var isFirstTimeActivity: Boolean = false

    /**
     * Clears and fills the phrasesCorrectAnswers and phrasesIncorrectAnswers lists with default responses.
     */
    @JvmStatic
    fun fillQuizListsCorrectIncorrectAnswerResponses() {
        phrasesCorrectAnswers.also {
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

        phrasesIncorrectAnswers.also {
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
    @JvmStatic
    fun fillHashMapDbTableName() {

        for (i in 0 until Constants.categoryNameArray.size)  {
            tableHashNames[Constants.categoryNameArray[i]] = MyDatabase.dataBaseTablesNames[i]
        }

        /*tableHashNames.clear()
        tableHashNames[Constants.VERB_NAME] =
            MyDatabase.TABLE_VERBS
        tableHashNames[Constants.SENTENCE_NAME] =
            MyDatabase.TABLE_SENTENCES
        tableHashNames[Constants.PHRASAL_NAME] =
            MyDatabase.TABLE_PHRASALS
        tableHashNames[Constants.NOUN_NAME] =
            MyDatabase.TABLE_NOUNS
        tableHashNames[Constants.ADJ_NAME] =
            MyDatabase.TABLE_ADJECTIVES
        tableHashNames[Constants.ADV_NAME] =
            MyDatabase.TABLE_ADVERBS
        tableHashNames[Constants.IDIOM_NAME] =
            MyDatabase.TABLE_IDIOMS*/
    }

    /**
     * Clears and fills the tableListNames list with category names used in the application.
     */
    @JvmStatic
    fun fillListOfCategoriesNames() {
        tableCategoriesListNames.also {
            it.clear()
            it.add(Constants.ALL_NAME)
            it.add(Constants.VERB_NAME)
            it.add(Constants.SENTENCE_NAME)
            it.add(Constants.PHRASAL_NAME)
            it.add(Constants.NOUN_NAME)
            it.add(Constants.ADJ_NAME)
            it.add(Constants.ADV_NAME)
            it.add(Constants.IDIOM_NAME)
        }

    }

    /**
     * Clears and fills the headerTablePdfList list with column header names for PDF tables.
     */
    @JvmStatic
    fun headerTablePdfNames() {
        headerTablePdfList.also {
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
    @JvmStatic
    fun fillItemRecyclerQuizNavList() {
        itemRecyclerQuizNavList.clear()
        itemRecyclerQuizNavList.add(
            ItemButtonsRecycler(
                Constants.categoryNameArray[0], "opened"
            )
        )
        itemRecyclerQuizNavList.add(
            ItemButtonsRecycler(
                Constants.categoryNameArray[1], "opened"
            )
        )
        itemRecyclerQuizNavList.add(
            ItemButtonsRecycler(
                Constants.categoryNameArray[2], "Phrasal verbs required 80 points"
            )
        )
        itemRecyclerQuizNavList.add(
            ItemButtonsRecycler(
                Constants.categoryNameArray[3], "Nouns required 150 points"
            )
        )
        itemRecyclerQuizNavList.add(
            ItemButtonsRecycler(
                Constants.categoryNameArray[4], "Adjectives required 250 points"
            )
        )
        itemRecyclerQuizNavList.add(
            ItemButtonsRecycler(
                Constants.categoryNameArray[5], "Adverbs required 400 points"
            )
        )
        itemRecyclerQuizNavList.add(
            ItemButtonsRecycler(
                Constants.categoryNameArray[6], "Idioms required 600 points"
            )
        )
    }
}
