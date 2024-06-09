package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

public class Constants {

    // allowed open
    public static final int permissionPhrasalScore = 80;
    public static final int permissionNounScore = 150;
    public static final int permissionAdjScore = 250;
    public static final int permissionAdvScore = 400;
    public static final int permissionIdiomScore = 600;

    public static final int[] permissionCategoryScoreArray = {permissionPhrasalScore,permissionNounScore,permissionAdjScore,permissionAdvScore,permissionIdiomScore};

    // final Constants
    public static final int HOME_NAV_INDEX = 0;
    public static final int TABLE_NAV_INDEX = 1;
    public static final int QUIZ_NAV_INDEX = 3;
    public static final int SETTINGS_NAV_INDEX = 4;

    public static final String TAG_HOME_NAV_FRAGMENT = "home_nav_fragment";
    public static final String TAG_TABLES_NAV_FRAGMENT = "tables_nav_fragment";
    public static final String TAG_QUIZ_NAV_FRAGMENT = "quiz_nav_fragment";
    public static final String TAG_SETTINGS_NAV_FRAGMENT = "settings_nav_fragment";
    public static final String TAG_TABLES_CATEGORY_FRAGMENT = "tables_category_fragment";
    public static final String TAG_QUIZ_CATEGORY_FRAGMENT = "quiz_category_fragment";
    public static final String TAG_SHEET_MAIN_FRAGMENT = "sheet_main_fragment";
    public static final String NO_CONNECTION_FRAGMENT = "no_connection_fragment";

    public static final String TAG_PREF_CHOOSING_LANG = "choosing_lang";
    public static final String TAG_PREF_NATIVE_LANGUAGE = "French";
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
    public static final String TAG_PREF_IS_THEME_DARK_MODE = "is_dark_mode";
    public static final String TAG_PREF_STR_URI_PROFILE_IMG = "uri_profile";
    public static final String TAG_PREF_SCORES_TOTAL_SCORE = "global_main_score";
    public static final String SHARED_PREFS_FILE_NAME = "privateFile.xml";
    public static final String TAG_PREF_IS_FIRST_TIME_ACTIVITY = "arg_is_first_time_activity";


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

    public static final String TAG_PREF_ALLOWED_VERBS_NUMBER = "arg_allowed_verbs_number";
    public static final String TAG_PREF_ALLOWED_SENTENCES_NUMBER = "arg_allowed_sentences_number";
    public static final String TAG_PREF_ALLOWED_PHRASALS_NUMBER = "arg_allowed_phrasals_number";
    public static final String TAG_PREF_ALLOWED_NOUNS_NUMBER = "arg_allowed_nouns_number";
    public static final String TAG_PREF_ALLOWED_ADJS_NUMBER = "arg_allowed_adjs_number";
    public static final String TAG_PREF_ALLOWED_ADVS_NUMBER = "arg_allowed_advs_number";
    public static final String TAG_PREF_ALLOWED_IDIOMS_NUMBER = "arg_allowed_idioms_number";

    public static final String ARG_UPDATED_CATEGORY_ANIMATION = "updated_category_animation";

    //--------------------------------------TableNavFrag-----------------------------------
    public static final String ALL_NAME = "Total";
    public static final String VERB_NAME = "Verbs";
    public static final String SENTENCE_NAME = "Sentences";
    public static final String PHRASAL_NAME = "Phrasal verbs";
    public static final String NOUN_NAME = "Nouns";
    public static final String ADJ_NAME = "Adjectives";
    public static final String ADV_NAME = "Adverbs";
    public static final String IDIOM_NAME = "Idioms";

    public static final String[] categoryNameArray = {VERB_NAME,SENTENCE_NAME,PHRASAL_NAME,NOUN_NAME,ADJ_NAME,ADV_NAME,IDIOM_NAME};


   public static final String KEY_PREF_USER_NAME = "user_name";


    public static final String TAG_PREF_VERB_ADDED = "tag_verb_added";
    public static final String TAG_PREF_SENTENCE_ADDED = "tag_sentence_added";
    public static final String TAG_PREF_PHRASAL_ADDED = "tag_phrasal_added";
    public static final String TAG_PREF_NOUN_ADDED = "tag_noun_added";
    public static final String TAG_PREF_ADJ_ADDED = "tag_adj_added";
    public static final String TAG_PREF_ADV_ADDED = "tag_adv_added";
    public static final String TAG_PREF_IDIOM_ADDED = "tag_idiom_added";

