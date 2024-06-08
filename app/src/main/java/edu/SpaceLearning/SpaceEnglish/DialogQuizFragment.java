package edu.SpaceLearning.SpaceEnglish;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.DialogQuizScoresBinding;


public class DialogQuizFragment extends DialogFragment {

    private static final String ARG_CATEGORY_TYPE = "category";
    private static final String ARG_MAIN_SCORE = "arg_main_score";
    private static final String ARG_POINTS_ADDED = "arg_points_added";
    private static final String ARG_ELEMENTS_ADDED = "arg_elements_added";
    private static final String ARG_USER_RIGHT_SCORE = "arg_quiz_counter";
    private static final String ARG_MSG = "msg";

    private DialogQuizScoresBinding binding;
    public static final String TAG = "quizDialog";


    private String categoryType,msg;
    private int pointsAdded,elementsAdded,userRightScore;


    private onDialogSendHomeClickListener sendHomeClickListener;
    private onDialogNewQuizClickListener newQuizClickListener;


    public DialogQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onDialogSendHomeClickListener) {
            sendHomeClickListener = (onDialogSendHomeClickListener) context;
        }

        if (context instanceof onDialogNewQuizClickListener) {
            newQuizClickListener = (onDialogNewQuizClickListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        sendHomeClickListener = null;
        newQuizClickListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryType = bundle.getString(ARG_CATEGORY_TYPE);
            pointsAdded = bundle.getInt(ARG_POINTS_ADDED);
            elementsAdded = bundle.getInt(ARG_ELEMENTS_ADDED);
            userRightScore = bundle.getInt(ARG_USER_RIGHT_SCORE);
            msg = bundle.getString(ARG_MSG);

        }

    }

    public static DialogQuizFragment newInstance(String categoryType,int pointsAdded , int elementsAdded , int userRightScore , String msg) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CATEGORY_TYPE, categoryType);
        bundle.putInt(ARG_POINTS_ADDED, pointsAdded);
        bundle.putInt(ARG_ELEMENTS_ADDED, elementsAdded);
        bundle.putInt(ARG_USER_RIGHT_SCORE, userRightScore);
        bundle.putString(ARG_MSG , msg);

        DialogQuizFragment dialog_fragment = new DialogQuizFragment();
        dialog_fragment.setArguments(bundle);
        return dialog_fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_quiz_scores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DialogQuizScoresBinding.bind(view);



        //______set Txt and Events----------------
        binding.tvDialogCongrat.setText(msg);
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager.speak(msg);
        }

        binding.tvDialogResult.setText(userRightScore + "/" + Utils.maxQuestionsPerQuiz);
        binding.tvPointsAdded.setText(String.valueOf(pointsAdded));
        binding.tvElementsAdded.setText(String.valueOf(elementsAdded));
        binding.tvDialogCategoryElementAdded.setText(categoryType + " Added :");

        binding.btnNewQuiz.setOnClickListener(v -> {

            //  Toast.makeText(requireActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();
            newQuizClickListener.onSheetDialogNewQuizClick();
            dismiss();
        });
        binding.btnSendHome.setOnClickListener(view1 -> {
            // Toast.makeText(requireActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();
            sendHomeClickListener.onDialogSendHomeClick(categoryType);
            dismiss();
        });
    }

    //--------------Interfaces-------------------
    public interface onDialogSendHomeClickListener {
        void onDialogSendHomeClick(String categoryType);
    }

    public interface onDialogNewQuizClickListener {
        void onSheetDialogNewQuizClick();
    }

}