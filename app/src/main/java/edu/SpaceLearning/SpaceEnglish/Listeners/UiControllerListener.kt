package edu.SpaceLearning.SpaceEnglish.Listeners

import android.widget.RadioButton
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Question

interface UiControllerListener {
   fun resetUIForNextQuestion()
   fun updateUIWithQuestion(question : Question , currentQstIndex : Int)
    fun updateUiRightScore(userRightScore : Int)
    fun updateUiWrongScore(userWrongScore : Int)
    fun getCheckedOptionUiID() : Int
    fun getCheckedOptionUi(checkedRadioID : Int) : RadioButton
    fun updateOptionsUiAfterAnswers(rightAnswer : String)
    fun updateNextButtonText(text : String)
    fun changeRightWrongAnswerColors(rightAnswer: String)
}