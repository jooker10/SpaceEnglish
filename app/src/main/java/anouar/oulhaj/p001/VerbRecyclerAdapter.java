package anouar.oulhaj.p001;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import anouar.oulhaj.p001.DB.Verb;

public class VerbRecyclerAdapter extends RecyclerView.Adapter<VerbRecyclerAdapter.VerbRecyclerHolder> {


    private List<Verb> verbs;
    private onRecyclerListener listener;
    public VerbRecyclerAdapter(List<Verb> verbs,onRecyclerListener listener) {

        this.verbs = verbs;
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


    }

    @Override
    public int getItemCount() {
        return verbs.size();
    }

    class VerbRecyclerHolder extends ViewHolder {

        private Verb verb;
        private TextView tv_id,tv_verbFr,tv_verbEng;

        public VerbRecyclerHolder( View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.holder_verbID);
            tv_verbFr = itemView.findViewById(R.id.holder_verb_fr);
            tv_verbEng = itemView.findViewById(R.id.holder_verb_eng);
        }

        void bind(Verb verb){
            this.verb = verb;

            tv_id.setText(String.valueOf(verb.getVerb_id()));
            tv_verbFr.setText(verb.getVerb_fr());
            tv_verbEng.setText(verb.getVerb_eng());
        }
    }

    public interface onRecyclerListener{
        void onDataChanged();
    }
}
