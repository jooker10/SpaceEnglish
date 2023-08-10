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
import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.Language;
import anouar.oulhaj.p001.Question;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.RandomIndexs;
import anouar.oulhaj.p001.Utils;


public class VerbsQuizFragment extends Fragment {

    private OnChoicesFragClickListener listener;

    private TextView tvMainQstInNativeLanguage, tvScore, tvQstCounter, tvMainElementOFQstInNative, tvNotes;
    private RadioGroup radioGroup_choices;
    private RadioButton rb0, rb1, rb2;
    private Button btnConfirmNextFinish;

    private ColorStateList rbDefaultColorTxt;

    private List<Question> questionsList = new ArrayList<>();
    private List<Verb> allListOfVerbs = new ArrayList<>();
    private int currentQstCounter, qstCounterTotal;
    private int yourCurrentScore;

    private boolean isAnswered;

    Question currentQuestion;


    public VerbsQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choices_idioms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvMainQstInNativeLanguage = view.findViewById(R.id.the_question_of_verb);
        tvMainElementOFQstInNative = view.findViewById(R.id.quiz_TheVerb_qst);
        tvScore = view.findViewById(R.id.quiz_tv_score_verbs);
        tvQstCounter = view.findViewById(R.id.quiz_tv_verbs_qstNumber);

        radioGroup_choices = view.findViewById(R.id.radioGroup_quiz_verbs);
        rb0 = view.findViewById(R.id.choicesFrag_verbs_option0);
        rb1 = view.findViewById(R.id.choicesFrag_verbs_option1);
        rb2 = view.findViewById(R.id.choicesFrag_verbs_option2);

        btnConfirmNextFinish = view.findViewById(R.id.quizFrag_Btn_confirm_next_verbs);
        tvNotes = view.findViewById(R.id.choicesFrag_tv_notes_verbs);

        rbDefaultColorTxt = rb1.getTextColors();
        //------- fill Lists--------------
        allListOfVerbs = Utils.verbsList;
        qstCounterTotal = allListOfVerbs.size();
        //--------------ChoosingMainQstLanguages------------------
        Utils.txtOftheMainQuestioninNative = choosingLanguageOfMainQuestion();
        //-----------------------------------------------------

        // SetRandomInts();
          MySetRandomQsts(allListOfVerbs);
        //setRandomQuestions(allListOfVerbs);
       // showNextQst();

