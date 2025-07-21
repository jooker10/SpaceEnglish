package edu.SpaceLearning.SpaceEnglish.UtilsClasses


data class Category (
    val id: Int,
    val englishName: String,
    val frenchName: String,
    val spanishName: String,
    val arabicName: String,
    val exampleSentence: String? = null)
