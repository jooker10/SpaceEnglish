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

import anouar.oulhaj.p001.DB.Sentence;
import anouar.oulhaj.p001.R;

public class SentencesRecyclerAdapter extends RecyclerView.Adapter<SentencesRecyclerAdapter.SentencesRecyclerHolder> {


    private List<Sentence> sentences;
    private onRecyclerListener listener;
    private Context context;
    public SentencesRecyclerAdapter(List<Sentence> sentences,Context context, onRecyclerListener listener) {

        this.sentences = sentences;
        this.context = context;
        this.listener = listener;
    }

    public void ChangeCategoryList(List<Sentence> newList){
        sentences.clear();
        sentences.addAll(newList);
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

        holder.bind(sentences.get(position));
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation));


    }

    @Override
    public int getItemCount() {
        return sentences.size();
    }

    class SentencesRecyclerHolder extends ViewHolder {

        private Sentence sentence;
        private TextView tv_id, tv_sentenceFr, tv_sentenceEng;
        private ImageView img_songs;

        public SentencesRecyclerHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.holder_verbID);
            tv_sentenceFr = itemView.findViewById(R.id.holder_verb_fr);
            tv_sentenceEng = itemView.findViewById(R.id.holder_verb_eng);
            img_songs = itemView.findViewById(R.id.img_songs);
        }

        void bind(Sentence sentence){
            this.sentence = sentence;

            tv_id.setText(String.valueOf(sentence.getSentence_id()));
            tv_sentenceFr.setText(sentence.getSentence_fr());
            tv_sentenceEng.setText(sentence.getSentence_eng());
            img_songs.setOnClickListener(v -> {
                String txt = sentence.getSentence_eng();
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
