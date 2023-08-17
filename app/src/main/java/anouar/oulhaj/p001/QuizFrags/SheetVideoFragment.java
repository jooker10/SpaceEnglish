package anouar.oulhaj.p001.QuizFrags;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.onVideoBuyClickListener;


public class SheetVideoFragment extends Fragment {

    private onVideoBuyClickListener videoBuyClickListener;

    public SheetVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onVideoBuyClickListener)
            videoBuyClickListener = (onVideoBuyClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(videoBuyClickListener != null)
            videoBuyClickListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sheet_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnVideoVerbs = view.findViewById(R.id.btnVideoVerbs);
        Button btnVideoSentences = view.findViewById(R.id.btnVideoSentences);
        Button btnVideoPhrasals = view.findViewById(R.id.btnVideoPhrasals);
        Button btnVideoNouns = view.findViewById(R.id.btnVideoNouns);
        Button btnVideoAdjs = view.findViewById(R.id.btnVideoAdjs);
        Button btnVideoAdvs = view.findViewById(R.id.btnVideoAdvs);
        Button btnVideoIdoms = view.findViewById(R.id.btnVideoIdoms);
        btnVideoVerbs.setOnClickListener(view13 -> {
            Toast.makeText(requireActivity(), "Show video verbs", Toast.LENGTH_SHORT).show();
            videoBuyClickListener.onShowVideoAds(Constants.VERB_NAME);
        });
        btnVideoSentences.setOnClickListener(view12 -> {
            Toast.makeText(requireActivity(), "Show video sentences", Toast.LENGTH_SHORT).show();
            videoBuyClickListener.onShowVideoAds(Constants.SENTENCE_NAME);
        });
        btnVideoPhrasals.setOnClickListener(view1 -> {
            Toast.makeText(requireActivity(), "Show video phrasals", Toast.LENGTH_SHORT).show();
            videoBuyClickListener.onShowVideoAds(Constants.PHRASAL_NAME);

        });
        btnVideoNouns.setOnClickListener(view1 -> {
            Toast.makeText(requireActivity(), "Show video Nouns", Toast.LENGTH_SHORT).show();
            videoBuyClickListener.onShowVideoAds(Constants.NOUN_NAME);

        });
        btnVideoAdjs.setOnClickListener(view1 -> {
            Toast.makeText(requireActivity(), "Show video Adjs", Toast.LENGTH_SHORT).show();
            videoBuyClickListener.onShowVideoAds(Constants.ADJ_NAME);

        });
        btnVideoAdvs.setOnClickListener(view1 -> {
            Toast.makeText(requireActivity(), "Show video Advs", Toast.LENGTH_SHORT).show();
            videoBuyClickListener.onShowVideoAds(Constants.ADV_NAME);

        });
        btnVideoIdoms.setOnClickListener(view1 -> {
            Toast.makeText(requireActivity(), "Show video Idioms", Toast.LENGTH_SHORT).show();
            videoBuyClickListener.onShowVideoAds(Constants.IDIOM_NAME);

        });
    }
}