        btnConfirmNextFinish.setOnClickListener(v -> {
            if (!isAnswered) {
                if (rb0.isChecked() || rb1.isChecked() || rb2.isChecked()) {
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
        RadioButton rbSelected = getView().findViewById(radioGroup_choices.getCheckedRadioButtonId());
        String yourAnswer = rbSelected.getText().toString();

        if (yourAnswer.equals(currentQuestion.getRightAnswer())) {
            yourCurrentScore++;
            tvScore.setText("Score: " + yourCurrentScore);
            rbSelected.setTextColor(Color.GREEN);
        } else {
            rbSelected.setTextColor(Color.RED);
        }

        showSolution(currentQuestion.getRightAnswer());
        if (yourAnswer.equals(currentQuestion.getRightAnswer())) {
            rbSelected.setTextColor(Color.GREEN);
        } else {
            rbSelected.setTextColor(Color.RED);
        }
        if (currentQstCounter == qstCounterTotal) {
            btnConfirmNextFinish.setText("Finish");
            btnConfirmNextFinish.setTextColor(Color.GREEN);
            btnConfirmNextFinish.setBackgroundTintList(rbDefaultColorTxt);
        }

    }

    private void showSolution(String rightAnswer) {

        String rb0_text = rb0.getText().toString();
        String rb1_text = rb0.getText().toString();
        String rb2_text = rb0.getText().toString();

        if (rb0_text.equals(rightAnswer)) {
            rb0.setTextColor(Color.GREEN);
        } else if (rb1_text.equals(rightAnswer)) {
            rb1.setTextColor(Color.GREEN);

        } else if (rb2_text.equals(rightAnswer)) {
            rb2.setTextColor(Color.GREEN);
        }


        tvNotes.setText("The right Answer is " + currentQuestion.getRightAnswer());
        btnConfirmNextFinish.setText("Next");
    }

    private void showNextQst() {
        rb0.setTextColor(rbDefaultColorTxt);
        rb1.setTextColor(rbDefaultColorTxt);
        rb2.setTextColor(rbDefaultColorTxt);
        radioGroup_choices.clearCheck();
        tvNotes.setText("");


        if (currentQstCounter < qstCounterTotal) {
            currentQuestion = questionsList.get(currentQstCounter);

            tvMainQstInNativeLanguage.setText(currentQuestion.getTheQst());
            rb0.setText(currentQuestion.getOption0());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());

            //----set tv_theVerbFR--------
            tvMainElementOFQstInNative.setText(allListOfVerbs.get(currentQstCounter).getVerb_fr());

            currentQstCounter++;

            tvQstCounter.setText("Qst " + currentQstCounter + "/" + qstCounterTotal);

            isAnswered = false;
            btnConfirmNextFinish.setText("Confirm");

        } else {
            isAnswered = false;
            listener.setScoreClick(yourCurrentScore, 0, 0);

        }
    }

    //------Random fitch Verbs-------
    private void SetRandomInts() {
        Collections.shuffle(allListOfVerbs);

        for (int i = 0; i < allListOfVerbs.size(); i++) {
            List<Integer> randomIndexes = new ArrayList<>();
            Random random = new Random();

            while (randomIndexes.size() != 2) {
                int j = random.nextInt(allListOfVerbs.size());
                if (!randomIndexes.contains(j) && j != i) {
                    randomIndexes.add(j);
                }
            }
            randomIndexes.add(i);
            Collections.shuffle(randomIndexes);

            String qst = Utils.txtOftheMainQuestioninNative;
            String rb0_str = allListOfVerbs.get(randomIndexes.get(0)).getVerb_eng();
            String rb1_str = allListOfVerbs.get(randomIndexes.get(1)).getVerb_eng();
            String rb2_str = allListOfVerbs.get(randomIndexes.get(2)).getVerb_eng();
            String right_Answer = allListOfVerbs.get(i).getVerb_eng();

            questionsList.add(new Question(qst, rb0_str, rb1_str, rb2_str, right_Answer));
        }
    }

    //------Random fith verbs by chatGPT------
    private void setRandomQuestions(List<Verb> allListOfVerbs) {
        Collections.shuffle(allListOfVerbs);
        Random random = new Random();
        int listSize = allListOfVerbs.size();
        for (int i = 0; i < listSize; i++) {
            List<Integer> randomIndexes = new ArrayList<>();
            while (randomIndexes.size() < 3) {
                int randomIndex = random.nextInt(listSize);
                if (!randomIndexes.contains(randomIndex) && randomIndex != i) {
                    randomIndexes.add(randomIndex);
                }
            }
            randomIndexes.add(i);
            Collections.shuffle(randomIndexes);

            String mainQuestion = Utils.txtOftheMainQuestioninNative;
            String rb0Str = allListOfVerbs.get(randomIndexes.get(0)).getVerb_eng();
            String rb1Str = allListOfVerbs.get(randomIndexes.get(1)).getVerb_eng();
            String rb2Str = allListOfVerbs.get(randomIndexes.get(2)).getVerb_eng();
            String rightAnswer = allListOfVerbs.get(i).getVerb_eng();

            questionsList.add(new Question(mainQuestion, rb0Str, rb1Str, rb2Str, rightAnswer));
        }
    }
    //------Random fitch Verbs-------
    private void MySetRandomQsts(List<Verb> allListOfVerbs) {
        Collections.shuffle(allListOfVerbs);
        RandomIndexs chooseRandomIndexes = new RandomIndexs();

        for (int i = 0; i < allListOfVerbs.size(); i++) {

            List<Integer> randomIndexes = chooseRandomIndexes.randomIndexes(allListOfVerbs,i);


            Toast.makeText(getActivity(), ""+randomIndexes.size(), Toast.LENGTH_SHORT).show();
            String qst = Utils.txtOftheMainQuestioninNative;
            String rb0_str = allListOfVerbs.get(randomIndexes.get(0)).getVerb_eng();
            String rb1_str = allListOfVerbs.get(randomIndexes.get(1)).getVerb_eng();
            String rb2_str = allListOfVerbs.get(randomIndexes.get(2)).getVerb_eng();
            String right_Answer = allListOfVerbs.get(i).getVerb_eng();

            questionsList.add(new Question(qst, rb0_str, rb1_str, rb2_str, right_Answer));
        }
    }



    //-------------------------------Listener-------------------------------------------------------
    public interface OnChoicesFragClickListener {
        void setScoreClick(int verbScore, int s1, int s2);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnChoicesFragClickListener) {
            listener = (OnChoicesFragClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}