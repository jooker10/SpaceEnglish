package edu.SpaceLearning.SpaceEnglish.Listeners

import android.widget.RadioButton
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Question

interface UiControllerListener {
   fun resetUIForNextQuestion()
   fun updateUIWithQuestion(question : Question , currentQstIndex : Int)
    fun updateCorrectAnswerScore(userCorrectScore : Int)
    fun updateWrongAnswerScore(userWrongScore : Int)
    fun getCheckedOptionId() : Int
    fun getCheckedOptionRadioButton(checkedRadioID : Int) : RadioButton
    fun updateOptionsUiAfterAnswers(rightAnswer : String)
    fun updateNextButtonText(text : String)
    fun changeRightWrongAnswerColors(rightAnswer: String)
}