package anouar.oulhaj.p001.QuizFrags;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;

public class MyQuizVerbFragment extends Fragment {
    private List<Verb> verbs = new ArrayList<>(); // Your list of verbs
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioGroup radioGroup_choices;
    private Button btnConfirmNextFinish;
    private TextView tvMainQstInNativeLanguage;
    private TextView tvScore, tvQstCounter, tvMainElementOFQstInNative, tvNotes;
    private int currentQuestionIndex = 0;
    private int userScore = 0;
    private int currentQstCounter = 0;
    private int qstCounterTotal = 0;
    private boolean isAnswer = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz_verbs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize verbs list with your verb data
        verbs = Utils.verbsList;
        qstCounterTotal = verbs.size();
        // Initialize the tv question list with your verb data


        tvMainQstInNativeLanguage = view.findViewById(R.id.the_question_of_verb);
        tvMainElementOFQstInNative = view.findViewById(R.id.quiz_TheVerb_qst);
        tvScore = view.findViewById(R.id.quiz_tv_score_verbs);
        tvQstCounter = view.findViewById(R.id.quiz_tv_verbs_qstNumber);
        btnConfirmNextFinish = view.findViewById(R.id.quizFrag_Btn_confirm_next_verbs);
        tvNotes = view.findViewById(R.id.choicesFrag_tv_notes_verbs);
        // Initialize radio buttons
        rb1 = view.findViewById(R.id.choicesFrag_verbs_option0);
        rb2 = view.findViewById(R.id.choicesFrag_verbs_option1);
        rb3 = view.findViewById(R.id.choicesFrag_verbs_option2);
        radioGroup_choices = view.findViewById(R.id.radioGroup_quiz_verbs);

        tvMainQstInNativeLanguage.setText(getResources().getString(R.string.TheQstInFrench));

        //----- Btn next onClick------------
        btnConfirmNextFinish.setOnClickListener(view1 -> {
            if (isAnswer) {
                if (currentQuestionIndex < Utils.verbsList.size()) {
                    displayQuestion();
                    btnConfirmNextFinish.setText("Confirm");
                } else {
                    // Quiz is complete, show user's score
                    Toast.makeText(requireContext(), "Quiz is complete.", Toast.LENGTH_SHORT).show();
                    btnConfirmNextFinish.setText("Finish");
                }
            } else {
                checkAnswer();
            }



        });

        // Display the first question
        displayQuestion();
    }

    private void displayQuestion() {
        isAnswer = false;
        clearRadioButton();

        currentQstCounter++;
        tvQstCounter.setText(String.valueOf(currentQstCounter + "/" + qstCounterTotal));
        // Shuffle the list of verbs
        Collections.shuffle(verbs);

        // Select the first three verbs in english, including the correct one
        String correctVerb = verbs.get(0).getVerb_eng();
        String wrongVerb1 = verbs.get(1).getVerb_eng();
        String wrongVerb2 = verbs.get(2).getVerb_eng();

        //----- correct Verb in Native language  ----------
        tvMainElementOFQstInNative.setText(verbs.get(0).getVerb_fr());

        // Randomly select which radio button to assign the correct answer
        int correctButtonIndex = new Random().nextInt(3);

        // Assign verbs to radio buttons
        if (correctButtonIndex == 0) {
            rb1.setText(correctVerb);
            rb2.setText(wrongVerb1);
            rb3.setText(wrongVerb2);
        } else if (correctButtonIndex == 1) {
            rb1.setText(wrongVerb1);
            rb2.setText(correctVerb);
            rb3.setText(wrongVerb2);
        } else {
            rb1.setText(wrongVerb2);
            rb2.setText(wrongVerb1);
            rb3.setText(correctVerb);
        }
    }

    private void checkAnswer() {

        int CheckedIndex = radioGroup_choices.getCheckedRadioButtonId();

        if (CheckedIndex == -1) {
            Toast.makeText(requireContext(), "Please check an answer", Toast.LENGTH_SHORT).show();
        } else {
            isAnswer = true;
            btnConfirmNextFinish.setText("Next");
            RadioButton selectedRadioButton = getView().findViewById(CheckedIndex);


            //-- the right answer checked--
            if (selectedRadioButton.getText().equals(verbs.get(0).getVerb_eng())) {
                userScore++;
                tvScore.setText(String.valueOf("Score : " + userScore));
                selectedRadioButton.setTextColor(Color.GREEN);
                tvNotes.setText("Right Answer : " + verbs.get(0).getVerb_eng());
                tvNotes.setTextColor(Color.GREEN);
            }
            //--- the wrong answer checked-------
            else {
                selectedRadioButton.setTextColor(Color.RED);
                tvNotes.setText("Wrong answer , and the right answer is " + verbs.get(0).getVerb_eng());
                tvNotes.setTextColor(Color.RED);
            }

            // Move to the next question
            currentQuestionIndex++;

        }

    }

    private void clearRadioButton() {
        RadioGroup radioGroup = requireView().findViewById(R.id.radioGroup_quiz_verbs);
        radioGroup.clearCheck();

        rb1.setTextColor(ContextCompat.getColor(requireContext(), R.color.txt_gray_800_100));
        rb2.setTextColor(ContextCompat.getColor(requireContext(), R.color.txt_gray_800_100));
        rb3.setTextColor(ContextCompat.getColor(requireContext(), R.color.txt_gray_800_100));

        tvNotes.setText("");
    }
}
