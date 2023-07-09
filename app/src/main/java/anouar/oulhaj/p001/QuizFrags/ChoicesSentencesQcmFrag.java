package anouar.oulhaj.p001.QuizFrags;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Sentence;
import anouar.oulhaj.p001.Question;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class ChoicesSentencesQcmFrag extends Fragment {

    public static final String CATEGORY_SENTENCE = "category";
    String category_sentence;
    private setOnChoicesFragClickListener listener;


    private TextView tv_qst, tv_score, tv_qstCount, tv_ofTheSentences, tv_notes;
    private RadioGroup radioGroup_choices;
    private RadioButton rb0, rb1, rb2;
    private Button btn_confirmNext;

    private ColorStateList rb_defaultColor_txt;

    private List<Question> qstsList = new ArrayList<>();
    private List<Sentence> AllSentences = new ArrayList<>();
    private int qst_counter, qstCounterTotal;
    private int score;

    private boolean isAnswered;

    Question currentQuestion;
    private AutoCompleteTextView autoTxt_quiz;

    public ChoicesSentencesQcmFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            category_sentence = bundle.getString(CATEGORY_SENTENCE);
        }
    }

    public static ChoicesSentencesQcmFrag newInstance(String category_sentence){
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_SENTENCE,category_sentence);
        ChoicesSentencesQcmFrag frag = new ChoicesSentencesQcmFrag();
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choices_sentenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_qst = view.findViewById(R.id.choicesSentencesFrag_TVQst);
        tv_score = view.findViewById(R.id.choicesSentencesFrag_tv_score);
        tv_qstCount = view.findViewById(R.id.choicesSentencesFrag_tv_qstNumber);
        tv_ofTheSentences = view.findViewById(R.id.choicesSentencesFrag_theVerbQst);
        tv_notes = view.findViewById(R.id.choicesSentencesFrag_tv_notes);
        btn_confirmNext = view.findViewById(R.id.choicesSentencesFrag_Btn_confirmNext);
        radioGroup_choices = view.findViewById(R.id.choicesSentencesFrag_radioGroup);
        rb0 = view.findViewById(R.id.choicesSentencesFrag_option0);
        rb1 = view.findViewById(R.id.choicesSentencesFrag_option1);
        rb2 = view.findViewById(R.id.choicesSentencesFrag_option2);
        autoTxt_quiz = view.findViewById(R.id.autoTxt_quiz);

        rb_defaultColor_txt = rb1.getTextColors();
        autoTxt_quiz.setText(category_sentence);
        ArrayAdapter auto_Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categoryOfSentence));
        autoTxt_quiz.setAdapter(auto_Adapter);

        //------- fill Lists--------------
        DbAccess db = DbAccess.getInstance(getActivity());
        db.open_to_read();

        ArrayList<Sentence> sentences0 = db.getAllSentences();;
        db.close();
        Utils.FillData();

        switch (autoTxt_quiz.getText().toString()){
            case "Category 0" :
                AllSentences.clear();
                AllSentences = sentences0;
                break;
            case "Category 1" :
                AllSentences.clear();
                AllSentences = Utils.sentences1;
                break;
            case "Category 2" :
                AllSentences.clear();
                AllSentences = Utils.sentences2;
                break;
        }


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
            tv_ofTheSentences.setText(AllSentences.get(qst_counter).getSentence_fr());

            qst_counter++;

            tv_qstCount.setText("Qst " + qst_counter + "/" + qstCounterTotal);

            isAnswered = false;
            btn_confirmNext.setText("Confirm");

        }
        else{
            isAnswered = false;
            listener.setScoreClick(0,score,0);

        }
    }
    private void SetRandomInts(){
        for (int i = 0; i < AllSentences.size(); i++) {
            List<Integer> randomList = new ArrayList<>();
            Random random = new Random();

            while(randomList.size() != 2) {
                int j = random.nextInt(AllSentences.size());
                if(!randomList.contains(j) && j != i){
                    randomList.add(j);
                }
            }

            randomList.add(i);
            Collections.shuffle(randomList);

            String qst = getString(R.string.qstSentenceIntro);
            String rb0_str = AllSentences.get(randomList.get(0)).getSentence_eng();
            String rb1_str = AllSentences.get(randomList.get(1)).getSentence_eng();
            String rb2_str = AllSentences.get(randomList.get(2)).getSentence_eng();
            String right_Answer = AllSentences.get(i).getSentence_eng();

            qstsList.add(new Question(qst, rb0_str, rb1_str, rb2_str, right_Answer));
        }
    }
    //------------------Listener-----------------------------------
    public interface setOnChoicesFragClickListener {
        void setScoreClick(int s1,int score,int s3);
        void reLoadFragment(String category);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof setOnChoicesFragClickListener){
            listener = (setOnChoicesFragClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}