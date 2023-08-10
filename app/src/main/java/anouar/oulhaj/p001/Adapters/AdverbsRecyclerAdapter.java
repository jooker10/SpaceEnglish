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
import anouar.oulhaj.p001.DB.Adverb;
import anouar.oulhaj.p001.DB.Phrasal;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;

public class AdverbsRecyclerAdapter extends RecyclerView.Adapter<AdverbsRecyclerAdapter.PhrasalRecyclerHolder> {


    private List<Adverb> adv;
    private onRecyclerListener listener;
    private Context context;

    public AdverbsRecyclerAdapter(List<Adverb> adv, Context context, onRecyclerListener listener) {

        this.adv = adv;
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

        holder.bind(adv.get(position));
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recycler_animation));


    }

    @Override
    public int getItemCount() {
        return adv.size();
    }

    class PhrasalRecyclerHolder extends ViewHolder {

        private Adverb adv;
        private TextView tv_id, tv_advNativeLang, tv_advEng;
        private ImageView img_songs;
        private LinearLayout layout_expanded;
        private Button btn_example;
        private TextView tv_example_expanded;
        private TextView tvKhtissarNativeLang;

        public PhrasalRecyclerHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.holder_verbID);
            tv_advEng = itemView.findViewById(R.id.holderVerbEnglish);
            tv_advNativeLang = itemView.findViewById(R.id.holderVerbNativeLang);
            tvKhtissarNativeLang = itemView.findViewById(R.id.tvKhtissarNativeLang);
            img_songs = itemView.findViewById(R.id.img_songs);
            btn_example = itemView.findViewById(R.id.btn_example_expanded);
            layout_expanded = itemView.findViewById(R.id.linearLayout_expanded);
            tv_example_expanded = itemView.findViewById(R.id.tv_expanded_examples);
            layout_expanded.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        }

        void bind(Adverb adv) {
            this.adv = adv;

            tv_id.setText(String.valueOf(adv.getAdv_id() + 1));
            tv_advEng.setText(adv.getAdv_eng());
            tv_advNativeLang.setText(ChoosingNativeLang(adv,tvKhtissarNativeLang));
            tv_example_expanded.setText(adv.getAdv_example());
            img_songs.setOnClickListener(v -> {
                String txt = adv.getAdv_eng();
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
    private String ChoosingNativeLang(Adverb adv, TextView tvKhtissarNativeLang) {
        switch (Utils.language) {
            // case FRENCH: return verb.getVerb_fr();
            case SPANISH:
                tvKhtissarNativeLang.setText(AbrLanguage.Sp.toString());
                return adv.getAdv_sp();
            case ARABIC:
                tvKhtissarNativeLang.setText(AbrLanguage.Ar.toString());
                return adv.getAdv_ar();
            default:
                tvKhtissarNativeLang.setText(AbrLanguage.Fr.toString());
                return adv.getAdv_fr();
        }
    }

    public interface onRecyclerListener {
        void onDataChanged();
    }
}
