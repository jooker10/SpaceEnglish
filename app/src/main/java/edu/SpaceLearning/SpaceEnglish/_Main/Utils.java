package edu.SpaceLearning.SpaceEnglish._Main;

import android.net.Uri;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.DB.Category;

public class Utils {

    //------ the main qst in native language----------
    public static String nativeLanguage = "French";

    public static boolean isThemeNight = false;
    public static String fragmentNameTagSearch = "Table Fragment";

    public static String QUIZ_COMPLETED_NAME = "Quiz completed";
    public static String QUIZ_COMPLETED_CORRECTLY_NAME = "Quiz completed successfully";
    public static String QUIZ_ELEMENT_ADDED_NAME = "Elements added";
    public static String QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME = "Elements added (Ads)";
    public static String QUIZ_POINT_ADDED_VIDEO_NAME = "Points added (Ads)";



    public static ArrayList<String> phrasesCorrectAnswers = new ArrayList<>();
    public static ArrayList<String> phrasesIncorrectAnswers = new ArrayList<>();

    public static boolean switchSimpleToVideoAds = true;
    public static int maxQuestionsPerQuiz = 10;

    public static int allowedVerbsNumber = 100;
    public static int allowedPhrasalsNumber = 50;
    public static int allowedSentencesNumber = 50;
    public static int allowedNounsNumber = 50;
    public static int allowedAdjsNumber = 50;
    public static int allowedAdvsNumber = 50;
    public static int allowedIdiomsNumber = 50;

    public static int totalVerbsNumber;
    public static int totalPhrasalsNumber;
    public static int totalSentencesNumber;
    public static int totalNounsNumber;
    public static int totalAdjsNumber;
    public static int totalAdvsNumber;
    public static int totalIdiomsNumber;
    public static Uri uriProfile;
    public static String userName = "User 1";
    public static boolean isFirstTimeActivity = false;


    public static void FillListsCorrectIncorrectAnswerResponses(){
        Utils.phrasesCorrectAnswers.add("Fantastic! You've got it right!");
        Utils.phrasesCorrectAnswers.add("Correctamundo! You're on fire!");
        Utils.phrasesCorrectAnswers.add("Bingo! Nailed it!");
        Utils.phrasesCorrectAnswers.add("Well done! You're a quiz whiz!");
        Utils.phrasesCorrectAnswers.add("You're absolutely correct! Keep it up!");
        Utils.phrasesCorrectAnswers.add("You're on a roll! That's the right answer!");
        Utils.phrasesCorrectAnswers.add("Gold star! You've chosen the correct option!");
        Utils.phrasesCorrectAnswers.add("Bravo! You're proving your expertise!");
        Utils.phrasesCorrectAnswers.add("Excellent choice! You're acing this quiz!");
        Utils.phrasesCorrectAnswers.add("That's the ticket! You're cruising through these questions!");
        Utils.phrasesIncorrectAnswers.add("Oops! Not quite, but don't give up!");
        Utils.phrasesIncorrectAnswers.add("Almost there, but not quite. Try again!");
        Utils.phrasesIncorrectAnswers.add("Oh no! That's not the right answer.");
        Utils.phrasesIncorrectAnswers.add("Close, but not exactly. Give it another shot!");
        Utils.phrasesIncorrectAnswers.add("Not quite this time. Keep trying!");
        Utils.phrasesIncorrectAnswers.add("Darn! That's not the correct answer.");
        Utils.phrasesIncorrectAnswers.add("Oopsie daisy! Keep guessing, you'll get it!");
        Utils.phrasesIncorrectAnswers.add("Don't worry, you'll get it next time! Try again!");
        Utils.phrasesIncorrectAnswers.add("That's not it, but you're learning with every try!");
        Utils.phrasesIncorrectAnswers.add("Keep going! Mistakes are part of the learning process.");

    }
}