    public static final String[] tagListCategoryAdded = {TAG_PREF_VERB_ADDED, TAG_PREF_SENTENCE_ADDED, TAG_PREF_PHRASAL_ADDED, TAG_PREF_NOUN_ADDED, TAG_PREF_ADJ_ADDED, TAG_PREF_ADV_ADDED, TAG_PREF_IDIOM_ADDED};

    public static final String TAG_PREF_VERB_ADDED_VIDEO = "tag_verb_added_video";
    public static final String TAG_PREF_SENTENCE_ADDED_VIDEO = "tag_sentence_added_video";
    public static final String TAG_PREF_PHRASAL_ADDED_VIDEO = "tag_phrasal_added_video";
    public static final String TAG_PREF_NOUN_ADDED_VIDEO = "tag_noun_added_video";
    public static final String TAG_PREF_ADJ_ADDED_VIDEO = "tag_adj_added_video";
    public static final String TAG_PREF_ADV_ADDED_VIDEO = "tag_adv_added_video";
    public static final String TAG_PREF_IDIOM_ADDED_VIDEO = "tag_idiom_added_video";

    public static final String[] tagListCategoryAddedVideo = {TAG_PREF_VERB_ADDED_VIDEO, TAG_PREF_SENTENCE_ADDED_VIDEO, TAG_PREF_PHRASAL_ADDED_VIDEO, TAG_PREF_NOUN_ADDED_VIDEO, TAG_PREF_ADJ_ADDED_VIDEO, TAG_PREF_ADV_ADDED_VIDEO, TAG_PREF_IDIOM_ADDED_VIDEO};


    public static final String TAG_PREF_VERB_POINT_ADDED_VIDEO = "tag_verb_point_added_video";
    public static final String TAG_PREF_SENTENCE_POINT_ADDED_VIDEO = "tag_sentence_point_added_video";
    public static final String TAG_PREF_PHRASAL_POINT_ADDED_VIDEO = "tag_phrasal_point_added_video";
    public static final String TAG_PREF_NOUN_POINT_ADDED_VIDEO = "tag_noun_point_added_video";
    public static final String TAG_PREF_ADJ_POINT_ADDED_VIDEO = "tag_adj_point_added_video";
    public static final String TAG_PREF_ADV_POINT_ADDED_VIDEO = "tag_adv_point_added_video";
    public static final String TAG_PREF_IDIOM_POINT_ADDED_VIDEO = "tag_idiom_point_added_video";

    public static final String[] tagListCategoryPointAddedVideo = {TAG_PREF_VERB_POINT_ADDED_VIDEO, TAG_PREF_SENTENCE_POINT_ADDED_VIDEO, TAG_PREF_PHRASAL_POINT_ADDED_VIDEO,
            TAG_PREF_NOUN_POINT_ADDED_VIDEO, TAG_PREF_ADJ_POINT_ADDED_VIDEO, TAG_PREF_ADV_POINT_ADDED_VIDEO, TAG_PREF_IDIOM_POINT_ADDED_VIDEO};

    public static final String TAG_PREF_VERB_QUIZ_COMPLETED = "tag_verb_quiz_completed";
    public static final String TAG_PREF_SENTENCE_QUIZ_COMPLETED = "tag_sentence_quiz_completed";
    public static final String TAG_PREF_PHRASAL_QUIZ_COMPLETED = "tag_phrasal_quiz_completed";
    public static final String TAG_PREF_NOUN_QUIZ_COMPLETED = "tag_noun_quiz_completed";
    public static final String TAG_PREF_ADJ_QUIZ_COMPLETED = "tag_adj_quiz_completed";
    public static final String TAG_PREF_ADV_QUIZ_COMPLETED = "tag_adv_quiz_completed";
    public static final String TAG_PREF_IDIOM_QUIZ_COMPLETED = "tag_idiom_quiz_completed";

    public static final String[] tagCategoryQuizCompleted = {TAG_PREF_VERB_QUIZ_COMPLETED, TAG_PREF_SENTENCE_QUIZ_COMPLETED, TAG_PREF_PHRASAL_QUIZ_COMPLETED
    , TAG_PREF_NOUN_QUIZ_COMPLETED, TAG_PREF_ADJ_QUIZ_COMPLETED, TAG_PREF_ADV_QUIZ_COMPLETED, TAG_PREF_IDIOM_QUIZ_COMPLETED};

