package anouar.oulhaj.p001.QuizFrags;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001._Main.Scores;
import anouar.oulhaj.p001._Main.Utils;


public class InfoQuizFragment extends Fragment {

    private Button btnVerbs;
    private Button btnSentences;
    private Button btnPhrasals;
    private Button btnNouns;
    private Button btnAdjs;
    private Button btnAdvs;
    private Button btnIdioms;
    private AutoCompleteTextView autoCompleteTvMaxQuestions;

    private OnsetFragmentToReplaceClickListener listener;


    public InfoQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnsetFragmentToReplaceClickListener){
            listener = (OnsetFragmentToReplaceClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_quiz, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnVerbs = view.findViewById(R.id.btnVerbs);
        btnSentences = view.findViewById(R.id.btnSentences);
        btnPhrasals = view.findViewById(R.id.btnPhrasals);
        btnNouns = view.findViewById(R.id.btnNouns);
        btnAdjs = view.findViewById(R.id.btnAdjs);
        btnAdvs = view.findViewById(R.id.btnAdvs);
        btnIdioms = view.findViewById(R.id.btnIdioms);
        autoCompleteTvMaxQuestions = view.findViewById(R.id.autoTxt);

        if(!autoCompleteTvMaxQuestions.getText().toString().isEmpty()) {
            Utils.maxQuestionsPerQuiz = Integer.parseInt(autoCompleteTvMaxQuestions.getText().toString());
        } else {
            Utils.maxQuestionsPerQuiz = 20;
        }

        String[] maxQuestionsList = getResources().getStringArray(R.array.max_questions_per_quiz);
        ArrayAdapter<String> adapterAutoCompleteTv = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1,maxQuestionsList);
        autoCompleteTvMaxQuestions.setAdapter(adapterAutoCompleteTv);

        autoCompleteTvMaxQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String txtSelected = autoCompleteTvMaxQuestions.getText().toString();
                Toast.makeText(requireActivity(), autoCompleteTvMaxQuestions.getText().toString(), Toast.LENGTH_SHORT).show();
                Utils.maxQuestionsPerQuiz = Integer.parseInt(txtSelected);
            }
        });

        // permission buttons the enabled true
        enableButton(btnPhrasals, Scores.totalScore,Constants.permissionPhrasalScore);
        enableButton(btnNouns, Scores.totalScore,Constants.permissionNounScore);
        enableButton(btnAdjs, Scores.totalScore,Constants.permissionAdjScore);
        enableButton(btnAdvs, Scores.totalScore,Constants.permissionAdvScore);
        enableButton(btnIdioms, Scores.totalScore,Constants.permissionIdiomScore);

        btnVerbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplaceInfoToQuiz(QuizCategoriesFragment.getInstance(Constants.VERB_NAME));
            }
        });
        btnVerbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplaceInfoToQuiz(QuizCategoriesFragment.getInstance(Constants.VERB_NAME));
            }
        });
        btnSentences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplaceInfoToQuiz(QuizCategoriesFragment.getInstance(Constants.SENTENCE_NAME));
            }
        });
        btnPhrasals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplaceInfoToQuiz(QuizCategoriesFragment.getInstance(Constants.PHRASAL_NAME));
            }
        });
        btnNouns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplaceInfoToQuiz(QuizCategoriesFragment.getInstance(Constants.NOUN_NAME));
            }
        });
        btnAdjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplaceInfoToQuiz(QuizCategoriesFragment.getInstance(Constants.ADJ_NAME));
            }
        });
        btnAdvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplaceInfoToQuiz(QuizCategoriesFragment.getInstance(Constants.ADV_NAME));
            }
        });
        btnIdioms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReplaceInfoToQuiz(QuizCategoriesFragment.getInstance(Constants.IDIOM_NAME));
            }
        });
    }


    public interface OnsetFragmentToReplaceClickListener {
        void onReplaceInfoToQuiz(Fragment fragment);
    }

    private void enableButton(Button button , int globalMainScore , int permissionScore) {
        button.setEnabled(globalMainScore >= permissionScore);
    }

}
