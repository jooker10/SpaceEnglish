package anouar.oulhaj.p001.Adapters;

import android.animation.LayoutTransition;
import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import anouar.oulhaj.p001.AbrLanguage;
import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_holder_table,parent,false);
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
        private TextView tv_id, tv_verbNativeLang,tv_verbEng;
        private ImageView img_songs;
        private Button btn_example;
        private LinearLayout layout_expanded;
        private TextView tv_example_expanded;
        private TextView tvKhtissarNativeLang;

        public VerbRecyclerHolder( View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.holder_verbID);
            tv_verbEng = itemView.findViewById(R.id.holderVerbEnglish);
            tv_verbNativeLang = itemView.findViewById(R.id.holderVerbNativeLang);
            img_songs = itemView.findViewById(R.id.img_songs);
            tvKhtissarNativeLang = itemView.findViewById(R.id.tvKhtissarNativeLang);
            btn_example = itemView.findViewById(R.id.btn_example_expanded);
            layout_expanded = itemView.findViewById(R.id.linearLayout_expanded);
            tv_example_expanded = itemView.findViewById(R.id.tv_expanded_examples);
            layout_expanded.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        }

        void bind(Verb verb){
            this.verb = verb;

            tv_id.setText(String.valueOf(verb.getVerb_id() + 1));
            tv_verbEng.setText(verb.getVerb_eng());
            tv_verbNativeLang.setText(ChoosingNativeLang(verb,tvKhtissarNativeLang));

            tv_example_expanded.setText(verb.getVerb_example());
            img_songs.setOnClickListener(v -> {
                String txt = verb.getVerb_eng();
                speech.speak(txt,TextToSpeech.QUEUE_FLUSH,null);
            });
            tv_example_expanded.setVisibility(View.GONE);
            btn_example.setOnClickListener(view -> {
                int v = (tv_example_expanded.getVisibility() == View.GONE)? View.VISIBLE : View.GONE ;
                TransitionManager.beginDelayedTransition(layout_expanded,new AutoTransition());
                tv_example_expanded.setVisibility(v);
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
    private String ChoosingNativeLang(Verb verb , TextView tvKhtissarNativeLang){
        switch (Utils.language) {
            case SPANISH:
                tvKhtissarNativeLang.setText(AbrLanguage.Sp.toString());
                return verb.getVerb_sp();
            case ARABIC:
                tvKhtissarNativeLang.setText(AbrLanguage.Ar.toString());
                return verb.getVerb_ar();
            default: tvKhtissarNativeLang.setText(AbrLanguage.Fr.toString());
                return verb.getVerb_fr();
        }
    }
    //-------interfaces----------
    public interface onRecyclerListener{
        void onDataChanged();
    }


}
