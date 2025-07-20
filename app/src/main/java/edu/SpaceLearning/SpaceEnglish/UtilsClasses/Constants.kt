package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * Constants class that contains various constant values used throughout the application.
 */
object Constants {
    // Permission scores for different categories
    const val permissionPhrasalScore: Int = 80
    const val permissionNounScore: Int = 150
    const val permissionAdjScore: Int = 250
    const val permissionAdvScore: Int = 400
    const val permissionIdiomScore: Int = 600

    // Array of permission scores for categories
    @JvmField
    val permissionCategoryScoreArray: IntArray = intArrayOf(
        permissionPhrasalScore,
        permissionNounScore,
        permissionAdjScore,
        permissionAdvScore,
        permissionIdiomScore
    )

    // Navigation indices
    const val HOME_NAV_INDEX: Int = 0
    const val TABLE_NAV_INDEX: Int = 1
    const val QUIZ_NAV_INDEX: Int = 3
    const val SETTINGS_NAV_INDEX: Int = 4

    // Fragment tags
    const val TAG_HOME_NAV_FRAGMENT: String = "home_nav_fragment"
    const val TAG_TABLES_NAV_FRAGMENT: String = "tables_nav_fragment"
    const val TAG_QUIZ_NAV_FRAGMENT: String = "quiz_nav_fragment"
    const val TAG_SETTINGS_NAV_FRAGMENT: String = "settings_nav_fragment"
    const val TAG_TABLES_CATEGORY_FRAGMENT: String = "tables_category_fragment"
    const val TAG_QUIZ_CATEGORY_FRAGMENT: String = "quiz_category_fragment"
    const val TAG_SHEET_MAIN_FRAGMENT: String = "sheet_main_fragment"
    const val NO_CONNECTION_FRAGMENT: String = "no_connection_fragment"
    const val TAG_MAX_NUMBERS_OF_QUESTIONS = "MAX_NUMBERS_OF_QUESTIONS"

    // Preference tags
    const val TAG_PREF_CHOOSING_LANG: String = "choosing_lang"
    const val TAG_PREF_NATIVE_LANGUAGE: String = "French"
    const val LANGUAGE_NATIVE_FRENCH: String = "French"
    const val LANGUAGE_NATIVE_SPANISH: String = "Spanish"
    const val LANGUAGE_NATIVE_ARABIC: String = "Arabic"
    const val TAG_CATEGORY_TYPE: String = "categoryType"

    const val TAG_PREF_VERB_SCORE: String = "verb_score"
    const val TAG_PREF_SENTENCE_SCORE: String = "sentence_score"
    const val TAG_PREF_PHRASAL_SCORE: String = "phrasal_score"
    const val TAG_PREF_NOUN_SCORE: String = "noun_score"
    const val TAG_PREF_ADJ_SCORE: String = "adj_score"
    const val TAG_PREF_ADV_SCORE: String = "adv_score"
    const val TAG_PREF_IDIOM_SCORE: String = "idiom_score"
    const val TAG_PREF_IS_THEME_DARK_MODE: String = "is_dark_mode"
    const val TAG_PREF_STR_URI_PROFILE_IMG: String = "uri_profile"
    const val TAG_PREF_SCORES_TOTAL_SCORE: String = "global_main_score"
    const val SHARED_PREFS_FILE_NAME: String = "privateFile.xml"
    const val TAG_PREF_IS_FIRST_TIME_ACTIVITY: String = "arg_is_first_time_activity"

    // Settings keys
    const val KEY_SETTINGS_SWITCH_THEME: String = "switch_theme"
    const val KEY_SETTINGS_SWITCH_LANGUAGE: String = "switch_language"
    const val KEY_SETTINGS_BTN_PRIVACY: String = "btn_privacy"
    const val KEY_SETTINGS_BTN_CONTACTUS: String = "btn_contactus"

    //--------------------------------------Home Frag-----------------------------------
    // Arguments for home fragment
    const val ARG_CURRENT_VERB_SCORE: String = "arg_current_verb_score"
    const val ARG_CURRENT_SENTENCE_SCORE: String = "arg_current_sentence_score"
    const val ARG_CURRENT_PHRASAL_SCORE: String = "arg_current_phrasal_score"
    const val ARG_CURRENT_NOUN_SCORE: String = "arg_current_noun_score"
    const val ARG_CURRENT_ADJ_SCORE: String = "arg_current_adj_score"
    const val ARG_CURRENT_ADV_SCORE: String = "arg_current_adv_score"
    const val ARG_CURRENT_IDIOM_SCORE: String = "arg_current_idiom_score"

    const val TAG_PREF_ALLOWED_VERBS_NUMBER: String = "arg_allowed_verbs_number"
    const val TAG_PREF_ALLOWED_SENTENCES_NUMBER: String = "arg_allowed_sentences_number"
    const val TAG_PREF_ALLOWED_PHRASALS_NUMBER: String = "arg_allowed_phrasals_number"
    const val TAG_PREF_ALLOWED_NOUNS_NUMBER: String = "arg_allowed_nouns_number"
    const val TAG_PREF_ALLOWED_ADJS_NUMBER: String = "arg_allowed_adjs_number"
    const val TAG_PREF_ALLOWED_ADVS_NUMBER: String = "arg_allowed_advs_number"
    const val TAG_PREF_ALLOWED_IDIOMS_NUMBER: String = "arg_allowed_idioms_number"

