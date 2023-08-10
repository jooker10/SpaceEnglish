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

import anouar.oulhaj.p001.DB.Noun;
import anouar.oulhaj.p001.Question;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class ChoicesNounsQcmFrag extends Fragment {

    private OnChoicesFragClickListener listener;

    private TextView tv_qst, tv_score, tv_qstCount, tv_ofTheVerb, tv_notes;
    private RadioGroup radioGroup_choices;
    private RadioButton rb0, rb1, rb2;
    private Button btn_confirmNext;

    private ColorStateList rb_defaultColor_txt;

    private List<Question> qstsList = new ArrayList<>();
    private List<Noun> allNouns = new ArrayList<>();
    private int qst_counter, qstCounterTotal;
    private int score;

    private boolean isAnswered;

    Question currentQuestion;


    public ChoicesNounsQcmFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choices_nouns, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_qst = view.findViewById(R.id.the_question_of_noun);
        tv_score = view.findViewById(R.id.quiz_tv_score_nouns);
        tv_qstCount = view.findViewById(R.id.quiz_tv_nouns_qstNumber);
        tv_ofTheVerb = view.findViewById(R.id.quiz_TheNoun_qst);
        tv_notes = view.findViewById(R.id.choicesFrag_tv_notes_nouns);

        btn_confirmNext = view.findViewById(R.id.quizFrag_Btn_confirm_next_nouns);

        radioGroup_choices = view.findViewById(R.id.choices_radioGroup_nouns);
        rb0 = view.findViewById(R.id.choicesFrag_nouns_option0);
        rb1 = view.findViewById(R.id.choicesFrag_nouns_option1);
        rb2 = view.findViewById(R.id.choicesFrag_nouns_option2);


        rb_defaultColor_txt = rb1.getTextColors();
        //------- fill Lists--------------
        allNouns = Utils.nounsList;

 /*       DbAccess db = DbAccess.getInstance(getActivity());
        db.open_to_read();
        AllVerbs = db.getAllVerbs();
        db.close();*/

        SetRandomInts();
        qstCounterTotal = qstsList.size();
        showNextQst();

        btn_confirmNext.setOnClickListener(v -> {
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
            score++;
            tv_score.setText("Score: " + score);
            rbSelected.setTextColor(Color.GREEN);
        }
        else {
            rbSelected.setTextColor(Color.RED);
        }

        showSolution(currentQuestion.getRightAnswer());
        if (yourAnswer.equals(currentQuestion.getRightAnswer())) {
            rbSelected.setTextColor(Color.GREEN);
        } else {
            rbSelected.setTextColor(Color.RED);
        }
        if(qst_counter == qstCounterTotal) {
            btn_confirmNext.setText("Finish");
            btn_confirmNext.setTextColor(Color.GREEN);
            btn_confirmNext.setBackgroundTintList(rb_defaultColor_txt);
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


        tv_notes.setText("The right Answer is " + currentQuestion.getRightAnswer());
        btn_confirmNext.setText("Next");
    }

    private void showNextQst() {
        rb0.setTextColor(rb_defaultColor_txt);
        rb1.setTextColor(rb_defaultColor_txt);
        rb2.setTextColor(rb_defaultColor_txt);
        radioGroup_choices.clearCheck();
        tv_notes.setText("");


        if (qst_counter < qstCounterTotal) {
            currentQuestion = qstsList.get(qst_counter);

            tv_qst.setText(currentQuestion.getTheQst());
            rb0.setText(currentQuestion.getOption0());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());

            //----set tv_theVerbFR--------
            tv_ofTheVerb.setText(allNouns.get(qst_counter).getNoun_fr());

            qst_counter++;

            tv_qstCount.setText("Qst " + qst_counter + "/" + qstCounterTotal);

            isAnswered = false;
            btn_confirmNext.setText("Confirm");

        }
        else{
            isAnswered = false;
           listener.setScoreClick(score,0,0);

        }
    }

    //------Random fitch Verbs-------
    private void SetRandomInts(){
        for (int i = 0; i < allNouns.size(); i++) {
            List<Integer> randomInts = new ArrayList<>();
            Random random = new Random();

            while(randomInts.size() != 2) {
                int j = random.nextInt(allNouns.size());
                if(!randomInts.contains(j) && j != i){
                    randomInts.add(j);
                }
            }
            randomInts.add(i);
            Collections.shuffle(randomInts);

            String qst = getString(R.string.qstVerbIntro);
            String rb0_str = allNouns.get(randomInts.get(0)).getGetNoun_eng();
            String rb1_str = allNouns.get(randomInts.get(1)).getGetNoun_eng();
            String rb2_str = allNouns.get(randomInts.get(2)).getGetNoun_eng();
            String right_Answer = allNouns.get(i).getGetNoun_eng();

            qstsList.add(new Question(qst, rb0_str, rb1_str, rb2_str, right_Answer));
        }
    }


//-------------------------------Listener-------------------------------------------------------
    public interface OnChoicesFragClickListener {
        void setScoreClick(int verbScore,int s1,int s2);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnChoicesFragClickListener){
            listener = (OnChoicesFragClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}