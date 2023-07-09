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

import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.R;

public class VerbRecyclerAdapter extends RecyclerView.Adapter<VerbRecyclerAdapter.VerbRecyclerHolder> {


    private List<Verb> verbs;
    private onRecyclerListener listener;
    private Context context;
    public VerbRecyclerAdapter(List<Verb> verbs,Context context,onRecyclerListener listener) {

        this.verbs = verbs;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VerbRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_table_verbs,parent,false);
        return new VerbRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerbRecyclerHolder holder, int position) {

        holder.bind(verbs.get(position));

        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation));


    }

    @Override
    public int getItemCount() {
        return verbs.size();
    }

    class VerbRecyclerHolder extends ViewHolder {

        private Verb verb;
        private TextView tv_id,tv_verbFr,tv_verbEng;
        private ImageView img_songs;

        public VerbRecyclerHolder( View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.holder_verbID);
            tv_verbFr = itemView.findViewById(R.id.holder_verb_fr);
            tv_verbEng = itemView.findViewById(R.id.holder_verb_eng);
            img_songs = itemView.findViewById(R.id.img_songs);
        }

        void bind(Verb verb){
            this.verb = verb;

            tv_id.setText(String.valueOf(verb.getVerb_id()));
            tv_verbFr.setText(verb.getVerb_fr());
            tv_verbEng.setText(verb.getVerb_eng());
            img_songs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String txt = verb.getVerb_eng();
                    speech.speak(txt,TextToSpeech.QUEUE_FLUSH,null);

                }
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
