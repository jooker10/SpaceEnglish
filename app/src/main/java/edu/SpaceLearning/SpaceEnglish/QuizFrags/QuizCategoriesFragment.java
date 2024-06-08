package edu.SpaceLearning.SpaceEnglish.QuizFrags;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.SpaceLearning.SpaceEnglish.SoundManager;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish.CountDownTimerHelper;
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.Category;
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess;
import edu.SpaceLearning.SpaceEnglish.Question;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;
import edu.SpaceLearning.SpaceEnglish._Main.Scores;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.QuizCategoriesFragmentBinding;


public class QuizCategoriesFragment extends Fragment implements CountDownTimerHelper.OnCountdownListener {
    private QuizCategoriesFragmentBinding binding;
    private QuizCategoryClickListener quizCategoryListener;
    private SoundManager soundManager;

    private CountDownTimerHelper countDownTimerHelper;
    private int maxCounterTimer = 15;

    private String categoryType;

    private ColorStateList rbDefaultColorTxt;

    private List<Question> questionsList = new ArrayList<>();
    private List<Category> choosedElementsList = new ArrayList<>();
    private int currentQstCounter, qstCounterTotal;
    private int userRightScore = 0;
    private int userWrongScore = 0;

    private boolean isAnswered;

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

        binding.tvQuizMainQuestionCategory.setText(choosingNativeLangQuestion(Utils.nativeLanguage)); // Choose the right native Language

        if (categoryType.equals(Constants.IDIOM_NAME) || categoryType.equals(Constants.SENTENCE_NAME)) {
            maxCounterTimer = 20;
            RadioGroup.LayoutParams layoutParams1 = (RadioGroup.LayoutParams) binding.QuizCategoryOption1.getLayoutParams();
            layoutParams1.setMargins(0, 12, 0, 4);
            RadioGroup.LayoutParams layoutParams2 = (RadioGroup.LayoutParams) binding.QuizCategoryOption2.getLayoutParams();
            layoutParams2.setMargins(0, 12, 0, 4);
            RadioGroup.LayoutParams layoutParams3 = (RadioGroup.LayoutParams) binding.QuizCategoryOption3.getLayoutParams();
            layoutParams3.setMargins(0, 12, 0, 4);
        }
        // Initialize the countdown timer and start it if not running
        if (countDownTimerHelper == null) {
            countDownTimerHelper = new CountDownTimerHelper(maxCounterTimer * 1000L, 1000);
            countDownTimerHelper.setListener(this);
        }

        rbDefaultColorTxt = binding.QuizCategoryOption2.getTextColors();

        //------- fill Lists--------------
        DbAccess db = DbAccess.getInstance(requireActivity());
        db.open_to_read();
        ArrayList<Category> allElementsList = new ArrayList<>(db.getAllElementsCategory(categoryType, false));
        db.close();
        Collections.shuffle(allElementsList);
        choosedElementsList = allElementsList.subList(0, Utils.maxQuestionsPerQuiz);
        qstCounterTotal = choosedElementsList.size();
//-------------------------------------------------------------begin-----------------------------------------------------------------

        SetRandomQuestions(); // Random 3 indexes including the right index --> to choose 3 Questions instance including the right Question.

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
          String theQst = choosingNativeLangQuestion(Utils.nativeLanguage);
        String mainElementQst = binding.QuizTheMainElementOfCategory.getText().toString();
        String option1 = binding.QuizCategoryOption1.getText().toString();
        String option2 = binding.QuizCategoryOption2.getText().toString();
        String option3 = binding.QuizCategoryOption3.getText().toString();

        binding.fabShareQstFriend.setOnClickListener(v -> shareQst(theQst+ " :\n" +mainElementQst+ "\n" + "A."+option1+
                "\n" + "B."+option2+ "\n" + "C."+option3 +"\n"+"\n" + "For additional questions or tests, please visit our app :"
                +"\n" + getString(R.string.url_app1)));

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

        for (int i = 0; i < binding.quizRadioGroupCategory.getChildCount(); i++) {
            RadioButton rbMaybeCorrect = (RadioButton) binding.quizRadioGroupCategory.getChildAt(i);
            String textMaybeCorrect = rbMaybeCorrect.getText().toString();
            if (textMaybeCorrect.equals(currentQuestion.getRightAnswer())) {
                rbMaybeCorrect.setTextColor(Color.GREEN);
            } else {
                rbMaybeCorrect.setTextColor(Color.RED);
            }
        }

