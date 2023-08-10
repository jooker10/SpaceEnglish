package anouar.oulhaj.p001.Adapters;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;
import java.util.Locale;

import anouar.oulhaj.p001.AbrLanguage;
import anouar.oulhaj.p001.DB.Phrasal;
import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;

public class PhrasalRecyclerAdapter extends RecyclerView.Adapter<PhrasalRecyclerAdapter.PhrasalRecyclerHolder> {


    private List<Phrasal> phrasals;
    private onRecyclerListener listener;
    private Context context;

    public PhrasalRecyclerAdapter(List<Phrasal> sentences, Context context, onRecyclerListener listener) {

        this.phrasals = sentences;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhrasalRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_holder_table, parent, false);
        return new PhrasalRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhrasalRecyclerHolder holder, int position) {

        holder.bind(phrasals.get(position));
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recycler_animation));


    }

    @Override
    public int getItemCount() {
        return phrasals.size();
    }

    class PhrasalRecyclerHolder extends ViewHolder {

        private Phrasal phrasal;
        private TextView tv_id, tv_phrasalNAtiveLang, tv_phrasalEng;
        private ImageView img_songs;
        private LinearLayout layout_expanded;
        private Button btn_example;
        private TextView tv_example_expanded;
        private TextView tvKhtissarNativeLang;

        public PhrasalRecyclerHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.holder_verbID);
            tv_phrasalEng = itemView.findViewById(R.id.holderVerbEnglish);
            tv_phrasalNAtiveLang = itemView.findViewById(R.id.holderVerbNativeLang);
            tvKhtissarNativeLang = itemView.findViewById(R.id.tvKhtissarNativeLang);
            img_songs = itemView.findViewById(R.id.img_songs);
            btn_example = itemView.findViewById(R.id.btn_example_expanded);
            layout_expanded = itemView.findViewById(R.id.linearLayout_expanded);
            tv_example_expanded = itemView.findViewById(R.id.tv_expanded_examples);
            layout_expanded.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        }

        void bind(Phrasal phrasal) {
            this.phrasal = phrasal;

            tv_id.setText(String.valueOf(phrasal.getPhrasal_id() + 1));
            tv_phrasalEng.setText(phrasal.getGetPhrasal_eng());
            tv_phrasalNAtiveLang.setText(ChoosingNativeLang(phrasal,tvKhtissarNativeLang));
            tv_example_expanded.setText(phrasal.getPhrasal_example());
            img_songs.setOnClickListener(v -> {
                String txt = phrasal.getGetPhrasal_eng();
                speech.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
            });
            tv_example_expanded.setVisibility(View.GONE);
            btn_example.setTextColor(Color.CYAN);
            btn_example.setOnClickListener(view -> {
                if(tv_example_expanded.getVisibility() == View.GONE) {
                    tv_example_expanded.setVisibility(View.VISIBLE);
                    btn_example.setTextColor(Color.GRAY);
                } else {
                    tv_example_expanded.setVisibility(View.GONE);
                    btn_example.setTextColor(Color.CYAN);
                }
                TransitionManager.beginDelayedTransition(layout_expanded,new AutoTransition());

            });
        }

        TextToSpeech speech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    speech.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    //----- Functions------------
    private String ChoosingNativeLang(Phrasal phrasal, TextView tvKhtissarNativeLang) {
        switch (Utils.language) {
            // case FRENCH: return verb.getVerb_fr();
            case SPANISH:
                tvKhtissarNativeLang.setText(AbrLanguage.Sp.toString());
                return phrasal.getPhrasal_sp();
            case ARABIC:
                tvKhtissarNativeLang.setText(AbrLanguage.Ar.toString());
                return phrasal.getPhrasal_ar();
            default:
                tvKhtissarNativeLang.setText(AbrLanguage.Fr.toString());
                return phrasal.getPhrasal_fr();
        }
    }

    public interface onRecyclerListener {
        void onDataChanged();
    }
}
