package anouar.oulhaj.p001;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import anouar.oulhaj.p001.databinding.DialogQuizScoresBinding;


public class DialogQuizFragment extends androidx.fragment.app.DialogFragment {

    private DialogQuizScoresBinding binding;
    public static final String TAG = "quizDialog";
    private static final String ARG_TITLE = "title";
    private static final String ARG_MSG_VERB = "msgVerb";
    private static final String ARG_MSG_SENTENCE = "msgSentence";
    private static final String ARG_MSG_PHRASAL = "msgPhrasal";
    private static final String ARG_MSG_NOUN = "msgNoun";
    private static final String ARG_MSG_ADJ = "msgAdj";
    private static final String ARG_MSG_ADV = "msgAdv";
    private static final String ARG_MSG_IDIOM = "msgIdiom";
    private static final String ARG_ICON = "icon";
    private static final String ARG_CATEGORY_TYPE = "Verb";


    private String titleDialog;
    private int iconDialog;
    private String categoryType;
    private String msgVerbDialog;
    private String msgSentenceDialog;
    private String msgSPhrasalDialog;
    private String msgNounDialog;
    private String msgAdjDialog;
    private String msgAdvDialog;
    private String msgIdiomDialog;


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
            titleDialog = bundle.getString(ARG_TITLE);
            iconDialog = bundle.getInt(ARG_ICON);
            categoryType = bundle.getString(ARG_CATEGORY_TYPE);
            msgVerbDialog = bundle.getString(ARG_MSG_VERB);
            msgSentenceDialog = bundle.getString(ARG_MSG_SENTENCE);
            msgSPhrasalDialog = bundle.getString(ARG_MSG_PHRASAL);
            msgNounDialog = bundle.getString(ARG_MSG_NOUN);
            msgAdjDialog = bundle.getString(ARG_MSG_ADJ);
            msgAdvDialog = bundle.getString(ARG_MSG_ADV);
            msgIdiomDialog = bundle.getString(ARG_MSG_IDIOM);
        }

    }

    public static DialogQuizFragment newInstance(String title, String verbMsg, String sentenceMsg, String phrasalMsg, String nounMsg, String adjMsg, String advMsg, String idiomMsg, int icon, String categoryType) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        bundle.putInt(ARG_ICON, icon);
        bundle.putString(ARG_CATEGORY_TYPE, categoryType);
        bundle.putString(ARG_MSG_VERB, verbMsg);
        bundle.putString(ARG_MSG_SENTENCE, sentenceMsg);
        bundle.putString(ARG_MSG_PHRASAL, phrasalMsg);
        bundle.putString(ARG_MSG_NOUN, nounMsg);
        bundle.putString(ARG_MSG_ADJ, adjMsg);
        bundle.putString(ARG_MSG_ADV, advMsg);
        bundle.putString(ARG_MSG_IDIOM, idiomMsg);

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
        binding.tvDialogTitle.setText(titleDialog);
        binding.imgDialogIcon.setImageResource(iconDialog);
        binding.tvDialogVerbMsg.setText(msgVerbDialog);
        binding.tvDialogSentenceMsg.setText(msgSentenceDialog);
        binding.tvDialogPhrasalMsg.setText(msgSPhrasalDialog);
        binding.tvDialogNounMsg.setText(msgNounDialog);
        binding.tvDialogAdjMsg.setText(msgAdjDialog);
        binding.tvDialogAdvMsg.setText(msgAdvDialog);
        binding.tvDialogIdiomMsg.setText(msgIdiomDialog);
        binding.tvDialogTitle.setText(titleDialog);
        binding.imgDialogIcon.setImageResource(iconDialog);

        if (categoryType.equals(Constants.VERB_NAME))
            binding.tvDialogVerbMsg.setTextColor(Color.GREEN);
        if (categoryType.equals(Constants.SENTENCE_NAME))
            binding.tvDialogSentenceMsg.setTextColor(Color.GREEN);
        if (categoryType.equals(Constants.PHRASAL_NAME))
            binding.tvDialogPhrasalMsg.setTextColor(Color.GREEN);
        if (categoryType.equals(Constants.NOUN_NAME))
            binding.tvDialogNounMsg.setTextColor(Color.GREEN);
        if (categoryType.equals(Constants.ADJ_NAME))
            binding.tvDialogAdjMsg.setTextColor(Color.GREEN);
        if (categoryType.equals(Constants.ADV_NAME))
            binding.tvDialogAdvMsg.setTextColor(Color.GREEN);
        if (categoryType.equals(Constants.IDIOM_NAME))
            binding.tvDialogIdiomMsg.setTextColor(Color.GREEN);

        binding.btnDialogNewQuiz.setOnClickListener(v -> {

            //  Toast.makeText(requireActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();
            newQuizClickListener.onDialogNewQuizClick();
            dismiss();
        });
        binding.btnDialogSendHome.setOnClickListener(view1 -> {
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
        void onDialogNewQuizClick();
    }

    public interface onDialogNeutralClickListener {
        void onDialogNeutralClick();
    }
}