    public static final String TAG_PREF_VERB_QUIZ_COMPLETED_CORRECTLY = "tag_verb_quiz_completed_correctly";
    public static final String TAG_PREF_SENTENCE_QUIZ_COMPLETED_CORRECTLY = "tag_sentence_quiz_completed_correctly";
    public static final String TAG_PREF_PHRASAL_QUIZ_COMPLETED_CORRECTLY = "tag_phrasal_quiz_completed_correctly";
    public static final String TAG_PREF_NOUN_QUIZ_COMPLETED_CORRECTLY = "tag_noun_quiz_completed_correctly";
    public static final String TAG_PREF_ADJ_QUIZ_COMPLETED_CORRECTLY = "tag_adj_quiz_completed_correctly";
    public static final String TAG_PREF_ADV_QUIZ_COMPLETED_CORRECTLY = "tag_adv_quiz_completed_correctly";
    public static final String TAG_PREF_IDIOM_QUIZ_COMPLETED_CORRECTLY = "tag_idiom_quiz_completed_correctly";

    public static final String[] tagCategoryQuizCompletedCorrectly = {TAG_PREF_VERB_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_SENTENCE_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_PHRASAL_QUIZ_COMPLETED_CORRECTLY
            , TAG_PREF_NOUN_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_ADJ_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_ADV_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_IDIOM_QUIZ_COMPLETED_CORRECTLY};

    //-----------------------------------------------GPT--------------------------------------------


    // Arrays for category lists
    public static final String[] mainScoreKeys = {TAG_PREF_VERB_SCORE, TAG_PREF_SENTENCE_SCORE, TAG_PREF_PHRASAL_SCORE,
            TAG_PREF_NOUN_SCORE, TAG_PREF_ADJ_SCORE, TAG_PREF_ADV_SCORE, TAG_PREF_IDIOM_SCORE};
    public static final String[] elementAddedKeys = {TAG_PREF_VERB_ADDED, TAG_PREF_SENTENCE_ADDED, TAG_PREF_PHRASAL_ADDED, TAG_PREF_NOUN_ADDED,
            TAG_PREF_ADJ_ADDED, TAG_PREF_ADV_ADDED, TAG_PREF_IDIOM_ADDED};
    public static final String[] elementAddedVideoKeys = {TAG_PREF_VERB_ADDED_VIDEO, TAG_PREF_SENTENCE_ADDED_VIDEO, TAG_PREF_PHRASAL_ADDED_VIDEO,
            TAG_PREF_NOUN_ADDED_VIDEO, TAG_PREF_ADJ_ADDED_VIDEO, TAG_PREF_ADV_ADDED_VIDEO, TAG_PREF_IDIOM_ADDED_VIDEO};
    public static final String[] elementQuizCompletedKeys = {TAG_PREF_VERB_QUIZ_COMPLETED, TAG_PREF_SENTENCE_QUIZ_COMPLETED,
            TAG_PREF_PHRASAL_QUIZ_COMPLETED, TAG_PREF_NOUN_QUIZ_COMPLETED, TAG_PREF_ADJ_QUIZ_COMPLETED, TAG_PREF_ADV_QUIZ_COMPLETED,
            TAG_PREF_IDIOM_QUIZ_COMPLETED};
    public static final String[] elementQuizCompletedCorrectlyKeys = {TAG_PREF_VERB_QUIZ_COMPLETED_CORRECTLY,
            TAG_PREF_SENTENCE_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_PHRASAL_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_NOUN_QUIZ_COMPLETED_CORRECTLY,
            TAG_PREF_ADJ_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_ADV_QUIZ_COMPLETED_CORRECTLY, TAG_PREF_IDIOM_QUIZ_COMPLETED_CORRECTLY};
    public static final String[] elementPointsAddedVideoKeys = {TAG_PREF_VERB_POINT_ADDED_VIDEO, TAG_PREF_SENTENCE_POINT_ADDED_VIDEO,
            TAG_PREF_PHRASAL_POINT_ADDED_VIDEO, TAG_PREF_NOUN_POINT_ADDED_VIDEO, TAG_PREF_ADJ_POINT_ADDED_VIDEO, TAG_PREF_ADV_POINT_ADDED_VIDEO,
            TAG_PREF_IDIOM_POINT_ADDED_VIDEO};
}
