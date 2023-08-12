package anouar.oulhaj.p001.QuizFrags;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import anouar.oulhaj.p001.DB.Category;
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.EnumCategory;
import anouar.oulhaj.p001.Language;
import anouar.oulhaj.p001.Question;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class QuizCategoriesFragment extends Fragment {

    private QuizCategoryClickListener listener;
    public static String TAG_CATEGORY_TYPE = "category";
    private String categoryType;

    private TextView QuizTheQuestionOfCategory, tvScore, tvQstCounter, QuizTheMainElementOfCategory, tvNotes;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;
    private Button btnConfirmNextFinish;

    private ColorStateList rbDefaultColorTxt;

    private List<Question> questionsList = new ArrayList<>();
    private List<Category> chosenListOfElements = new ArrayList<>();
    private int currentQstCounter, qstCounterTotal;
    private int userScore = 0;

    private boolean isAnswered;

    Question currentQuestion;


    public QuizCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            categoryType = bundle.getString(TAG_CATEGORY_TYPE,"VERB");
        }
    }

    public static QuizCategoriesFragment getInstance(String categoryType) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG_CATEGORY_TYPE,categoryType);
        QuizCategoriesFragment fragment = new QuizCategoriesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        QuizTheQuestionOfCategory = view.findViewById(R.id.QuizTheQuestionOfCategory);
        QuizTheMainElementOfCategory = view.findViewById(R.id.QuizTheMainElementOfCategory);
        tvScore = view.findViewById(R.id.tvQuizUserScoreCategory);
        tvQstCounter = view.findViewById(R.id.tvQuizCurrentCounterCategory);
        btnConfirmNextFinish = view.findViewById(R.id.btnConfirmNextCategory);
        tvNotes = view.findViewById(R.id.quizTvNotesCategory);
        // Initialize radio buttons
        radioGroup = view.findViewById(R.id.quizRadioGroupCategory);
        rb1 = view.findViewById(R.id.QuizCategoryOption1);
        rb2 = view.findViewById(R.id.QuizCategoryOption2);
        rb3 = view.findViewById(R.id.QuizCategoryOption3);

        QuizTheQuestionOfCategory.setText(getResources().getString(R.string.TheQstInFrench));

        rbDefaultColorTxt = rb2.getTextColors();
        //------- fill Lists--------------
        DbAccess db = DbAccess.getInstance(requireActivity());
        db.open_to_read();
        ArrayList<Category> allLitOfElements = new ArrayList<>(db.getAllElementsOfCategory(categoryType,false));
        db.close();
       Collections.shuffle(allLitOfElements);
        chosenListOfElements = allLitOfElements.subList(0,10);
        qstCounterTotal = chosenListOfElements.size();
        //--------------ChoosingMainQstLanguages------------------
        Utils.txtOftheMainQuestioninNative = choosingLanguageOfMainQuestion();
        //-----------------------------------------------------


        SetRandomQuestions();
        qstCounterTotal = chosenListOfElements.size();
        showNextQst();

        btnConfirmNextFinish.setOnClickListener(v -> {
            if (!isAnswered) {
                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                    checkAnswer();
                } else {
                    Toast.makeText(getActivity(), "Please Answer the Qst", Toast.LENGTH_SHORT).show();
                }
            } else {
                showNextQst();
            }
        });
    }

    private void checkAnswer() {
        isAnswered = true;
        RadioButton rbSelected = getView().findViewById(radioGroup.getCheckedRadioButtonId());
        String yourAnswer = rbSelected.getText().toString();

        if (yourAnswer.equals(currentQuestion.getRightAnswer())) {
            userScore++;
            tvScore.setText("Score: " + userScore);
            rbSelected.setTextColor(Color.GREEN);
            tvNotes.setText("Yes, your answer is Right : " + currentQuestion.getRightAnswer());
            tvNotes.setTextColor(Color.GREEN);
        }
        else {
            rbSelected.setTextColor(Color.RED);
            tvNotes.setText("Nooo, your answer is wrong, the right Answer : " + currentQuestion.getRightAnswer());
            tvNotes.setTextColor(Color.RED);
        }

        showSolution(currentQuestion.getRightAnswer());
        if (yourAnswer.equals(currentQuestion.getRightAnswer())) {
            rbSelected.setTextColor(Color.GREEN);
        } else {
            rbSelected.setTextColor(Color.RED);
        }
        if(currentQstCounter == qstCounterTotal) {
            btnConfirmNextFinish.setText("Finish");
            btnConfirmNextFinish.setTextColor(Color.GREEN);
            btnConfirmNextFinish.setBackgroundTintList(rbDefaultColorTxt);
        }

    }

    private void showSolution(String rightAnswer) {

        String rb1_text = rb1.getText().toString();
        String rb2_text = rb2.getText().toString();
        String rb3_text = rb3.getText().toString();

        if (rb1_text.equals(rightAnswer)) {
            rb1.setTextColor(Color.GREEN);
        } else if (rb2_text.equals(rightAnswer)) {
            rb2.setTextColor(Color.GREEN);

        } else if (rb3_text.equals(rightAnswer)) {
            rb3.setTextColor(Color.GREEN);
        }

        btnConfirmNextFinish.setText("Next");
    }

    private void showNextQst() {
        rb1.setTextColor(rbDefaultColorTxt);
        rb2.setTextColor(rbDefaultColorTxt);
        rb3.setTextColor(rbDefaultColorTxt);
        radioGroup.clearCheck();
        tvNotes.setText("");


        if (currentQstCounter < qstCounterTotal) {
            currentQuestion = questionsList.get(currentQstCounter);

            QuizTheMainElementOfCategory.setText(choosingLanguageOfMainQuestion());
            rb1.setText(currentQuestion.getOption0());
            rb2.setText(currentQuestion.getOption1());
            rb3.setText(currentQuestion.getOption2());

            //----set tv_theVerbFR--------
            QuizTheMainElementOfCategory.setText(chosenListOfElements.get(currentQstCounter).getCategoryFr());

            currentQstCounter++;

            tvQstCounter.setText("Qst " + currentQstCounter + "/" + qstCounterTotal);

            isAnswered = false;
            btnConfirmNextFinish.setText("Confirm");

        }
        else{
            isAnswered = false;
            setScoreToTheRightCategory(userScore);
            Float result = (userScore /12f) * 100f;
            tvNotes.setText("Your result : " + result);

        }
    }
    private void setScoreToTheRightCategory(int userScore){
        switch(categoryType)
        {
            case "VERB":
                listener.setScoreOnClick(userScore,0,0 ,0,0,0,0, EnumCategory.VERB.name());

                break;
            case "SENTENCE":
                listener.setScoreOnClick(0,userScore,0 ,0,0,0,0 , EnumCategory.SENTENCE.name());

                break;
            case "PHRASAL":
                listener.setScoreOnClick(0,0,userScore ,0,0,0,0,EnumCategory.PHRASAL.name());

                break;
            case "NOUN":
                listener.setScoreOnClick(0,0,0 ,userScore,0,0,0,EnumCategory.NOUN.name());

                break;
            case "ADJECTIVE":
                listener.setScoreOnClick(0,0,0 ,0,userScore,0,0,EnumCategory.ADJECTIVE.name());

                break;
            case "ADVERB":
                listener.setScoreOnClick(0,0,0 ,0,0,userScore,0,EnumCategory.ADVERB.name());

                break;
            case "IDIOM":
                listener.setScoreOnClick(0,0,0 ,0,0,0,userScore,EnumCategory.IDIOM.name());

                break;
        }
    }
    private void SetRandomQuestions(){
        for (int i = 0; i < chosenListOfElements.size(); i++) {
            List<Integer> randomList = new ArrayList<>();
            Random random = new Random();

            while(randomList.size() != 2) {
                int j = random.nextInt(chosenListOfElements.size());
                if(!randomList.contains(j) && j != i){
                    randomList.add(j);
                }
            }

            randomList.add(i);
            Collections.shuffle(randomList);

            String qst = getString(R.string.qstSentenceIntro);
            String rb0_str = chosenListOfElements.get(randomList.get(0)).getCategoryEng();
            String rb1_str = chosenListOfElements.get(randomList.get(1)).getCategoryEng();
            String rb2_str = chosenListOfElements.get(randomList.get(2)).getCategoryEng();
            String right_Answer = chosenListOfElements.get(i).getCategoryEng();

            questionsList.add(new Question(qst, rb0_str, rb1_str, rb2_str, right_Answer));
        }
    }



    private String choosingLanguageOfMainQuestion() {
        String txtQst = "";
        if (Utils.language == Language.SPANISH) {
            txtQst = getResources().getString(R.string.TheQstInSpanish);
        } else if (Utils.language == Language.ARABIC) {
            txtQst = getResources().getString(R.string.TheQstInArabic);
        } else {
            txtQst = getResources().getString(R.string.TheQstInFrench);
        }
        return txtQst;
    }


    //-------------------------------Listener-------------------------------------------------------
    public interface QuizCategoryClickListener {
        void setScoreOnClick(int verbScore, int sentenceScore, int phrasalScore , int nounScore , int adjScore , int advScore , int idiomScore ,String categoryType);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof QuizCategoryClickListener) {
            listener = (QuizCategoryClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}