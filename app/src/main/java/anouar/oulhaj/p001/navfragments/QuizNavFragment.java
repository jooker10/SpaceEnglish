package anouar.oulhaj.p001.navfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.R;


public class QuizNavFragment extends Fragment {

    private List<Verb> verbs = new ArrayList<>();
    private TextView tv_verb_name;
    private TextView tv_maybe;
    private EditText et_answer;
    private Button btn_submit;
    private Button btn_skip;
    private FloatingActionButton btn_maybe;
    private int current_index;

    public QuizNavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //_________inflate views-----------------
        tv_verb_name = view.findViewById(R.id.choicesFrag_theVerbQst);
        tv_maybe = view.findViewById(R.id.txt_maybe);
        et_answer = view.findViewById(R.id.fragQuizEtAnswer);
        btn_submit = view.findViewById(R.id.choicesFrag_Btn_confirmNext);
        btn_skip = view.findViewById(R.id.fragQuizBtn_skip);
        btn_maybe = view.findViewById(R.id.fragQuizBtn_maybe);
        //------ nchofo ki ndiro lhadchi?? -------
        initializeListOfVerbs();
        fillRandomlyDataVerb();

        btn_skip.setOnClickListener(v -> {
            fillRandomlyDataVerb();
        });

        btn_submit.setOnClickListener(v -> {
            String your_answer = et_answer.getText().toString().trim().toLowerCase();
            String rightAnswer = verbs.get(current_index).getVerb_eng().trim().toLowerCase();
            if (your_answer.equals(rightAnswer)) {
                Toast.makeText(getActivity(), "Good", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Wrong Answer", Toast.LENGTH_SHORT).show();
            }
        });

        btn_maybe.setOnClickListener(v -> {

            List<String> maybeAnswers = new ArrayList<>();
            Random randomInt = new Random();
            String rightAnswer = verbs.get(current_index).getVerb_eng();
            maybeAnswers.add(rightAnswer);

            while (maybeAnswers.size() < verbs.size()) {
                int index = randomInt.nextInt(verbs.size());
                if (index != current_index) {
                    String Answer = verbs.get(index).getVerb_eng();
                    maybeAnswers.add(Answer);
                }
            }

            String maybe = "The Answer maybe \n " + maybeAnswers.get(randomInt.nextInt(maybeAnswers.size())) + "\n" + maybeAnswers.get(randomInt.nextInt(maybeAnswers.size())) + "\n" + maybeAnswers.get(randomInt.nextInt(maybeAnswers.size())) + "\n" + maybeAnswers.get(randomInt.nextInt(maybeAnswers.size()));

            tv_maybe.setText(maybe);

        });


    }

    private void initializeListOfVerbs() {
        DbAccess db = DbAccess.getInstance(getActivity());
        db.open_to_read();
        verbs = db.getAllVerbs();
        db.close();
    }

    private void fillRandomlyDataVerb() {
        et_answer.setText("");
        Random random = new Random();
        int i = random.nextInt(verbs.size());
        current_index = i;
        tv_verb_name.setText(verbs.get(current_index).getVerb_fr());
    }
}