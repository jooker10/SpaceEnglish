package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.content.SharedPreferences;
import android.net.Uri;

import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

public class SharedPrefsManager {

    private final SharedPreferences sharedPreferences;

    public SharedPrefsManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
    public void getSharedPreferencesData() {

        Utils.uriProfile = Uri.parse(sharedPreferences.getString(Constants.TAG_PREF_STR_URI_PROFILE_IMG, ""));
        Scores.totalScore = sharedPreferences.getInt(Constants.TAG_PREF_SCORES_TOTAL_SCORE, 0);

        Scores.verbScore = sharedPreferences.getInt(Constants.TAG_PREF_VERB_SCORE,0);
        Scores.sentenceScore = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_SCORE,0);
        Scores.phrasalScore = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_SCORE,0);
        Scores.nounScore = sharedPreferences.getInt(Constants.TAG_PREF_NOUN_SCORE,0);
        Scores.adjScore =sharedPreferences.getInt(Constants.TAG_PREF_ADJ_SCORE,0);
        Scores.advScore =sharedPreferences.getInt(Constants.TAG_PREF_ADV_SCORE,0);
        Scores.idiomScore = sharedPreferences.getInt(Constants.TAG_PREF_IDIOM_SCORE,0);

        // elements added

        /*for(int i = 0 ; i<Scores.categoryListAdded.length;i++) {
            Scores.categoryListAdded[i] = sharedPreferences.getInt(Constants.tagListCategoryAdded[i],0);
        }*/

        Scores.verbAdded = sharedPreferences.getInt(Constants.TAG_PREF_VERB_ADDED, 0);
        Scores.sentenceAdded = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_ADDED, 0);
        Scores.phrasalAdded = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_ADDED, 0);
        Scores.nounAdded = sharedPreferences.getInt(Constants.TAG_PREF_NOUN_ADDED, 0);
        Scores.adjAdded = sharedPreferences.getInt(Constants.TAG_PREF_ADJ_ADDED, 0);
        Scores.advAdded = sharedPreferences.getInt(Constants.TAG_PREF_ADV_ADDED, 0);
        Scores.idiomAdded = sharedPreferences.getInt(Constants.TAG_PREF_IDIOM_ADDED, 0);
        // elements added video

       /* for(int i = 0 ; i < Scores.categoryListAdded.length ; i++) {
            Scores.categoryListAdded[i] = sharedPreferences.getInt(Constants.tagListCategoryAdded[i],0);
        }*/
        Scores.verbAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_VERB_ADDED_VIDEO, 0);
        Scores.sentenceAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_ADDED_VIDEO, 0);
        Scores.phrasalAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_ADDED_VIDEO, 0);
        Scores.nounAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_NOUN_ADDED_VIDEO, 0);
        Scores.adjAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_ADJ_ADDED_VIDEO, 0);
        Scores.advAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_ADV_ADDED_VIDEO, 0);
        Scores.idiomAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_IDIOM_ADDED_VIDEO, 0);
       /* for(int i = 0 ; i < Scores.categoryListAddedVideo.length ; i++) {
            Scores.categoryListAddedVideo[i] = sharedPreferences.getInt(Constants.tagListCategoryAddedVideo[i],0);
        }*/

        // Points added video
        Scores.verbPointsAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_VERB_POINT_ADDED_VIDEO, 0);
        Scores.sentencePointsAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_POINT_ADDED_VIDEO, 0);
        Scores.phrasalPointsAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_POINT_ADDED_VIDEO, 0);
        Scores.nounPointsAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_NOUN_POINT_ADDED_VIDEO, 0);
        Scores.adjPointsAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_ADJ_POINT_ADDED_VIDEO, 0);
        Scores.advPointsAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_ADV_POINT_ADDED_VIDEO, 0);
        Scores.idiomPointsAddedVideo = sharedPreferences.getInt(Constants.TAG_PREF_IDIOM_POINT_ADDED_VIDEO, 0);
       /* for(int i = 0 ; i < Scores.categoryListPointsAddedVideo.length ; i++) {
            Scores.categoryListPointsAddedVideo[i] = sharedPreferences.getInt(Constants.tagListCategoryPointAddedVideo[i],0);
        }*/

        // quiz number completed
        Scores.verbQuizCompleted = sharedPreferences.getInt(Constants.TAG_PREF_VERB_QUIZ_COMPLETED,0);
        Scores.sentenceQuizCompleted = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_QUIZ_COMPLETED,0);
        Scores.phrasalQuizCompleted = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_QUIZ_COMPLETED,0);
        Scores.nounQuizCompleted = sharedPreferences.getInt(Constants.TAG_PREF_NOUN_QUIZ_COMPLETED,0);
        Scores.adjQuizCompleted = sharedPreferences.getInt(Constants.TAG_PREF_ADJ_QUIZ_COMPLETED,0);
        Scores.advQuizCompleted = sharedPreferences.getInt(Constants.TAG_PREF_ADV_QUIZ_COMPLETED,0);
        Scores.idiomQuizCompleted = sharedPreferences.getInt(Constants.TAG_PREF_IDIOM_QUIZ_COMPLETED,0);
       /* for(int i = 0 ; i < Scores.categoryListQuizCompleted.length ; i++) {
            Scores.categoryListQuizCompleted[i] = sharedPreferences.getInt(Constants.tagCategoryQuizCompleted[i],0);
        }*/
        // quiz number completed correctly
        Scores.verbQuizCompletedCorrectly = sharedPreferences.getInt(Constants.TAG_PREF_VERB_QUIZ_COMPLETED_CORRECTLY,0);
        Scores.sentenceQuizCompletedCorrectly = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_QUIZ_COMPLETED_CORRECTLY,0);
        Scores.phrasalQuizCompletedCorrectly = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_QUIZ_COMPLETED_CORRECTLY,0);
        Scores.nounQuizCompletedCorrectly = sharedPreferences.getInt(Constants.TAG_PREF_NOUN_QUIZ_COMPLETED_CORRECTLY,0);
        Scores.adjQuizCompletedCorrectly = sharedPreferences.getInt(Constants.TAG_PREF_ADJ_QUIZ_COMPLETED_CORRECTLY,0);
        Scores.advQuizCompletedCorrectly = sharedPreferences.getInt(Constants.TAG_PREF_ADV_QUIZ_COMPLETED_CORRECTLY,0);
        Scores.idiomQuizCompletedCorrectly = sharedPreferences.getInt(Constants.TAG_PREF_IDIOM_QUIZ_COMPLETED_CORRECTLY,0);

       /* for(int i = 0 ; i < Scores.categoryListQuizCompletedCorrectly.length ; i++) {
            Scores.categoryListQuizCompletedCorrectly[i] = sharedPreferences.getInt(Constants.tagCategoryQuizCompletedCorrectly[i],0);
        }*/

        // The allowed max of each category
        Utils.allowedVerbsNumber = sharedPreferences.getInt(Constants.TAG_PREF_ALLOWED_VERBS_NUMBER,100);
        Utils.allowedSentencesNumber = sharedPreferences.getInt(Constants.TAG_PREF_ALLOWED_SENTENCES_NUMBER,50);
        Utils.allowedPhrasalsNumber = sharedPreferences.getInt(Constants.TAG_PREF_ALLOWED_PHRASALS_NUMBER,50);
        Utils.allowedNounsNumber = sharedPreferences.getInt(Constants.TAG_PREF_ALLOWED_NOUNS_NUMBER,50);
        Utils.allowedAdjsNumber = sharedPreferences.getInt(Constants.TAG_PREF_ALLOWED_ADJS_NUMBER,50);
        Utils.allowedAdvsNumber = sharedPreferences.getInt(Constants.TAG_PREF_ALLOWED_ADVS_NUMBER,50);
        Utils.allowedIdiomsNumber= sharedPreferences.getInt(Constants.TAG_PREF_ALLOWED_IDIOMS_NUMBER,50);




        Utils.nativeLanguage = sharedPreferences.getString(Constants.TAG_PREF_NATIVE_LANGUAGE,Constants.LANGUAGE_NATIVE_FRENCH);
    }

    public void getSharedPrefTheme() {
        Utils.isThemeNight = sharedPreferences.getBoolean(Constants.TAG_PREF_IS_THEME_DARK_MODE,false);
        Utils.isFirstTimeActivity = sharedPreferences.getBoolean(Constants.TAG_PREF_IS_FIRST_TIME_ACTIVITY,false);
    }
    public void putSharedPrefTheme(SharedPreferences.Editor editor){
        editor.putBoolean(Constants.TAG_PREF_IS_THEME_DARK_MODE, Utils.isThemeNight); //set the Theme Mode
        editor.putBoolean(Constants.TAG_PREF_IS_FIRST_TIME_ACTIVITY,Utils.isFirstTimeActivity);
        editor.apply();
    }
    public void putSharedPreferencesScores(SharedPreferences.Editor editor) {

        // put categories main scores in the sharedPref
        editor.putInt(Constants.TAG_PREF_VERB_SCORE,  Scores.verbScore);
        editor.putInt(Constants.TAG_PREF_SENTENCE_SCORE,  Scores.sentenceScore);
        editor.putInt(Constants.TAG_PREF_PHRASAL_SCORE,  Scores.phrasalScore);
        editor.putInt(Constants.TAG_PREF_NOUN_SCORE,  Scores.nounScore);
        editor.putInt(Constants.TAG_PREF_ADJ_SCORE,  Scores.adjScore);
        editor.putInt(Constants.TAG_PREF_ADV_SCORE,  Scores.advScore);
        editor.putInt(Constants.TAG_PREF_IDIOM_SCORE,  Scores.idiomScore);

        // elements added
        editor.putInt(Constants.TAG_PREF_VERB_ADDED,Scores.verbAdded);
        editor.putInt(Constants.TAG_PREF_SENTENCE_ADDED,Scores.sentenceAdded);
        editor.putInt(Constants.TAG_PREF_PHRASAL_ADDED,Scores.phrasalAdded);
        editor.putInt(Constants.TAG_PREF_NOUN_ADDED,Scores.nounAdded);
        editor.putInt(Constants.TAG_PREF_ADJ_ADDED,Scores.adjAdded);
        editor.putInt(Constants.TAG_PREF_ADV_ADDED,Scores.advAdded);
        editor.putInt(Constants.TAG_PREF_IDIOM_ADDED,Scores.idiomAdded);

        // elements added video
        editor.putInt(Constants.TAG_PREF_VERB_ADDED_VIDEO,Scores.verbAddedVideo);
        editor.putInt(Constants.TAG_PREF_SENTENCE_ADDED_VIDEO,Scores.sentenceAddedVideo);
        editor.putInt(Constants.TAG_PREF_PHRASAL_ADDED_VIDEO,Scores.phrasalAddedVideo);
        editor.putInt(Constants.TAG_PREF_NOUN_ADDED_VIDEO,Scores.nounAddedVideo);
        editor.putInt(Constants.TAG_PREF_ADJ_ADDED_VIDEO,Scores.adjAddedVideo);
        editor.putInt(Constants.TAG_PREF_ADV_ADDED_VIDEO,Scores.advAddedVideo);
        editor.putInt(Constants.TAG_PREF_IDIOM_ADDED_VIDEO,Scores.idiomAddedVideo);

        // number of quiz completed
        editor.putInt(Constants.TAG_PREF_VERB_QUIZ_COMPLETED, Scores.verbQuizCompleted);
        editor.putInt(Constants.TAG_PREF_SENTENCE_QUIZ_COMPLETED, Scores.sentenceQuizCompleted);
        editor.putInt(Constants.TAG_PREF_PHRASAL_QUIZ_COMPLETED, Scores.phrasalQuizCompleted);
        editor.putInt(Constants.TAG_PREF_NOUN_QUIZ_COMPLETED, Scores.nounQuizCompleted);
        editor.putInt(Constants.TAG_PREF_ADJ_QUIZ_COMPLETED, Scores.adjQuizCompleted);
        editor.putInt(Constants.TAG_PREF_ADV_QUIZ_COMPLETED, Scores.advQuizCompleted);
        editor.putInt(Constants.TAG_PREF_IDIOM_QUIZ_COMPLETED, Scores.idiomQuizCompleted);

        // number of quiz completed correctly
        editor.putInt(Constants.TAG_PREF_VERB_QUIZ_COMPLETED_CORRECTLY, Scores.verbQuizCompletedCorrectly);
        editor.putInt(Constants.TAG_PREF_SENTENCE_QUIZ_COMPLETED_CORRECTLY, Scores.sentenceQuizCompletedCorrectly);
        editor.putInt(Constants.TAG_PREF_PHRASAL_QUIZ_COMPLETED_CORRECTLY, Scores.phrasalQuizCompletedCorrectly);
        editor.putInt(Constants.TAG_PREF_NOUN_QUIZ_COMPLETED_CORRECTLY, Scores.nounQuizCompletedCorrectly);
        editor.putInt(Constants.TAG_PREF_ADJ_QUIZ_COMPLETED_CORRECTLY, Scores.adjQuizCompletedCorrectly);
        editor.putInt(Constants.TAG_PREF_ADV_QUIZ_COMPLETED_CORRECTLY, Scores.advQuizCompletedCorrectly);
        editor.putInt(Constants.TAG_PREF_IDIOM_QUIZ_COMPLETED_CORRECTLY, Scores.idiomQuizCompletedCorrectly);

        // points added video
        editor.putInt(Constants.TAG_PREF_VERB_POINT_ADDED_VIDEO,Scores.verbPointsAddedVideo);
        editor.putInt(Constants.TAG_PREF_SENTENCE_POINT_ADDED_VIDEO,Scores.sentencePointsAddedVideo);
        editor.putInt(Constants.TAG_PREF_PHRASAL_POINT_ADDED_VIDEO,Scores.phrasalPointsAddedVideo);
        editor.putInt(Constants.TAG_PREF_NOUN_POINT_ADDED_VIDEO,Scores.nounPointsAddedVideo);
        editor.putInt(Constants.TAG_PREF_ADJ_POINT_ADDED_VIDEO,Scores.adjPointsAddedVideo);
        editor.putInt(Constants.TAG_PREF_ADV_POINT_ADDED_VIDEO,Scores.advPointsAddedVideo);
        editor.putInt(Constants.TAG_PREF_IDIOM_POINT_ADDED_VIDEO,Scores.idiomPointsAddedVideo);

        // put allowed max numbers in sharedPref
        editor.putInt(Constants.TAG_PREF_ALLOWED_VERBS_NUMBER,Utils.allowedVerbsNumber);
        editor.putInt(Constants.TAG_PREF_ALLOWED_SENTENCES_NUMBER,Utils.allowedSentencesNumber);
        editor.putInt(Constants.TAG_PREF_ALLOWED_PHRASALS_NUMBER,Utils.allowedPhrasalsNumber);
        editor.putInt(Constants.TAG_PREF_ALLOWED_NOUNS_NUMBER,Utils.allowedNounsNumber);
        editor.putInt(Constants.TAG_PREF_ALLOWED_ADJS_NUMBER,Utils.allowedAdjsNumber);
        editor.putInt(Constants.TAG_PREF_ALLOWED_ADVS_NUMBER,Utils.allowedAdvsNumber);
        editor.putInt(Constants.TAG_PREF_ALLOWED_IDIOMS_NUMBER,Utils.allowedIdiomsNumber);

        editor.putString(Constants.TAG_PREF_NATIVE_LANGUAGE,Utils.nativeLanguage); // set preferences of current Native Language

        editor.putString(Constants.TAG_PREF_STR_URI_PROFILE_IMG,Utils.uriProfile.toString());
        editor.putInt(Constants.TAG_PREF_SCORES_TOTAL_SCORE,Scores.totalScore);

        editor.apply();
    }
}
