package anouar.oulhaj.p001;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import anouar.oulhaj.p001.DB.Verb;

public class QuizManager {

    private Context context;
    private List<Verb> allVerbs;
    private RandomIndexs randomIndexs = new RandomIndexs();
    private int currentQuestionIndex = 0;
    private RadioButton rb0, rb1, rb2;
    private TextView questionTextView;
    public QuizManager(Context context, List<Verb> allVerbs ,RadioButton rb0 ,RadioButton rb1 ,RadioButton rb2 , TextView questionTextView ) {
        this.context = context;
        this.allVerbs = allVerbs;
        this.rb0 = rb0;
        this.rb1 = rb1;
        this.rb2 = rb2;
        this.questionTextView = questionTextView;
        Collections.shuffle(allVerbs); // Shuffle the list initially
    }

    public void setNextQuestion(TextView questionTextView, RadioButton rb0, RadioButton rb1, RadioButton rb2) {
        if (currentQuestionIndex < allVerbs.size()) {
            Verb currentVerb = allVerbs.get(currentQuestionIndex);

            questionTextView.setText(currentVerb.getVerb_fr()); // Display the question
            List<Integer> randomIndexes = randomIndexs.randomIndexes(allVerbs, currentQuestionIndex);

            rb0.setText(allVerbs.get(randomIndexes.get(0)).getVerb_eng());
            rb1.setText(allVerbs.get(randomIndexes.get(1)).getVerb_eng());
            rb2.setText(allVerbs.get(randomIndexes.get(2)).getVerb_eng());

            // Store the correct answer for later comparison
            String correctAnswer = currentVerb.getVerb_eng();

            // Set up OnClickListener for radio buttons
            rb0.setOnClickListener(v -> checkAnswer(rb0.getText().toString(), correctAnswer));
            rb1.setOnClickListener(v -> checkAnswer(rb1.getText().toString(), correctAnswer));
            rb2.setOnClickListener(v -> checkAnswer(rb2.getText().toString(), correctAnswer));
        } else {
            // Quiz finished
            Toast.makeText(context, "Quiz Finished", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkAnswer(String selectedAnswer, String correctAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            // Correct answer
            // Display feedback (e.g., Toast)
            Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            // Incorrect answer
            // Display feedback (e.g., Toast)
            Toast.makeText(context, "Incorrect. The correct answer is: " + correctAnswer, Toast.LENGTH_SHORT).show();
        }

        // Move to the next question
        currentQuestionIndex++;
        setNextQuestion(questionTextView, rb0, rb1, rb2);
    }
}