        // the counter is reached the final Question then change the btn to finish
        if (currentQstCounter == qstCounterTotal) {
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

    private void SetRandomQuestions() {
        for (int i = 0; i < choosedElementsList.size(); i++) {
            List<Integer> randomList = new ArrayList<>();
            Random random = new Random();

            while (randomList.size() != 2) {
                int j = random.nextInt(choosedElementsList.size());
                if (!randomList.contains(j) && j != i) {
                    randomList.add(j);
                }
            }

            randomList.add(i);
            Collections.shuffle(randomList);

            String mainNativeElement = choosingTheRightMainElementLang(Utils.nativeLanguage, i); // here change the Element language
            String rbOption1 = choosedElementsList.get(randomList.get(0)).getCategoryEng();
            String rbOption2 = choosedElementsList.get(randomList.get(1)).getCategoryEng();
            String rbOption3 = choosedElementsList.get(randomList.get(2)).getCategoryEng();
            String rightAnswer = choosedElementsList.get(i).getCategoryEng();

            questionsList.add(new Question(mainNativeElement, rbOption1, rbOption2, rbOption3, rightAnswer));
        }
    }


    private String choosingNativeLangQuestion(String nativeLanguage) {
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
                return choosedElementsList.get(currentIndex).getCategoryAr();

            case Constants.LANGUAGE_NATIVE_SPANISH:
                return choosedElementsList.get(currentIndex).getCategorySp();

            default:
                return choosedElementsList.get(currentIndex).getCategoryFr();
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
        int idCheckedRadio = binding.quizRadioGroupCategory.getCheckedRadioButtonId();
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
        if (currentQstCounter < qstCounterTotal) {

            countDownTimerHelper.start(); //Start the timer
            resetUIForNextQuestion(); // Reset UI components for the next question
            currentQuestion = questionsList.get(currentQstCounter);
            updateUIWithQuestion(currentQuestion);
            currentQstCounter++;

            // Update the TextView with the initial time (15 seconds)
            binding.tvCounterDownTimer.setText(String.valueOf(maxCounterTimer / 1000));
        } else {
            if (countDownTimerHelper != null) countDownTimerHelper.stop();
            finishQuiz();
        }
    }

    private void resetUIForNextQuestion() {
        isAnswered = false;
        binding.progressBarTimer.setProgress(0);
        binding.btnConfirmNextCategory.setText("Confirm");   // changed R.string.confirm_text
        binding.QuizCategoryOption1.setTextColor(rbDefaultColorTxt);
        binding.QuizCategoryOption2.setTextColor(rbDefaultColorTxt);
        binding.QuizCategoryOption3.setTextColor(rbDefaultColorTxt);
        binding.quizRadioGroupCategory.clearCheck();
    }

    private void updateUIWithQuestion(Question question) {
        binding.QuizTheMainElementOfCategory.setText(question.getTheMainElement());
        binding.QuizCategoryOption1.setText(question.getOption0());
        binding.QuizCategoryOption2.setText(question.getOption1());
        binding.QuizCategoryOption3.setText(question.getOption2());
        binding.tvQuizCurrentCounterCategory.setText((currentQstCounter + 1) + "/" + qstCounterTotal);
    }

    private void checkAnswer() {
        int idCheckedRadio = binding.quizRadioGroupCategory.getCheckedRadioButtonId();
        if (idCheckedRadio != -1) {
            isAnswered = true;
            binding.btnConfirmNextCategory.setText("Next");    //changed R.string.next_text
            RadioButton rbSelected = getView().findViewById(idCheckedRadio);
            String yourAnswer = rbSelected.getText().toString();

            if (yourAnswer.equals(currentQuestion.getRightAnswer())) {
                handleCorrectAnswer(rbSelected);
            } else {
                handleIncorrectAnswer();
            }

            // Pause the timer when checking the answer
            countDownTimerHelper.pause();

            if (currentQstCounter == qstCounterTotal) {
                binding.btnConfirmNextCategory.setText("Finish");     // changed R.string.finish_text

            }
        } else {
            handleNoAnswerSelected();
        }
    }

    private void handleCorrectAnswer(RadioButton rbSelected) {
        soundManager.playSound(requireActivity(),R.raw.coins1);
        userRightScore++;
        binding.tvQuizUserRightAnswerCounter.setText(String.valueOf(userRightScore));

        int randomIndex = new Random().nextInt(Utils.phrasesCorrectAnswers.size());
        String text = Utils.phrasesCorrectAnswers.get(randomIndex);
        speakEnglish(text);

        rbSelected.setTextColor(Color.GREEN);  // Only set the selected radio button's text color to green

    }


    private void handleIncorrectAnswer() {
        soundManager.playSound(requireActivity(),R.raw.error0);
        int randomIndex = new Random().nextInt(Utils.phrasesIncorrectAnswers.size());
        String text = Utils.phrasesIncorrectAnswers.get(randomIndex);
        speakEnglish(text);
        userWrongScore++;
        binding.tvQuizUserWrongAnswerCounter.setText(String.valueOf(userWrongScore));

        setRadioButtonTextColor(binding.quizRadioGroupCategory, Color.RED);


        setCorrectAnswerRadioButtonColor(binding.quizRadioGroupCategory, currentQuestion.getRightAnswer());

    }

    private void handleNoAnswerSelected() {
        isAnswered = false;
        String text = "Please Answer the Question first";
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager.speak(text);
        }
    }

    private void setRadioButtonTextColor(RadioGroup radioGroup, int color) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setTextColor(color);
        }
    }

    private void setCorrectAnswerRadioButtonColor(RadioGroup radioGroup, String correctAnswer) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.getText().toString().equals(correctAnswer)) {
                radioButton.setTextColor(Color.GREEN);
                break;
            }
        }
    }

    private void finishQuiz() {
        //  here you can finish the quiz with dialog and set scores...etc        counter 6 = max 6
        isAnswered = true;
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

        float result = userScore;
        String msg = "";

        if(result == qstCounterTotal) {
            // result 20
            pointsAdded = 10;
            elementsAdded = 3;
            msg = "Perfection Achieved!";

        } else if (result >= 0.75f * qstCounterTotal) {
            // result >=15
            pointsAdded = 5;
            elementsAdded = 2;
            msg = "Excellent";
        } else if (result >= 0.5f * qstCounterTotal) {
            // esult >=10
            pointsAdded = 4;
            elementsAdded = 1;
            msg = "Average";
        } else if(result >= 0.25f * qstCounterTotal){
            // result >= 5
            pointsAdded = 1;
            elementsAdded = 0;
            msg = "Below Average";

        } else {
            // result <5
            pointsAdded = 0;
            elementsAdded = 0;
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