package edu.SpaceLearning.SpaceEnglish.UtilsClasses


data class Question(
    val prompt: String,
    val options : List<String>,
    val correctAnswer: String
)