package anouar.oulhaj.p001.Adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;
import java.util.Locale;

import anouar.oulhaj.p001.AbrLanguage;
import anouar.oulhaj.p001.DB.Idiom;
import anouar.oulhaj.p001.DB.Sentence;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;

public class IdiomsRecyclerAdapter extends RecyclerView.Adapter<IdiomsRecyclerAdapter.SentencesRecyclerHolder> {


    private List<Idiom> idioms;
    private onRecyclerListener listener;
    private Context context;
    public IdiomsRecyclerAdapter(List<Idiom> idioms, Context context, onRecyclerListener listener) {

        this.idioms = idioms;
        this.context = context;
        this.listener = listener;
    }

    public void ChangeCategoryList(List<Idiom> newList){
        idioms.clear();
        idioms.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SentencesRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_holder_table,parent,false);
        return new SentencesRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SentencesRecyclerHolder holder, int position) {

        holder.bind(idioms.get(position));
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation));


    }

    @Override
    public int getItemCount() {
        return idioms.size();
    }

    class SentencesRecyclerHolder extends ViewHolder {

        private Idiom idiom;
        private TextView tv_id, tv_idiomNativeLang, tv_idiomEng;
        private ImageView img_songs;
        private Button btn_example;
        private TextView tvKhtissarNativeLang;

        public SentencesRecyclerHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.holder_verbID);
            tv_idiomEng = itemView.findViewById(R.id.holderVerbEnglish);
            tv_idiomNativeLang = itemView.findViewById(R.id.holderVerbNativeLang);
            tvKhtissarNativeLang = itemView.findViewById(R.id.tvKhtissarNativeLang);
            img_songs = itemView.findViewById(R.id.img_songs);
            btn_example = itemView.findViewById(R.id.btn_example_expanded);
            btn_example.setVisibility(View.GONE);
        }

        void bind(Idiom idiom){
            this.idiom = idiom;

            tv_id.setText(String.valueOf(idiom.getIdiom_id() + 1));
            tv_idiomNativeLang.setText(ChoosingNativeLang(idiom , tvKhtissarNativeLang));
            tv_idiomEng.setText(idiom.getIdiom_eng());
            img_songs.setOnClickListener(v -> {
                String txt = idiom.getIdiom_eng();
                speech.speak(txt,TextToSpeech.QUEUE_FLUSH,null);
            });
        }

        TextToSpeech speech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    speech.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    //----- Functions------------
    private String ChoosingNativeLang(Idiom idiom, TextView tvKhtissarNativeLang){
        switch (Utils.language) {
            case SPANISH:
                tvKhtissarNativeLang.setText(AbrLanguage.Sp.toString());
            return idiom.getIdiom_sp();
            case ARABIC:
                tvKhtissarNativeLang.setText(AbrLanguage.Ar.toString());
                return idiom.getIdiom_ar();
            default:
                tvKhtissarNativeLang.setText(AbrLanguage.Fr.toString());
                return idiom.getIdiom_fr();
        }
    }

    public interface onRecyclerListener{
        void onDataChanged();
    }
}
