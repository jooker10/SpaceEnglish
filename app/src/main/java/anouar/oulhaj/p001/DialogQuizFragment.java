package anouar.oulhaj.p001;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001.databinding.DialogQuizScoresBinding;


public class DialogQuizFragment extends androidx.fragment.app.DialogFragment {

    private static final String ARG_CATEGORY_TYPE = "category";
    private static final String ARG_MAIN_SCORE = "arg_main_score";
    private static final String ARG_POINTS_ADDED = "arg_points_added";
    private static final String ARG_ELEMENTS_ADDED = "arg_elements_added";
    private static final String ARG_QUIZ_COUNTER = "arg_quiz_counter";

    private DialogQuizScoresBinding binding;
    public static final String TAG = "quizDialog";


    private String categoryType;
    private int mainScore,pointsAdded,elementsAdded,quizCounter;


    private onDialogSendHomeClickListener sendHomeClickListener;
    private onDialogNewQuizClickListener newQuizClickListener;
    private onDialogNeutralClickListener neutralListener;

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
        if (context instanceof onDialogNeutralClickListener) {
            neutralListener = (onDialogNeutralClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sendHomeClickListener = null;
        newQuizClickListener = null;
        neutralListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryType = bundle.getString(ARG_CATEGORY_TYPE);
            mainScore = bundle.getInt(ARG_MAIN_SCORE);
            pointsAdded = bundle.getInt(ARG_POINTS_ADDED);
            elementsAdded = bundle.getInt(ARG_ELEMENTS_ADDED);
            quizCounter = bundle.getInt(ARG_QUIZ_COUNTER);

        }

    }

    public static DialogQuizFragment newInstance(String categoryType, int mainScore,int pointsAdded , int elementsAdded , int quizCounter) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CATEGORY_TYPE, categoryType);
        bundle.putInt(ARG_MAIN_SCORE, mainScore);
        bundle.putInt(ARG_POINTS_ADDED, pointsAdded);
        bundle.putInt(ARG_ELEMENTS_ADDED, elementsAdded);
        bundle.putInt(ARG_QUIZ_COUNTER, quizCounter);


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
        binding.tvDialogTitle.setText(categoryType);

        binding.tvMainScore.setText(categoryType + " Score : " + mainScore);
        binding.tvPointsAdded.setText("Points : "+ pointsAdded + " points.");
        binding.tvElementsAdded.setText("Extra "+elementsAdded + categoryType + " : "+elementsAdded);
        binding.tvQuizNumberCounter.setText(categoryType + " Quiz completed : " + quizCounter);

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

    public interface onDialogNeutralClickListener {
        void onDialogNeutralClick();
    }
}