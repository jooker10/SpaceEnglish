package edu.SpaceLearning.SpaceEnglish.QuizFrags;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SoundManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category;
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Question;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.QuizCategoriesFragmentBinding;


public class QuizCategoriesFragment extends Fragment implements CountDownTimerHelper.OnCountdownListener {
    private QuizCategoriesFragmentBinding binding;
    private QuizCategoryClickListener quizCategoryListener;
    private SoundManager soundManager;

    private CountDownTimerHelper countDownTimerHelper;
    private int maxCounterTimer = 15;

    private String categoryType;

    private ColorStateList rbDefaultColorTxt;

    private final List<Question> questionsList = new ArrayList<>();
    private List<Category> currentCategorySubList;
    private int currentQstIndex = 0;
    private int userRightScore = 0;
    private int userWrongScore = 0;

    private boolean isAnswered = false;

    private Question currentQuestion;


    public QuizCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryType = bundle.getString(Constants.TAG_CATEGORY_TYPE, Constants.VERB_NAME);
        }
    }

    public static QuizCategoriesFragment getInstance(String categoryType) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TAG_CATEGORY_TYPE, categoryType);
        QuizCategoriesFragment fragment = new QuizCategoriesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_categories_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = QuizCategoriesFragmentBinding.bind(view);
        soundManager = new SoundManager(requireActivity());
        rbDefaultColorTxt = binding.QuizCategoryOption1.getTextColors();

        binding.tvQuizChooseTheRightAnswerLabel.setText(chooseTvQuizRightAnswerRequiredLanguage(Utils.nativeLanguage)); // Choose the right native Language

        fillListWithRequiredCategoryFromDataBase();
        ifCategoryIsSentenceOrIdiomAddMargins();
        initCountDownTimerAndStartIt();
