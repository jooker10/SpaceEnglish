package edu.SpaceLearning.SpaceEnglish.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.UtilsClasses.InfoScore;
import edu.SpaceLearning.SpaceEnglish.R;

public class HomeInfoScoresRecyclerAdapter extends RecyclerView.Adapter<HomeInfoScoresRecyclerAdapter.InfoScoreRecyclerHolder> {
    private ArrayList<InfoScore> items;

    public HomeInfoScoresRecyclerAdapter(ArrayList<InfoScore> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public InfoScoreRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_recycler_info_scores,parent,false);

        return new InfoScoreRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoScoreRecyclerHolder holder, int position) {

        InfoScore infoScore = items.get(position);
        holder.bind(infoScore,position);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation_chip));


    }

    @Override
    public int getItemCount() {
        return items.size();
    }



     class InfoScoreRecyclerHolder extends RecyclerView.ViewHolder {
        private InfoScore item;
        private TextView tvTitleLabel;
        private TextView tvScore;
        private View divider;

        public InfoScoreRecyclerHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleLabel = itemView.findViewById(R.id.tvRecyclerHomeTitleLabel);
            tvScore = itemView.findViewById(R.id.tvRecyclerHomeScoreCounter);
            divider = itemView.findViewById(R.id.dividerRecyclerColor);
        }

        void bind(InfoScore item , int position) {
            this.item = item;
            tvTitleLabel.setText(item.getTitleLabel());
            tvScore.setText(item.getScoreCounter());
            tvScore.setAnimation(AnimationUtils.loadAnimation(tvScore.getContext(),R.anim.anim_score_tv));

            switch (position) {
                case 0 :
                    divider.setBackgroundColor(divider.getContext().getColor(R.color.custom_secondary));
                    tvTitleLabel.setTextColor(tvTitleLabel.getContext().getColor(R.color.custom_secondary));
                    tvScore.setTextColor(tvScore.getContext().getColor(R.color.custom_secondary));
                    break;
                case 1 :
                    divider.setBackgroundColor(divider.getContext().getColor(R.color.mint_green));
                    break;
                case 2 :
                    divider.setBackgroundColor(divider.getContext().getColor(R.color.light_blue));
                    break;
                case 3 :
                    divider.setBackgroundColor(divider.getContext().getColor(R.color.lemon_yellow));
                    break;
                case 4 :
                    divider.setBackgroundColor(divider.getContext().getColor( R.color.lime_green));
                    break;
                case 5 :
                    divider.setBackgroundColor(divider.getContext().getColor(R.color.turquoise));
                    break;
            }
        }

    }
}
