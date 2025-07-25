package edu.SpaceLearning.SpaceEnglish._RoomDatabase

sealed class CategoryType {

    object Verb : CategoryType()
    object Sentence : CategoryType()
    object Phrasal : CategoryType()
    object Noun : CategoryType()
    object Adjective : CategoryType()
    object Adverb : CategoryType()
    object Idiom : CategoryType()
}