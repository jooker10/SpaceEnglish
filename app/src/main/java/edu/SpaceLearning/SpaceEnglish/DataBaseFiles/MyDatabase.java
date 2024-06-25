/**
 * MyDatabase.java
 * This class extends SQLiteAssetHelper to manage the SQLite database for the English learning app.
 * The database is used in read-only mode by default. Uncomment the columns names below if you want to enable write operations.
 * Updated on: [23-Jun-2024]
 * Author: [Your Name]
 * Version: 1.0
 */

package edu.SpaceLearning.SpaceEnglish.DataBaseFiles;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

  // Database name and version
  public static final String DB_NAME = "english_learning.db";
  public static final int DB_VERSION = 1;

  // Table names
  // Table of Verbs
  public static final String TABLE_VERBS = "table_verbs";
  /* public static final String VERB_ID = "id_verb";
  public static final String VERB_ENG = "english_verb";
  public static final String VERB_FR = "french_verb";
  public static final String VERB_SP = "spanish_verb";
  public static final String VERB_AR = "arabic_verb";*/

  // Table of Sentences
  public static final String TABLE_SENTENCES = "table_sentences_phrases";
 /* public static final String SENTENCES_ID = "id_sentence";
  public static final String SENTENCES_ENG = "english_sentence";
  public static final String SENTENCES_FR = "french_sentence";
  public static final String SENTENCES_SP = "spanish_sentence";
  public static final String SENTENCES_AR = "arabic_sentence";*/

  // Table of Phrasal Verbs
  public static final String TABLE_PHRASALS = "table_phrasals";
/*  public static final String PHRASAL_ID = "id_phrasal";
  public static final String PHRASAL_ENG = "english_phrasal";
  public static final String PHRASAL_FR = "french_phrasal";
  public static final String PHRASAL_SP = "spanish_phrasal";
  public static final String PHRASAL_AR = "arabic_phrasal";*/

  // Table of Nouns
  public static final String TABLE_NOUNS = "table_nouns";
  /*public static final String NOUN_ID = "id_noun";
  public static final String NOUN_ENG = "english_noun";
  public static final String NOUN_FR = "french_noun";
  public static final String NOUN_SP = "spanish_noun";
  public static final String NOUN_AR = "arabic_noun";*/

  // Table of Adjectives
  public static final String TABLE_ADJECTIVES = "table_adjectives";
  /*public static final String ADJ_ID = "id_adj";
  public static final String ADJ_ENG = "english_adj";
  public static final String ADJ_FR = "french_adj";
  public static final String ADJ_SP = "spanish_adj";
  public static final String ADJ_AR = "arabic_adj";*/

  // Table of Adverbs
  public static final String TABLE_ADVERBS = "table_adverbs";
  /*public static final String ADV_ID = "id_adv";
  public static final String ADV_ENG = "english_adv";
  public static final String ADV_FR = "french_adv";
  public static final String ADV_SP = "spanish_adv";
  public static final String ADV_AR = "arabic_adv";*/

  // Table of Idioms
  public static final String TABLE_IDIOMS = "table_idioms";
 /* public static final String IDIOM_ID = "id_idiom";
  public static final String IDIOM_ENG = "english_idiom";
  public static final String IDIOM_FR = "french_idiom";
  public static final String IDIOM_SP = "spanish_idiom";
  public static final String IDIOM_AR = "arabic_idiom";*/

  public MyDatabase(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }
}
