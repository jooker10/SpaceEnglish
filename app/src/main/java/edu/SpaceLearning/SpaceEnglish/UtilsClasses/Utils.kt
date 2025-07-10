package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.MyDatabase;

/**
 * Utils class contains various utility methods and constants used throughout the application.
 * It includes static fields for language settings, quiz names, lists for correct/incorrect answers,
 * database table names mapping, maximum allowed numbers, user profile URI, user name,
 * and methods to fill lists and mappings used in different parts of the application.
 */
public class Utils {

    //------ the main qst in native language----------
    public static String nativeLanguage = "French";

    public static boolean isThemeNight = false;

    // Quiz names
    public static String QUIZ_COMPLETED_NAME = "Quiz completed";
    public static String QUIZ_COMPLETED_CORRECTLY_NAME = "Quiz completed successfully";
    public static String QUIZ_ELEMENT_ADDED_NAME = "Elements added";
    public static String QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME = "Elements added (Ads)";
    public static String QUIZ_POINT_ADDED_VIDEO_NAME = "Points added (Ads)";

    // Lists for quiz responses
    public static ArrayList<String> phrasesCorrectAnswers = new ArrayList<>();
    public static ArrayList<String> phrasesIncorrectAnswers = new ArrayList<>();

    // Lists and mappings for database tables and UI elements
    public static ArrayList<String> tableListNames = new ArrayList<>();
    public static ArrayList<String> headerTablePdfList = new ArrayList<>();
    public static ArrayList<ItemButtonsRecycler> itemRecyclerQuizNavList = new ArrayList<>();
    public static HashMap<String,String> tableHashNames = new HashMap<>();

    public static boolean switchSimpleToVideoAds = true;
    public static int maxQuestionsPerQuiz = 10;

    // Maximum allowed numbers for different categories
    public static int allowedVerbsNumber = 100;
    public static int allowedPhrasalsNumber = 50;
    public static int allowedSentencesNumber = 50;
    public static int allowedNounsNumber = 50;
    public static int allowedAdjsNumber = 50;
    public static int allowedAdvsNumber = 50;
    public static int allowedIdiomsNumber = 50;

    // Total numbers for different categories
    public static int totalVerbsNumber;
    public static int totalPhrasalsNumber;
    public static int totalSentencesNumber;
    public static int totalNounsNumber;
    public static int totalAdjsNumber;
    public static int totalAdvsNumber;
    public static int totalIdiomsNumber;

    // User profile information
    public static Uri uriProfile;
    public static String userName = "User 1";
    public static boolean isFirstTimeActivity = false;

    /**
     * Clears and fills the phrasesCorrectAnswers and phrasesIncorrectAnswers lists with default responses.
     */
    public static void FillListsCorrectIncorrectAnswerResponses(){
        phrasesCorrectAnswers.clear();
        phrasesCorrectAnswers.add("Fantastic! You've got it right!");
        phrasesCorrectAnswers.add("Correctamundo! You're on fire!");
        phrasesCorrectAnswers.add("Bingo! Nailed it!");
        phrasesCorrectAnswers.add("Well done! You're a quiz whiz!");
        phrasesCorrectAnswers.add("You're absolutely correct! Keep it up!");
        phrasesCorrectAnswers.add("You're on a roll! That's the right answer!");
        phrasesCorrectAnswers.add("Gold star! You've chosen the correct option!");
        phrasesCorrectAnswers.add("Bravo! You're proving your expertise!");
        phrasesCorrectAnswers.add("Excellent choice! You're acing this quiz!");
        phrasesCorrectAnswers.add("That's the ticket! You're cruising through these questions!");

        phrasesIncorrectAnswers.clear();
        phrasesIncorrectAnswers.add("Oops! Not quite, but don't give up!");
        phrasesIncorrectAnswers.add("Almost there, but not quite. Try again!");
        phrasesIncorrectAnswers.add("Oh no! That's not the right answer.");
        phrasesIncorrectAnswers.add("Close, but not exactly. Give it another shot!");
        phrasesIncorrectAnswers.add("Not quite this time. Keep trying!");
        phrasesIncorrectAnswers.add("Darn! That's not the correct answer.");
        phrasesIncorrectAnswers.add("Oopsie daisy! Keep guessing, you'll get it!");
        phrasesIncorrectAnswers.add("Don't worry, you'll get it next time! Try again!");
        phrasesIncorrectAnswers.add("That's not it, but you're learning with every try!");
        phrasesIncorrectAnswers.add("Keep going! Mistakes are part of the learning process.");
    }

    /**
     * Clears and fills the tableHashNames mapping with category names and their corresponding database table names.
     */
    public static void FillHashMapTableName() {
        tableHashNames.clear();
        tableHashNames.put(Constants.VERB_NAME, MyDatabase.TABLE_VERBS);
        tableHashNames.put(Constants.SENTENCE_NAME, MyDatabase.TABLE_SENTENCES);
        tableHashNames.put(Constants.PHRASAL_NAME, MyDatabase.TABLE_PHRASALS);
        tableHashNames.put(Constants.NOUN_NAME, MyDatabase.TABLE_NOUNS);
        tableHashNames.put(Constants.ADJ_NAME, MyDatabase.TABLE_ADJECTIVES);
        tableHashNames.put(Constants.ADV_NAME, MyDatabase.TABLE_ADVERBS);
        tableHashNames.put(Constants.IDIOM_NAME, MyDatabase.TABLE_IDIOMS);
    }

    /**
     * Clears and fills the tableListNames list with category names used in the application.
     */
    public static void FillListCategoriesNames() {
        tableListNames.clear();
        tableListNames.add(Constants.ALL_NAME);
        tableListNames.add(Constants.VERB_NAME);
        tableListNames.add(Constants.SENTENCE_NAME);
        tableListNames.add(Constants.PHRASAL_NAME);
        tableListNames.add(Constants.NOUN_NAME);
        tableListNames.add(Constants.ADJ_NAME);
        tableListNames.add(Constants.ADV_NAME);
        tableListNames.add(Constants.IDIOM_NAME);
    }

    /**
     * Clears and fills the headerTablePdfList list with column header names for PDF tables.
     */
    public static void FillListHeaderTablePdf() {
        headerTablePdfList.clear();
        headerTablePdfList.add("ID");
        headerTablePdfList.add("ENGLISH");
        headerTablePdfList.add("Native language");
    }

    /**
     * Clears and fills the itemRecyclerQuizNavList list with navigation items for quizzes.
     * Uses predefined quiz categories and their requirements.
     */
    public static void FillItemRecyclerQuizNavList() {
        itemRecyclerQuizNavList.clear();
        itemRecyclerQuizNavList.add(new ItemButtonsRecycler(Constants.categoryNameArray[0], "opened"));
        itemRecyclerQuizNavList.add(new ItemButtonsRecycler(Constants.categoryNameArray[1], "opened"));
        itemRecyclerQuizNavList.add(new ItemButtonsRecycler(Constants.categoryNameArray[2], "Phrasal verbs required 80 points"));
        itemRecyclerQuizNavList.add(new ItemButtonsRecycler(Constants.categoryNameArray[3], "Nouns required 150 points"));
        itemRecyclerQuizNavList.add(new ItemButtonsRecycler(Constants.categoryNameArray[4], "Adjectives required 250 points"));
        itemRecyclerQuizNavList.add(new ItemButtonsRecycler(Constants.categoryNameArray[5], "Adverbs required 400 points"));
        itemRecyclerQuizNavList.add(new ItemButtonsRecycler(Constants.categoryNameArray[6], "Idioms required 600 points"));
    }
}
