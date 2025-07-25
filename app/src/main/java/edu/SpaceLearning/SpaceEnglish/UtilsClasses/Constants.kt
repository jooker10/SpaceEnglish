package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * Constants class that contains various constant values used throughout the application.
 */
object Constants {
    // Permission scores for different categories
    const val PERMISSION_SCORE_PHRASAL: Int = 80
    const val PERMISSION_SCORE_NOUN: Int = 150
    const val PERMISSION_SCORE_ADJECTIVE: Int = 250
    const val PERMISSION_SCORE_ADVERB: Int = 400
    const val PERMISSION_SCORE_IDIOM: Int = 600


    // Array of permission scores for categories

     val CATEGORY_PERMISSION_SCORES: IntArray = intArrayOf(
        PERMISSION_SCORE_PHRASAL,
        PERMISSION_SCORE_NOUN,
        PERMISSION_SCORE_ADJECTIVE,
        PERMISSION_SCORE_ADVERB,
        PERMISSION_SCORE_IDIOM
    )

    // Quiz labels
    const val QUIZ_COMPLETED: String = "Quiz completed"
    const val QUIZ_COMPLETED_SUCCESSFULLY: String = "Quiz completed successfully"
    const val QUIZ_ELEMENT_ADDED: String = "Elements added"
    const val QUIZ_ELEMENT_ADDED_ADS: String = "Elements added (Ads)"
    const val QUIZ_POINT_ADDED_ADS: String = "Points added (Ads)"

    // Navigation indices
    const val NAV_INDEX_HOME: Int = 0
    const val NAV_INDEX_TABLE: Int = 1
    const val NAV_INDEX_QUIZ: Int = 3
    const val NAV_INDEX_SETTINGS : Int = 4

    // Fragment tags
    const val TAG_HOME: String = "home_nav_fragment"
    const val TAG_TABLES: String = "tables_nav_fragment"
    const val TAG_QUIZ: String = "quiz_nav_fragment"
    const val TAG_SETTINGS: String = "settings_nav_fragment"

    const val TAG_CATEGORY_TABLES : String = "tables_category_fragment"
    const val TAG_CATEGORY_QUIZ: String = "quiz_category_fragment"
    const val TAG_MAIN_BOTTOM_SHEET : String = "sheet_main_fragment"
    const val TAG_NO_CONNECTION : String = "no_connection_fragment"
    const val TAG_MAX_QUESTIONS  = "MAX_NUMBERS_OF_QUESTIONS"

    // Preference tags
    const val PREF_LANGUAGE_SELECTION : String = "choosing_lang"
    const val PREF_NATIVE_LANGUAGE_DEFAULT: String = "French"
    const val LANGUAGE_NATIVE_FRENCH: String = "French"
    const val LANGUAGE_NATIVE_SPANISH: String = "Spanish"
    const val LANGUAGE_NATIVE_ARABIC: String = "Arabic"
    const val PREF_CATEGORY_TYPE: String = "categoryType"

    const val PREF_SCORE_VERBS: String = "verb_score"
    const val PREF_SCORE_SENTENCES: String = "sentence_score"
    const val PREF_SCORE_PHRASAL: String = "phrasal_score"
    const val PREF_SCORE_NOUNS: String = "noun_score"
    const val PREF_SCORE_ADJECTIVES: String = "adj_score"
    const val PREF_SCORE_ADVERBS: String = "adv_score"
    const val PREF_SCORE_IDIOMS: String = "idiom_score"

    const val PREF_IS_THEME_DARK_MODE: String = "is_dark_mode"
    const val PREF_PROFILE_IMAGE_URI: String = "uri_profile"
    const val TAG_PREF_SCORES_TOTAL_SCORE: String = "global_main_score"
    const val SHARED_PREFS_FILE: String = "privateFile.xml"
    const val PREF_IS_FIRST_LAUNCH: String = "arg_is_first_time_activity"

    // Settings keys
    const val SETTINGS_KEY_THEME_SWITCH: String = "switch_theme"
    const val SETTINGS_KEY_LANGUAGE_SWITCH: String = "switch_language"
    const val SETTINGS_KEY_PRIVACY_BUTTON: String = "btn_privacy"
    const val SETTINGS_KEY_CONTACT_US: String = "btn_contactus"

    //--------------------------------------Home Frag-----------------------------------
    // Arguments for home fragment
    const val ARG_CURRENT_VERB_SCORE: String = "arg_current_verb_score"
    const val ARG_CURRENT_SENTENCE_SCORE: String = "arg_current_sentence_score"
    const val ARG_CURRENT_PHRASAL_SCORE: String = "arg_current_phrasal_score"
    const val ARG_CURRENT_NOUN_SCORE: String = "arg_current_noun_score"
    const val ARG_CURRENT_ADJ_SCORE: String = "arg_current_adj_score"
    const val ARG_CURRENT_ADV_SCORE: String = "arg_current_adv_score"
    const val ARG_CURRENT_IDIOM_SCORE: String = "arg_current_idiom_score"

    const val PREF_ALLOWED_VERBS_COUNT: String = "arg_allowed_verbs_number"
    const val PREF_ALLOWED_SENTENCES_COUNT: String = "arg_allowed_sentences_number"
    const val PREF_ALLOWED_PHRASAL_COUNT: String = "arg_allowed_phrasals_number"
    const val PREF_ALLOWED_NOUNS_COUNT: String = "arg_allowed_nouns_number"
    const val PREF_ALLOWED_ADJECTIVES_COUNT: String = "arg_allowed_adjs_number"
    const val PREF_ALLOWED_ADVERBS_COUNT: String = "arg_allowed_advs_number"
    const val PREF_ALLOWED_IDIOMS_COUNT: String = "arg_allowed_idioms_number"

