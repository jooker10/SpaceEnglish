package edu.SpaceLearning.SpaceEnglish.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.ChipItem;
import edu.SpaceLearning.SpaceEnglish.R;

public class ChipRecyclerAdapter extends RecyclerView.Adapter<ChipRecyclerAdapter.ChipRecyclerHolder> {
    private ArrayList<ChipItem> items;

    public ChipRecyclerAdapter(ArrayList<ChipItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ChipRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_recycler_chip,parent,false);

        return new ChipRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChipRecyclerHolder holder, int position) {

        ChipItem chipItem = items.get(position);
        holder.bind(chipItem,position);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation_chip));


    }

    @Override
    public int getItemCount() {
        return items.size();
    }



     class ChipRecyclerHolder extends RecyclerView.ViewHolder {
        private ChipItem item;
        private TextView tvTitleLabel;
        private TextView tvScore;
        private View divider;

        public ChipRecyclerHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleLabel = itemView.findViewById(R.id.tvRecyclerHomeTitleLabel);
            tvScore = itemView.findViewById(R.id.tvRecyclerHomeScoreCounter);
            divider = itemView.findViewById(R.id.dividerRecyclerColor);
        }

        void bind(ChipItem item , int position) {
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
