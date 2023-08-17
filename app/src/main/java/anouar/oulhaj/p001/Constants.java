package anouar.oulhaj.p001;

import android.net.Uri;

public class Constants {

    // final Constants
    public static final int HOME_NAV_INDEX = 0;
    public static final int TABLE_NAV_INDEX = 1;
    public static final int QUIZ_NAV_INDEX = 3;
    public static final int SETTINGS_NAV_INDEX = 4;

    public static final String TAG_PREF_CHOOSING_LANG = "choosing_lang";
    public static final String TAG_NATIVE_LANGUAGE = "French";
    public static final String LANGUAGE_NATIVE_FRENCH = "French";
    public static final String LANGUAGE_NATIVE_SPANISH= "Spanish";
    public static final String LANGUAGE_NATIVE_ARABIC = "Arabic";
    public static final String TAG_CATEGORY_TYPE = "categoryType";

    public static final String TAG_PREF_VERB_SCORE = "verb_score";
    public static final String TAG_PREF_SENTENCE_SCORE = "sentence_score";
    public static final String TAG_PREF_PHRASAL_SCORE = "phrasal_score";
    public static final String TAG_PREF_NOUN_SCORE = "noun_score";
    public static final String TAG_PREF_ADJ_SCORE = "adj_score";
    public static final String TAG_PREF_ADV_SCORE = "adv_score";
    public static final String TAG_PREF_IDIOM_SCORE = "idiom_score";
    public static final String ARG_IS_THEME_DARK_MODE = "is_dark_mode";


    // no Final constants
    public static int pref_verb_score = 0;
    public static int pref_sentence_score = 0;
    public static int pref_phrasal_score = 0;
    public static int pref_noun_score = 0;
    public static int pref_adj_score = 0;
    public static int pref_adv_score = 0;
    public static int pref_idiom_score = 0;

    public static Uri uri_pref;

    public static final String KEY_SETTINGS_SWITCH_THEME = "switch_theme";
    public static final String KEY_SETTINGS_SWITCH_LANGUAGE = "switch_language";
    public static final String KEY_SETTINGS_BTN_PRIVACY = "btn_privacy";
    public static final String KEY_SETTINGS_BTN_CONTACTUS = "btn_contactus";

    //--------------------------------------Home Frag-----------------------------------
    public static final String ARG_CURRENT_VERB_SCORE = "arg_current_verb_score";
    public static final String ARG_CURRENT_SENTENCE_SCORE = "arg_current_sentence_score";
    public static final String ARG_CURRENT_PHRASAL_SCORE = "arg_current_phrasal_score";
    public static final String ARG_CURRENT_NOUN_SCORE = "arg_current_noun_score";
    public static final String ARG_CURRENT_ADJ_SCORE = "arg_current_adj_score";
    public static final String ARG_CURRENT_ADV_SCORE = "arg_current_adv_score";
    public static final String ARG_CURRENT_IDIOM_SCORE = "arg_current_idiom_score";

    public static final String ARG_ALLOWED_VERBS_NUMBER = "arg_allowed_verbs_number";
    public static final String ARG_ALLOWED_SENTENCES_NUMBER = "arg_allowed_sentences_number";
    public static final String ARG_ALLOWED_PHRASALS_NUMBER = "arg_allowed_phrasals_number";
    public static final String ARG_ALLOWED_NOUNS_NUMBER = "arg_allowed_nouns_number";
    public static final String ARG_ALLOWED_ADJS_NUMBER = "arg_allowed_adjs_number";
    public static final String ARG_ALLOWED_ADVS_NUMBER = "arg_allowed_advs_number";
    public static final String ARG_ALLOWED_IDIOMS_NUMBER = "arg_allowed_idioms_number";

    public static final String ARG_UPDATED_CATEGORY_ANIMATION = "updated_category_animation";

    //--------------------------------------TableNavFrag-----------------------------------
    public static final String VERB_NAME = "Verbs";
    public static final String SENTENCE_NAME = "Sentences_Phrases";
    public static final String PHRASAL_NAME = "Phrasal verbs";
    public static final String NOUN_NAME = "Nouns";
    public static final String ADJ_NAME = "Adjectives";
    public static final String ADV_NAME = "Adverbs";
    public static final String IDIOM_NAME = "Idioms";

    public static final int VERBS_INDEX = 0;
    public static final int SENTENCES_INDEX = 1;
    public static final int PHRASAL_INDEX = 2;
    public static final int NOUN_INDEX = 3;
    public static final int ADJ_INDEX = 4;
    public static final int ADV_INDEX = 5;
    public static final int IDIOM_INDEX = 6;

   public static final String ARG_USER_NAME = "user_name";


}
