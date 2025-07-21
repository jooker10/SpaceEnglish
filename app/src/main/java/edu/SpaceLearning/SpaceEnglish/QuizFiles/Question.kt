package edu.SpaceLearning.SpaceEnglish.UtilsClasses


data class Question(
    val theMainElement: String,
    /*val option1: String,
    val option2: String,
    val option3: String,*/
     val optionsList : List<String>,
    val rightAnswer: String
)