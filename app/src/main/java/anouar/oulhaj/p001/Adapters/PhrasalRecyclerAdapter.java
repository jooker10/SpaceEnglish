package anouar.oulhaj.p001.Adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;
import java.util.Locale;

import anouar.oulhaj.p001.DB.Phrasal;
import anouar.oulhaj.p001.R;

public class PhrasalRecyclerAdapter extends RecyclerView.Adapter<PhrasalRecyclerAdapter.PhrasalRecyclerHolder> {


    private List<Phrasal> phrasals;
    private onRecyclerListener listener;
    private Context context;
    public PhrasalRecyclerAdapter(List<Phrasal> sentences,Context context, onRecyclerListener listener) {

        this.phrasals = sentences;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhrasalRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_table_verbs,parent,false);
        return new PhrasalRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhrasalRecyclerHolder holder, int position) {

        holder.bind(phrasals.get(position));
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation));



    }

    @Override
    public int getItemCount() {
        return phrasals.size();
    }

    class PhrasalRecyclerHolder extends ViewHolder {

        private Phrasal phrasal;
        private TextView tv_id, tv_phrasalFr, tv_phrasalEng;
        private ImageView img_songs;

        public PhrasalRecyclerHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.holder_verbID);
            tv_phrasalFr = itemView.findViewById(R.id.holder_verb_fr);
            tv_phrasalEng = itemView.findViewById(R.id.holder_verb_eng);
            img_songs = itemView.findViewById(R.id.img_songs);
        }

        void bind(Phrasal phrasal){
            this.phrasal = phrasal;

            tv_id.setText(String.valueOf(phrasal.getPhrasal_id()));
            tv_phrasalFr.setText(phrasal.getPhrasal_fr());
            tv_phrasalEng.setText(phrasal.getGetPhrasal_eng());
            img_songs.setOnClickListener(v -> {
                String txt = phrasal.getGetPhrasal_eng();
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

    public interface onRecyclerListener{
        void onDataChanged();
    }
}
