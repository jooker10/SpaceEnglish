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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import edu.SpaceLearning.SpaceEnglish.QuizFrags.QuizCategoriesFragment;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish._Main.Scores;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentNavQuizBinding;


public class QuizNavFragment extends Fragment  {

    private FragmentNavQuizBinding binding;
    private OnsetFragmentToReplaceClickListener replaceFragListener;
    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList <TextView> textViews = new ArrayList<>();


    public QuizNavFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_quiz, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         binding = FragmentNavQuizBinding.bind(view);


         buttons.add(binding.btnVerbs);buttons.add(binding.btnSentences);buttons.add(binding.btnPhrasals);buttons.add(binding.btnNouns);
         buttons.add(binding.btnAdjs);buttons.add(binding.btnAdvs);buttons.add(binding.btnIdioms);
         textViews.add(binding.tvPhrasalRequiredLabel);textViews.add(binding.tvNounRequiredLabel);textViews.add(binding.tvAdjRequiredLabel);
        textViews.add(binding.tvAdvRequiredLabel);textViews.add(binding.tvIdiomRequiredLabel);


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
        for(int i = 0 ; i<Constants.permissionCategoryScoreArray.length;i++) {
            enableButton(buttons.get(i + 2),textViews.get(i), Scores.totalScore,Constants.permissionCategoryScoreArray[i]);
        }

        for (int i =0 ; i< buttons.size();i++) {
            int finalI = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    replaceFragListener.onSetRequiredFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.categoryNameArray[finalI]));
                }
            });
        }

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

    public interface OnsetFragmentToReplaceClickListener {
        void onSetRequiredFragmentQuiz(Fragment fragment);
        void onShowSimpleAdsQuiz();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnsetFragmentToReplaceClickListener){
            replaceFragListener = (OnsetFragmentToReplaceClickListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        replaceFragListener = null;
    }


}