//-------------------------------------------------------------begin-----------------------------------------------------------------

        setRandomIndexesIncludingTheRightAnswerIndexThenGenerateRequiredQuestionList(); // Random 3 indexes including the right index -> to choose random question list.

        showNextQuestion();       // a new question set with there options + native element

        binding.btnConfirmNextCategory.setOnClickListener(v -> {   // the button Confirm/Next
            if (!isAnswered) checkAnswer();
            else {
                showNextQuestion();
            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------------------

          txtRadioOptionToSpeech(binding.QuizCategoryOption1);
          txtRadioOptionToSpeech(binding.QuizCategoryOption2);
          txtRadioOptionToSpeech(binding.QuizCategoryOption3);

          String theQst = chooseTvQuizRightAnswerRequiredLanguage(Utils.nativeLanguage);
        String mainElementQst = binding.tvQuizMainElementQuestion.getText().toString();
        String option1 = binding.QuizCategoryOption1.getText().toString();
        String option2 = binding.QuizCategoryOption2.getText().toString();
        String option3 = binding.QuizCategoryOption3.getText().toString();

        String questionToShareTxt = theQst + " :\n" +mainElementQst + "\n" + "A." + option1 +
                "\n" + "B." + option2 + "\n" + "C." + option3 + "\n"+"\n" + "For additional questions or tests, please visit our app :"
                +"\n" + getString(R.string.url_app1);
        binding.fabShareQstFriend.setOnClickListener(v -> shareQst(questionToShareTxt));

    }

    private void ifCategoryIsSentenceOrIdiomAddMargins() {
        int[] margins = {0,12,0,4};
        if (categoryType.equals(Constants.IDIOM_NAME) || categoryType.equals(Constants.SENTENCE_NAME)) {
            maxCounterTimer = 20;
            RadioGroup.LayoutParams layoutParams1 = (RadioGroup.LayoutParams) binding.QuizCategoryOption1.getLayoutParams();
            layoutParams1.setMargins(margins[0], margins[1], margins[2], margins[3]);
            RadioGroup.LayoutParams layoutParams2 = (RadioGroup.LayoutParams) binding.QuizCategoryOption2.getLayoutParams();
            layoutParams2.setMargins(margins[0], margins[1], margins[2], margins[3]);
            RadioGroup.LayoutParams layoutParams3 = (RadioGroup.LayoutParams) binding.QuizCategoryOption3.getLayoutParams();
            layoutParams3.setMargins(margins[0], margins[1], margins[2], margins[3]);
        }
    }

    private void fillListWithRequiredCategoryFromDataBase() {
        DbAccess dbAccess = DbAccess.getInstance(requireActivity());
        dbAccess.open_to_read();
        ArrayList<Category> allRequiredTypeListFromDB = new ArrayList<>(dbAccess.getAllElementsCategoryList(categoryType, false));
        dbAccess.close();
        Collections.shuffle(allRequiredTypeListFromDB);
        currentCategorySubList = allRequiredTypeListFromDB.subList(0, Utils.maxQuestionsPerQuiz);
    }

    private void initCountDownTimerAndStartIt() {
        if (countDownTimerHelper == null) {
            countDownTimerHelper = new CountDownTimerHelper(maxCounterTimer * 1000L, 1000);
            countDownTimerHelper.setListener(this);
        }
    }

    //---------------------------------------------------------------------------------------------

    private void checkEmptyAnswerCounter() {

        quizCategoryListener.onShowSimpleAdsQuiz();
        userWrongScore++;
        binding.tvQuizUserWrongAnswerCounter.setText(String.valueOf(userWrongScore));

        isAnswered = true;
        binding.btnConfirmNextCategory.setText("Next");
      //  Random random = new Random();

       // int randomIndex = random.nextInt(Utils.phrasesIncorrectAnswers.size());
       // String text = Utils.phrasesIncorrectAnswers.get(randomIndex);
        String text = "You don't choose any answer , please make sure to choose an answer next time ";
        speakEnglish(text);

        for (int i = 0; i < binding.quizRadioGroup.getChildCount(); i++) {
            RadioButton rbMaybeCorrect = (RadioButton) binding.quizRadioGroup.getChildAt(i);
            String textMaybeCorrect = rbMaybeCorrect.getText().toString();
            if (textMaybeCorrect.equals(currentQuestion.getRightAnswer())) {
                rbMaybeCorrect.setTextColor(Color.GREEN);
            } else {
                rbMaybeCorrect.setTextColor(Color.RED);
            }
        }

        // the counter is reached the final Question then change the btn to finish
        if (currentQstIndex == Utils.maxQuestionsPerQuiz) {
            binding.btnConfirmNextCategory.setText("Finish");
            binding.btnConfirmNextCategory.setTextColor(Color.GREEN);
            binding.btnConfirmNextCategory.setBackgroundColor(Color.WHITE);
        }

    }

    private void speakEnglish(String text) {
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager.speak(text);
        }
    }

   /* private void SetRandomQuestions1() {
        for (int i = 0; i < currentElementsList.size(); i++) {
            List<Integer> randomList = new ArrayList<>();
            Random random = new Random();

            while (randomList.size() != 2) {
                int j = random.nextInt(currentElementsList.size());
                if (!randomList.contains(j) && j != i) {
                    randomList.add(j);
                }
            }

            randomList.add(i);
            Collections.shuffle(randomList);

            String mainNativeElement = choosingTheRightMainElementLang(Utils.nativeLanguage, i); // here change the Element language
            String rbOption1 = currentElementsList.get(randomList.get(0)).getCategoryEng();
            String rbOption2 = currentElementsList.get(randomList.get(1)).getCategoryEng();
            String rbOption3 = currentElementsList.get(randomList.get(2)).getCategoryEng();
            String rightAnswer = currentElementsList.get(i).getCategoryEng();

            questionsList.add(new Question(mainNativeElement, rbOption1, rbOption2, rbOption3, rightAnswer));
        }
    }*/

    private void setRandomIndexesIncludingTheRightAnswerIndexThenGenerateRequiredQuestionList() {
         Set<Integer> randomSet = new HashSet<>();
         Random random = new Random();

        for (int i = 0; i < currentCategorySubList.size(); i++) {
            randomSet.clear(); // Clear the set for each new iteration

            // Generate two random indices different from i
            while (randomSet.size() < 2) {
                int randomIndex = random.nextInt(currentCategorySubList.size());
                if (randomIndex != i) {
                    randomSet.add(randomIndex);
                }
            }

            // Add i to the set
            randomSet.add(i);

            // Convert set to list for shuffling
            List<Integer> randomList = new ArrayList<>(randomSet);
            Collections.shuffle(randomList);
            // Now randomList contains 3 unique indices: i and two others
            // You can use these indices to select questions from currentElementsList
            String mainNativeElement = choosingTheRightMainElementLang(Utils.nativeLanguage, i); // here change the Element language
            String rbOption1 = currentCategorySubList.get(randomList.get(0)).getCategoryEng();
            String rbOption2 = currentCategorySubList.get(randomList.get(1)).getCategoryEng();
            String rbOption3 = currentCategorySubList.get(randomList.get(2)).getCategoryEng();
            String rightAnswer = currentCategorySubList.get(i).getCategoryEng();

            questionsList.add(new Question(mainNativeElement, rbOption1, rbOption2, rbOption3, rightAnswer));
        }
    }



    private String chooseTvQuizRightAnswerRequiredLanguage(String nativeLanguage) {
        switch (nativeLanguage) {
            case Constants.LANGUAGE_NATIVE_ARABIC:
                return getResources().getString(R.string.TheQstInArabic);
            case Constants.LANGUAGE_NATIVE_SPANISH:
                return getResources().getString(R.string.TheQstInSpanish);
            default:
                return getResources().getString(R.string.TheQstInFrench);
        }

    }

    private String choosingTheRightMainElementLang(String nativeLanguage, int currentIndex) {
        switch (nativeLanguage) {
            case Constants.LANGUAGE_NATIVE_ARABIC:
                return currentCategorySubList.get(currentIndex).getCategoryAr();

            case Constants.LANGUAGE_NATIVE_SPANISH:
                return currentCategorySubList.get(currentIndex).getCategorySp();

            default:
                return currentCategorySubList.get(currentIndex).getCategoryFr();
        }
    }

    @Override
    public void onTick(int secondsUntilFinished) {
        binding.tvCounterDownTimer.setText(String.valueOf(secondsUntilFinished));
        binding.progressBarTimer.setProgress(binding.progressBarTimer.getProgress() + 100/(maxCounterTimer-1));
        if(secondsUntilFinished <= 5) {
            soundManager.playSound(requireActivity(),R.raw.start_quiz);
           //binding.tvCounterDownTimer.setTextColor(Color.RED);
        }


    }

    @Override
    public void onFinish() {
        // Handle timer finished event
        counterDownTimerOver();
        int idCheckedRadio = binding.quizRadioGroup.getCheckedRadioButtonId();
        if (idCheckedRadio == -1) {
            soundManager.playSound(requireActivity(),R.raw.error4);
            checkEmptyAnswerCounter();
        } else {
            checkAnswer();
        }
    }


    //-------------------------------Listener-------------------------------------------------------
    public interface QuizCategoryClickListener {
        void onShowSimpleAdsQuiz();
        void onShowVideoAdsQuiz();
        void onSetQuizCategoryResultClick(String categoryType , int pointsAdded , int elementsAdded , int userRightAnswerScore , String msg);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof QuizCategoryClickListener) {
            quizCategoryListener = (QuizCategoryClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        quizCategoryListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        soundManager.release();

        if (countDownTimerHelper != null) {
            countDownTimerHelper.stop();
        }
    }

    private void counterDownTimerOver() {

                String text = "Time over! ";
                // Check if the TextToSpeech instance is initialized before using it.
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager.speak(text);
        }

    }


    //---------------------------gpt function-------------------------------------------
    private void showNextQuestion() {
        if (currentQstIndex < Utils.maxQuestionsPerQuiz) {

            isAnswered = false;
            resetUIForNextQuestion(); // Reset UI Color & Timer & clean Check RadioButtons for the next question
            currentQuestion = questionsList.get(currentQstIndex);
            updateUIWithQuestion(currentQuestion);
            currentQstIndex++;

            if (countDownTimerHelper != null) {
                binding.tvCounterDownTimer.setText(String.valueOf(maxCounterTimer / 1000)); // Update the TextView with the initial time (15 seconds)
                countDownTimerHelper.start();
            }

        } else {
            isAnswered = true;
            if (countDownTimerHelper != null) {
                countDownTimerHelper.stop();
            }
            finishQuiz();
        }
    }



    private void resetUIForNextQuestion() {
        /*isAnswered = false;*/
        binding.progressBarTimer.setProgress(0);
        binding.btnConfirmNextCategory.setText(R.string.confirm_quiz_text);   // changed R.string.confirm_text
        binding.QuizCategoryOption1.setTextColor(rbDefaultColorTxt);
        binding.QuizCategoryOption2.setTextColor(rbDefaultColorTxt);
        binding.QuizCategoryOption3.setTextColor(rbDefaultColorTxt);
        binding.quizRadioGroup.clearCheck();
    }

    private void updateUIWithQuestion(Question question) {
        binding.tvQuizMainElementQuestion.setText(question.getTheMainElement());
        binding.QuizCategoryOption1.setText(question.getOption1());
        binding.QuizCategoryOption2.setText(question.getOption2());
        binding.QuizCategoryOption3.setText(question.getOption3());
        binding.tvQuizCurrentIndex.setText((currentQstIndex + 1) + "/" + Utils.maxQuestionsPerQuiz);
    }

    private void checkAnswer() {
        int checkedRadioID = binding.quizRadioGroup.getCheckedRadioButtonId();
        if (checkedRadioID != -1) {
            isAnswered = true;
            if(countDownTimerHelper != null) {countDownTimerHelper.pause();}  // Pause the timer when checking the answer
            binding.btnConfirmNextCategory.setText(R.string.next_quiz_text);
            RadioButton radioSelected = requireView().findViewById(checkedRadioID);
            String userAnswer = radioSelected.getText().toString();

            if (userAnswer.equals(currentQuestion.getRightAnswer())) {
                handleCorrectAnswer();
            } else {
                handleIncorrectAnswer();
            }

            setAnsweredRequiredRadioButtonsColorText(currentQuestion.getRightAnswer()); //  right answer (green color) , wrong answers (red color).

            if (currentQstIndex == Utils.maxQuestionsPerQuiz) {
                binding.btnConfirmNextCategory.setText(R.string.finish_quiz_text);
                MainActivity.textToSpeechManager.speak("Final Question!");

            }
        } else {
            handleNoAnswerSelected();
        }
    }

    private void setAnsweredRequiredRadioButtonsColorText(String rightAnswer) {
        for(int i = 0 ; i < binding.quizRadioGroup.getChildCount() ; i++) {
            RadioButton radioButton = (RadioButton) binding.quizRadioGroup.getChildAt(i);
            if(radioButton.getText().toString().equals(rightAnswer)) {
                radioButton.setTextColor(Color.GREEN);
            }
            else {radioButton.setTextColor(Color.RED);}
        }
    }

    private void handleCorrectAnswer() {
        soundManager.playSound(requireActivity(),R.raw.coins1);
        userRightScore++;
        //  if you want you can add anim here!
        binding.tvQuizUserRightAnswerCounter.setAnimation(AnimationUtils.loadAnimation(requireActivity(),R.anim.anim_tv_right_wrong_score));
        binding.tvQuizUserRightAnswerCounter.setText(String.valueOf(userRightScore));

        Random random = new Random();
        int randomIndex = random.nextInt(Utils.phrasesCorrectAnswers.size());
        String text = Utils.phrasesCorrectAnswers.get(randomIndex);
        speakEnglish(text);

    }


    private void handleIncorrectAnswer() {
        userWrongScore++;
        soundManager.playSound(requireActivity(),R.raw.error0);
        int randomIndex = new Random().nextInt(Utils.phrasesIncorrectAnswers.size());
        String text = Utils.phrasesIncorrectAnswers.get(randomIndex);
        speakEnglish(text);

        binding.tvQuizUserWrongAnswerCounter.setText(String.valueOf(userWrongScore));
        binding.tvQuizUserWrongAnswerCounter.setAnimation(AnimationUtils.loadAnimation(requireActivity(),R.anim.anim_tv_right_wrong_score));


    }

    private void handleNoAnswerSelected() {
        isAnswered = false;
        String text = getString(R.string.no_answer_selected_text);
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager.speak(text);
        }
    }


    private void finishQuiz() {
        //  here you can finish the quiz with dialog and set scores...etc
        if(Utils.switchSimpleToVideoAds) {
            quizCategoryListener.onShowVideoAdsQuiz();} else {quizCategoryListener.onShowVideoAdsQuiz();}
        Utils.switchSimpleToVideoAds = !Utils.switchSimpleToVideoAds;

        if (countDownTimerHelper != null)
            countDownTimerHelper.stop();

        finishQuizUpdateAll(userRightScore);
    }
    private void finishQuizUpdateAll(int userScore) {
        int pointsAdded = 0;
        int elementsAdded = 0;

        String msg = "";

        if((float) userScore == Utils.maxQuestionsPerQuiz) {
            // result 20
            pointsAdded = 10;
            elementsAdded = 3;
            msg = "Perfection Achieved!";

        } else if ((float) userScore >= 0.75f * Utils.maxQuestionsPerQuiz) {
            // result >=15
            pointsAdded = 5;
            elementsAdded = 2;
            msg = "Excellent";
        } else if ((float) userScore >= 0.5f * Utils.maxQuestionsPerQuiz) {
            // esult >=10
            pointsAdded = 4;
            elementsAdded = 1;
            msg = "Average";
        } else if((float) userScore >= 0.25f * Utils.maxQuestionsPerQuiz){
            // result >= 5
            pointsAdded = 1;
            msg = "Below Average";

        } else {
            // result <5
            msg = "Needs improvement";
        }

        finishQuizUpdateEachCategory(categoryType,pointsAdded,elementsAdded);
        quizCategoryListener.onSetQuizCategoryResultClick(categoryType,pointsAdded,elementsAdded,userRightScore,msg);

    }

    private void finishQuizUpdateEachCategory(String categoryType , int pointsAdded , int elementsAdded){
        switch(categoryType){
            case Constants.VERB_NAME:
             Scores.verbScore += pointsAdded;
             Utils.allowedVerbsNumber = getCurrentElementsNewSize(Utils.totalVerbsNumber,Utils.allowedVerbsNumber,elementsAdded);
             Scores.verbQuizCompleted++;
             Scores.verbAdded += elementsAdded;
             //Scores.verbPointsAdded += pointsAdded;
             if(pointsAdded == 10) Scores.verbQuizCompletedCorrectly++;
                break;
            case Constants.SENTENCE_NAME:
                Scores.sentenceScore += pointsAdded;
                Utils.allowedSentencesNumber = getCurrentElementsNewSize(Utils.totalSentencesNumber,Utils.allowedSentencesNumber,elementsAdded);
                Scores.sentenceQuizCompleted++;
                Scores.sentenceAdded += elementsAdded;
               // Scores.sentencePointsAdded += pointsAdded;
                if(pointsAdded == 10) Scores.sentenceQuizCompletedCorrectly++;
                break;
            case Constants.PHRASAL_NAME:
                Scores.phrasalScore += pointsAdded;
                Utils.allowedPhrasalsNumber = getCurrentElementsNewSize(Utils.totalPhrasalsNumber,Utils.allowedPhrasalsNumber,elementsAdded);
                Scores.phrasalQuizCompleted++;
                Scores.phrasalAdded += elementsAdded;
               // Scores.phrasalPointsAdded += pointsAdded;
                if(pointsAdded == 10) Scores.phrasalQuizCompletedCorrectly++;
                break;
            case Constants.NOUN_NAME:
                Scores.nounScore += pointsAdded;
                Utils.allowedNounsNumber = getCurrentElementsNewSize(Utils.totalNounsNumber,Utils.allowedNounsNumber,elementsAdded);
                Scores.nounQuizCompleted++;
                Scores.nounAdded += elementsAdded;
                //Scores.nounPointsAdded += pointsAdded;
                if(pointsAdded == 10) Scores.nounQuizCompletedCorrectly++;
                break;
            case Constants.ADJ_NAME:
                Scores.adjScore += pointsAdded;
                Utils.allowedAdjsNumber = getCurrentElementsNewSize(Utils.totalAdjsNumber,Utils.allowedAdjsNumber,elementsAdded);
                Scores.adjQuizCompleted++;
                Scores.adjAdded += elementsAdded;
                //Scores.adjPointsAdded += pointsAdded;
                if(pointsAdded == 10) Scores.adjQuizCompletedCorrectly++;
                break;
            case Constants.ADV_NAME:
                Scores.advScore += pointsAdded;
                Utils.allowedAdvsNumber = getCurrentElementsNewSize(Utils.totalAdvsNumber,Utils.allowedAdvsNumber,elementsAdded);
                Scores.advQuizCompleted++;
                Scores.advAdded += elementsAdded;
                //Scores.advPointsAdded += pointsAdded;
                if(pointsAdded == 10) Scores.advQuizCompletedCorrectly++;
                break;
            case Constants.IDIOM_NAME:
                Scores.idiomScore += pointsAdded;
                Utils.allowedIdiomsNumber = getCurrentElementsNewSize(Utils.totalIdiomsNumber,Utils.allowedIdiomsNumber,elementsAdded);
                Scores.idiomQuizCompleted++;
                Scores.idiomAdded += elementsAdded;
                //Scores.idiomPointsAdded += pointsAdded;
                if(pointsAdded == 10) Scores.idiomQuizCompletedCorrectly++;
                break;

        }
    }

    private void txtRadioOptionToSpeech (RadioButton radioOption) {
        radioOption.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                String text = radioOption.getText().toString();
                // Check if the TextToSpeech instance is initialized before using it.
                if (MainActivity.textToSpeechManager != null) {
                    MainActivity.textToSpeechManager.speak(text);
                }
            }
        });
    }

    private int getCurrentElementsNewSize(int maxElementsCategory,int currentElementsCategory,int elementsAdded ){
        if((currentElementsCategory + elementsAdded) <= maxElementsCategory) {
            currentElementsCategory  += elementsAdded;
        } else{
            currentElementsCategory = maxElementsCategory;
        }
        return currentElementsCategory;


    }

    private void shareQst(String qstToShare) {
        // Create an Intent with ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        shareIntent.setType("text/plain");

        // Put the text to share
        shareIntent.putExtra(Intent.EXTRA_TEXT, qstToShare);

        // Create a chooser to let the user choose where to share
        Intent chooserIntent = Intent.createChooser(shareIntent, "Share with");

        // Check if there are apps that can handle the share intent
        if (shareIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Start the chooser activity
            startActivity(chooserIntent);
        }
    }

}