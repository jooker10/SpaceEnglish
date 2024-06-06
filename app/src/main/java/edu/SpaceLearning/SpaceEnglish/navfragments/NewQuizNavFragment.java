package edu.SpaceLearning.SpaceEnglish.navfragments;

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
import android.widget.TextView;
import android.widget.Toast;

import edu.SpaceLearning.SpaceEnglish.OnFragmentNavigationListener;
import edu.SpaceLearning.SpaceEnglish.QuizFrags.QuizCategoriesFragment;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish._Main.Scores;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentNavNewQuizBinding;


public class NewQuizNavFragment extends Fragment  {

    private FragmentNavNewQuizBinding binding;
    private OnsetFragmentToReplaceClickListener replaceFragListener;
    private OnFragmentNavigationListener navigationListener;


    public NewQuizNavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnsetFragmentToReplaceClickListener){
            replaceFragListener = (OnsetFragmentToReplaceClickListener) context;
        }
        if(context instanceof OnFragmentNavigationListener) {
            navigationListener = (OnFragmentNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        replaceFragListener = null;
        navigationListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_new_quiz, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         binding = FragmentNavNewQuizBinding.bind(view);

        Utils.fragmentNameTagSearch = "Quiz";
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }

        binding.autoTvmaxQuestionsPerQuiz.setText(String.valueOf(Utils.maxQuestionsPerQuiz));

        if(!binding.autoTvmaxQuestionsPerQuiz.getText().toString().isEmpty()) {
            Utils.maxQuestionsPerQuiz = Integer.parseInt(binding.autoTvmaxQuestionsPerQuiz.getText().toString());
        }
        String[] maxQuestionsList = getResources().getStringArray(R.array.max_questions_per_quiz);
        ArrayAdapter<String> adapterAutoCompleteTv = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1,maxQuestionsList);
        binding.autoTvmaxQuestionsPerQuiz.setAdapter(adapterAutoCompleteTv);

        binding.autoTvmaxQuestionsPerQuiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String txtSelected = binding.autoTvmaxQuestionsPerQuiz.getText().toString();
                Utils.maxQuestionsPerQuiz = Integer.parseInt(txtSelected);
            }
        });

        // permission buttons the enabled true
        enableButton(binding.btnPhrasals,binding.tvPhrasalRequiredLabel, Scores.totalScore,Constants.permissionPhrasalScore);
        enableButton(binding.btnNouns,binding.tvNounRequiredLabel, Scores.totalScore,Constants.permissionNounScore);
        enableButton(binding.btnAdjs,binding.tvAdjRequiredLabel, Scores.totalScore,Constants.permissionAdjScore);
        enableButton(binding.btnAdvs,binding.tvAdvRequiredLabel, Scores.totalScore,Constants.permissionAdvScore);
        enableButton(binding.btnIdioms,binding.tvIdiomRequiredLabel, Scores.totalScore,Constants.permissionIdiomScore);

        binding.btnVerbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.VERB_NAME));
            }
        });
        binding.btnVerbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.VERB_NAME));
            }
        });
        binding.btnSentences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.SENTENCE_NAME));
            }
        });
        binding.btnPhrasals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.PHRASAL_NAME));
            }
        });
        binding.btnNouns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.NOUN_NAME));
            }
        });
        binding.btnAdjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.ADJ_NAME));
            }
        });
        binding.btnAdvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.ADV_NAME));
            }
        });
        binding.btnIdioms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.IDIOM_NAME));
            }
        });
    }


    public interface OnsetFragmentToReplaceClickListener {
        void onSetRequiredFragmentQuiz(Fragment fragment);
        void onShowSimpleAdsQuiz();
    }

    private void enableButton(Button button , TextView tvRequiredLabel , int globalMainScore , int permissionScore) {

        if(globalMainScore >= permissionScore) {
            button.setEnabled(true);
            tvRequiredLabel.setVisibility(View.GONE);
        }
        else {
            button.setEnabled(false);
            tvRequiredLabel.setVisibility(View.VISIBLE);
        }
    }

}
