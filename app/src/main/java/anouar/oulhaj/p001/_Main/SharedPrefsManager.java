package anouar.oulhaj.p001._Main;

import android.content.SharedPreferences;
import android.net.Uri;

public class SharedPrefsManager {

    private SharedPreferences sharedPreferences;
    private final MainActivity activity;

    public SharedPrefsManager( MainActivity activity, SharedPreferences sharedPreferences) {
        this.activity = activity;
        this.sharedPreferences = sharedPreferences;
    }
    public void getSharedPreferencesData() {

        Utils.uriProfile = Uri.parse(sharedPreferences.getString(Constants.ARG_STR_URI_PROFILE_IMG, ""));

        // The Categories main scores displayed in HomeNavFragment
        activity.verbMainScore = sharedPreferences.getInt(Constants.TAG_PREF_VERB_SCORE,0);
        activity.sentenceMainScore = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_SCORE,0);
        activity.phrasalMainScore = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_SCORE,0);
        activity.nounMainScore = sharedPreferences.getInt(Constants.TAG_PREF_NOUN_SCORE,0);
        activity.adjMainScore =sharedPreferences.getInt(Constants.TAG_PREF_ADJ_SCORE,0);
        activity.advMainScore =sharedPreferences.getInt(Constants.TAG_PREF_ADV_SCORE,0);
        activity.idiomMainScore = sharedPreferences.getInt(Constants.TAG_PREF_IDIOM_SCORE,0);

        // The allowed max of each category
        Utils.allowedVerbsNumber = sharedPreferences.getInt(Constants.ARG_ALLOWED_VERBS_NUMBER,100);
        Utils.allowedSentencesNumber = sharedPreferences.getInt(Constants.ARG_ALLOWED_SENTENCES_NUMBER,50);
        Utils.allowedPhrasalsNumber = sharedPreferences.getInt(Constants.ARG_ALLOWED_PHRASALS_NUMBER,50);
        Utils.allowedNounsNumber = sharedPreferences.getInt(Constants.ARG_ALLOWED_NOUNS_NUMBER,50);
        Utils.allowedAdjsNumber = sharedPreferences.getInt(Constants.ARG_ALLOWED_ADJS_NUMBER,50);
        Utils.allowedAdvsNumber = sharedPreferences.getInt(Constants.ARG_ALLOWED_ADVS_NUMBER,50);
        Utils.allowedIdiomsNumber= sharedPreferences.getInt(Constants.ARG_ALLOWED_IDIOMS_NUMBER,50);


        Utils.nativeLanguage = sharedPreferences.getString(Constants.TAG_NATIVE_LANGUAGE,Constants.LANGUAGE_NATIVE_FRENCH);
        Utils.isThemeNight = sharedPreferences.getBoolean(Constants.ARG_IS_THEME_DARK_MODE,false);
    }
    public void putSharedPreferencesScores(SharedPreferences.Editor editor) {

        // put categories main scores in the sharedPref
        editor.putInt(Constants.TAG_PREF_VERB_SCORE,  activity.verbMainScore);
        editor.putInt(Constants.TAG_PREF_SENTENCE_SCORE,  activity.sentenceMainScore);
        editor.putInt(Constants.TAG_PREF_PHRASAL_SCORE,  activity.phrasalMainScore);
        editor.putInt(Constants.TAG_PREF_NOUN_SCORE,  activity.nounMainScore);
        editor.putInt(Constants.TAG_PREF_ADJ_SCORE,  activity.adjMainScore);
        editor.putInt(Constants.TAG_PREF_ADV_SCORE,  activity.advMainScore);
        editor.putInt(Constants.TAG_PREF_IDIOM_SCORE,  activity.idiomMainScore);

        // put allowed max numbers in sharedPref
        editor.putInt(Constants.ARG_ALLOWED_VERBS_NUMBER,Utils.allowedVerbsNumber);
        editor.putInt(Constants.ARG_ALLOWED_SENTENCES_NUMBER,Utils.allowedSentencesNumber);
        editor.putInt(Constants.ARG_ALLOWED_PHRASALS_NUMBER,Utils.allowedPhrasalsNumber);
        editor.putInt(Constants.ARG_ALLOWED_NOUNS_NUMBER,Utils.allowedNounsNumber);
        editor.putInt(Constants.ARG_ALLOWED_ADJS_NUMBER,Utils.allowedAdjsNumber);
        editor.putInt(Constants.ARG_ALLOWED_ADVS_NUMBER,Utils.allowedAdvsNumber);
        editor.putInt(Constants.ARG_ALLOWED_IDIOMS_NUMBER,Utils.allowedIdiomsNumber);

        editor.putString(Constants.TAG_NATIVE_LANGUAGE,Utils.nativeLanguage); // set preferences of current Native Language
        editor.putBoolean(Constants.ARG_IS_THEME_DARK_MODE , Utils.isThemeNight); //set the Theme Mode

        editor.putString(Constants.ARG_STR_URI_PROFILE_IMG,Utils.uriProfile.toString());

        editor.apply();
    }
}