    //--------------------------------------TableNavFrag-----------------------------------
    // Category names
    const val CATEGORY_NAME_ALL: String = "Total"
    const val CATEGORY_NAME_VERB: String = "Verbs"
    const val CATEGORY_NAME_SENTENCE: String = "Sentences"
    const val CATEGORY_NAME_PHRASAL: String = "Phrasal verbs"
    const val CATEGORY_NAME_NOUNS: String = "Nouns"
    const val CATEGORY_NAME_ADJECTIVE: String = "Adjectives"
    const val CATEGORY_NAME_ADVERBS: String = "Adverbs"
    const val CATEGORY_NAME_IDIOMS: String = "Idioms"


    val CATEGORY_NAME_LIST: Array<String> =
        arrayOf(CATEGORY_NAME_VERB, CATEGORY_NAME_SENTENCE, CATEGORY_NAME_PHRASAL, CATEGORY_NAME_NOUNS, CATEGORY_NAME_ADJECTIVE, CATEGORY_NAME_ADVERBS, CATEGORY_NAME_IDIOMS)

    //--------------------------------------Preferences-----------------------------------
    // Preference tags for items added
    const val PREF_USERNAME: String = "user_name"

    const val PREF_VERB_ADDED: String = "tag_verb_added"
    const val PREF_SENTENCE_ADDED: String = "tag_sentence_added"
    const val PREF_PHRASAL_ADDED: String = "tag_phrasal_added"
    const val PREF_NOUN_ADDED: String = "tag_noun_added"
    const val PREF_ADJECTIVE_ADDED: String = "tag_adj_added"
    const val PREF_ADVERB_ADDED: String = "tag_adv_added"
    const val PREF_IDIOM_ADDED: String = "tag_idiom_added"

    // Video tags for added items
    const val PREF_VERB_ADDED_VIDEO: String = "tag_verb_added_video"
    const val PREF_SENTENCE_ADDED_VIDEO: String = "tag_sentence_added_video"
    const val PREF_PHRASAL_ADDED_VIDEO: String = "tag_phrasal_added_video"
    const val PREF_NOUN_ADDED_VIDEO: String = "tag_noun_added_video"
    const val PREF_ADJECTIVE_ADDED_VIDEO: String = "tag_adj_added_video"
    const val PREF_ADVERB_ADDED_VIDEO: String = "tag_adv_added_video"
    const val PREF_IDIOM_ADDED_VIDEO: String = "tag_idiom_added_video"

    // Video point tags for added items
    const val PREF_VERB_POINT_ADDED_VIDEO: String = "tag_verb_point_added_video"
    const val PREF_SENTENCE_POINT_ADDED_VIDEO: String = "tag_sentence_point_added_video"
    const val PREF_PHRASAL_POINT_ADDED_VIDEO: String = "tag_phrasal_point_added_video"
    const val PREF_NOUN_POINT_ADDED_VIDEO: String = "tag_noun_point_added_video"
    const val PREF_ADJECTIVE_POINT_ADDED_VIDEO: String = "tag_adj_point_added_video"
    const val PREF_ADVERB_POINT_ADDED_VIDEO: String = "tag_adv_point_added_video"
    const val PREF_IDIOM_POINT_ADDED_VIDEO: String = "tag_idiom_point_added_video"

    // Quiz completion tags
    const val PREF_VERB_QUIZ_COMPLETED: String = "tag_verb_quiz_completed"
    const val PREF_SENTENCE_QUIZ_COMPLETED: String = "tag_sentence_quiz_completed"
    const val PREF_PHRASAL_QUIZ_COMPLETED: String = "tag_phrasal_quiz_completed"
    const val PREF_NOUN_QUIZ_COMPLETED: String = "tag_noun_quiz_completed"
    const val PREF_ADJECTIVE_QUIZ_COMPLETED: String = "tag_adj_quiz_completed"
    const val PREF_ADVERB_QUIZ_COMPLETED: String = "tag_adv_quiz_completed"
    const val PREF_IDIOM_QUIZ_COMPLETED: String = "tag_idiom_quiz_completed"

    // Quiz completion correctly tags
    const val PREF_VERB_QUIZ_COMPLETED_CORRECT: String = "tag_verb_quiz_completed_correctly"
    const val PREF_SENTENCE_QUIZ_COMPLETED_CORRECT: String = "tag_sentence_quiz_completed_correctly"
    const val PREF_PHRASAL_QUIZ_COMPLETED_CORRECT: String = "tag_phrasal_quiz_completed_correctly"
    const val PREF_NOUN_QUIZ_COMPLETED_CORRECT: String = "tag_noun_quiz_completed_correctly"
    const val PREF_ADJECTIVE_QUIZ_COMPLETED_CORRECT: String = "tag_adj_quiz_completed_correctly"
    const val PREF_ADVERB_QUIZ_COMPLETED_CORRECT: String = "tag_adv_quiz_completed_correctly"
    const val PREF_IDIOM_QUIZ_COMPLETED_CORRECT: String = "tag_idiom_quiz_completed_correctly"
}
