package anouar.oulhaj.p001._Main;

public class Constants {

    // allowed open
    public static final int permissionPhrasalScore = 100;
    public static final int permissionNounScore = 200;
    public static final int permissionAdjScore = 400;
    public static final int permissionAdvScore = 700;
    public static final int permissionIdiomScore = 1000;

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
    public static final String ARG_STR_URI_PROFILE_IMG = "uri_profile";
    public static final String TAG_SCORES_TOTAL_SCORE = "global_main_score";
    public static final String SHARED_PREFS_FILE_NAME = "privateFile.xml";
    public static final String ARG_IS_FIRST_TIME_ACTIVITY = "arg_is_first_time_activity";


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


   public static final String KEY_USER_NAME = "user_name";


    public static final String TAG_VERB_ADDED = "tag_verb_added";
    public static final String TAG_SENTENCE_ADDED = "tag_sentence_added";
    public static final String TAG_PHRASAL_ADDED = "tag_phrasal_added";
    public static final String TAG_NOUN_ADDED = "tag_noun_added";
    public static final String TAG_ADJ_ADDED = "tag_adj_added";
    public static final String TAG_ADV_ADDED = "tag_adv_added";
    public static final String TAG_IDIOM_ADDED = "tag_idiom_added";

    public static final String TAG_VERB_ADDED_VIDEO = "tag_verb_added_video";
    public static final String TAG_SENTENCE_ADDED_VIDEO  = "tag_sentence_added_video";
    public static final String TAG_PHRASAL_ADDED_VIDEO = "tag_phrasal_added_video";
    public static final String TAG_NOUN_ADDED_VIDEO  = "tag_noun_added_video";
    public static final String TAG_ADJ_ADDED_VIDEO  = "tag_adj_added_video";
    public static final String TAG_ADV_ADDED_VIDEO  = "tag_adv_added_video";
    public static final String TAG_IDIOM_ADDED_VIDEO  = "tag_idiom_added_video";

    public static final String TAG_VERB_POINT_ADDED_VIDEO = "tag_verb_point_added_video";
    public static final String TAG_SENTENCE_POINT_ADDED_VIDEO  = "tag_sentence_point_added_video";
    public static final String TAG_PHRASAL_POINT_ADDED_VIDEO = "tag_phrasal_point_added_video";
    public static final String TAG_NOUN_POINT_ADDED_VIDEO  = "tag_noun_point_added_video";
    public static final String TAG_ADJ_POINT_ADDED_VIDEO  = "tag_adj_point_added_video";
    public static final String TAG_ADV_POINT_ADDED_VIDEO  = "tag_adv_point_added_video";
    public static final String TAG_IDIOM_POINT_ADDED_VIDEO  = "tag_idiom_point_added_video";

    public static final String TAG_VERB_QUIZ_COMPLETED = "tag_verb_quiz_completed";
    public static final String TAG_SENTENCE_QUIZ_COMPLETED = "tag_sentence_quiz_completed";
    public static final String TAG_PHRASAL_QUIZ_COMPLETED = "tag_phrasal_quiz_completed";
    public static final String TAG_NOUN_QUIZ_COMPLETED = "tag_noun_quiz_completed";
    public static final String TAG_ADJ_QUIZ_COMPLETED = "tag_adj_quiz_completed";
    public static final String TAG_ADV_QUIZ_COMPLETED = "tag_adv_quiz_completed";
    public static final String TAG_IDIOM_QUIZ_COMPLETED = "tag_idiom_quiz_completed";

    public static final String TAG_VERB_QUIZ_COMPLETED_CORRECTLY = "tag_verb_quiz_completed_correctly";
    public static final String TAG_SENTENCE_QUIZ_COMPLETED_CORRECTLY = "tag_sentence_quiz_completed_correctly";
    public static final String TAG_PHRASAL_QUIZ_COMPLETED_CORRECTLY = "tag_phrasal_quiz_completed_correctly";
    public static final String TAG_NOUN_QUIZ_COMPLETED_CORRECTLY = "tag_noun_quiz_completed_correctly";
    public static final String TAG_ADJ_QUIZ_COMPLETED_CORRECTLY = "tag_adj_quiz_completed_correctly";
    public static final String TAG_ADV_QUIZ_COMPLETED_CORRECTLY = "tag_adv_quiz_completed_correctly";
    public static final String TAG_IDIOM_QUIZ_COMPLETED_CORRECTLY = "tag_idiom_quiz_completed_correctly";

}