    //--------------------------------------TableNavFrag-----------------------------------
    // Category names
    const val ALL_NAME: String = "Total"
    const val VERB_NAME: String = "Verbs"
    const val SENTENCE_NAME: String = "Sentences"
    const val PHRASAL_NAME: String = "Phrasal verbs"
    const val NOUN_NAME: String = "Nouns"
    const val ADJ_NAME: String = "Adjectives"
    const val ADV_NAME: String = "Adverbs"
    const val IDIOM_NAME: String = "Idioms"

    @JvmField
    val categoryNamesList: Array<String> =
        arrayOf(VERB_NAME, SENTENCE_NAME, PHRASAL_NAME, NOUN_NAME, ADJ_NAME, ADV_NAME, IDIOM_NAME)

    //--------------------------------------Preferences-----------------------------------
    // Preference tags for items added
    const val KEY_PREF_USER_NAME: String = "user_name"

    const val TAG_PREF_VERB_ADDED: String = "tag_verb_added"
    const val TAG_PREF_SENTENCE_ADDED: String = "tag_sentence_added"
    const val TAG_PREF_PHRASAL_ADDED: String = "tag_phrasal_added"
    const val TAG_PREF_NOUN_ADDED: String = "tag_noun_added"
    const val TAG_PREF_ADJ_ADDED: String = "tag_adj_added"
    const val TAG_PREF_ADV_ADDED: String = "tag_adv_added"
    const val TAG_PREF_IDIOM_ADDED: String = "tag_idiom_added"

    // Video tags for added items
    const val TAG_PREF_VERB_ADDED_VIDEO: String = "tag_verb_added_video"
    const val TAG_PREF_SENTENCE_ADDED_VIDEO: String = "tag_sentence_added_video"
    const val TAG_PREF_PHRASAL_ADDED_VIDEO: String = "tag_phrasal_added_video"
    const val TAG_PREF_NOUN_ADDED_VIDEO: String = "tag_noun_added_video"
    const val TAG_PREF_ADJ_ADDED_VIDEO: String = "tag_adj_added_video"
    const val TAG_PREF_ADV_ADDED_VIDEO: String = "tag_adv_added_video"
    const val TAG_PREF_IDIOM_ADDED_VIDEO: String = "tag_idiom_added_video"

    // Video point tags for added items
    const val TAG_PREF_VERB_POINT_ADDED_VIDEO: String = "tag_verb_point_added_video"
    const val TAG_PREF_SENTENCE_POINT_ADDED_VIDEO: String = "tag_sentence_point_added_video"
    const val TAG_PREF_PHRASAL_POINT_ADDED_VIDEO: String = "tag_phrasal_point_added_video"
    const val TAG_PREF_NOUN_POINT_ADDED_VIDEO: String = "tag_noun_point_added_video"
    const val TAG_PREF_ADJ_POINT_ADDED_VIDEO: String = "tag_adj_point_added_video"
    const val TAG_PREF_ADV_POINT_ADDED_VIDEO: String = "tag_adv_point_added_video"
    const val TAG_PREF_IDIOM_POINT_ADDED_VIDEO: String = "tag_idiom_point_added_video"

    // Quiz completion tags
    const val TAG_PREF_VERB_QUIZ_COMPLETED: String = "tag_verb_quiz_completed"
    const val TAG_PREF_SENTENCE_QUIZ_COMPLETED: String = "tag_sentence_quiz_completed"
    const val TAG_PREF_PHRASAL_QUIZ_COMPLETED: String = "tag_phrasal_quiz_completed"
    const val TAG_PREF_NOUN_QUIZ_COMPLETED: String = "tag_noun_quiz_completed"
    const val TAG_PREF_ADJ_QUIZ_COMPLETED: String = "tag_adj_quiz_completed"
    const val TAG_PREF_ADV_QUIZ_COMPLETED: String = "tag_adv_quiz_completed"
    const val TAG_PREF_IDIOM_QUIZ_COMPLETED: String = "tag_idiom_quiz_completed"

    // Quiz completion correctly tags
    const val TAG_PREF_VERB_QUIZ_COMPLETED_CORRECTLY: String = "tag_verb_quiz_completed_correctly"
    const val TAG_PREF_SENTENCE_QUIZ_COMPLETED_CORRECTLY: String =
        "tag_sentence_quiz_completed_correctly"
    const val TAG_PREF_PHRASAL_QUIZ_COMPLETED_CORRECTLY: String =
        "tag_phrasal_quiz_completed_correctly"
    const val TAG_PREF_NOUN_QUIZ_COMPLETED_CORRECTLY: String = "tag_noun_quiz_completed_correctly"
    const val TAG_PREF_ADJ_QUIZ_COMPLETED_CORRECTLY: String = "tag_adj_quiz_completed_correctly"
    const val TAG_PREF_ADV_QUIZ_COMPLETED_CORRECTLY: String = "tag_adv_quiz_completed_correctly"
    const val TAG_PREF_IDIOM_QUIZ_COMPLETED_CORRECTLY: String = "tag_idiom_quiz_completed_correctly"
}
