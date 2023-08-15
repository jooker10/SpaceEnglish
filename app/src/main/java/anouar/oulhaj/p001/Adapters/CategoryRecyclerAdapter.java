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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import anouar.oulhaj.p001.Constants;
import anouar.oulhaj.p001.DB.Category;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;
import anouar.oulhaj.p001.databinding.RecyclerHolderTableBinding;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryRecyclerHolder>  implements Filterable {
  RecyclerHolderTableBinding binding;

    private List<Category> allElements;
    private List<Category> filteredElements; // For filtered data
    private Context context;
    private String categoryType;
    private TextToSpeech speech;
    private CustomFilter customFilter;
    public CategoryRecyclerAdapter(List<Category> originalElements, Context context,String categoryType,TextToSpeech speech) {

        this.allElements = originalElements;
        this.context = context;
        this.categoryType = categoryType;
        this.speech = speech;
        filteredElements = new ArrayList<>(allElements);
    }

    @NonNull
    @Override
    public CategoryRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerHolderTableBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CategoryRecyclerHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerHolder holder, int position) {

        holder.bind(filteredElements.get(position));

        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation));


    }

    @Override
    public int getItemCount() {
        return filteredElements.size();
    }

    @Override
    public Filter getFilter() {
        if (customFilter == null) {
            customFilter = new CustomFilter();
        }
        return customFilter;
    }

    // Custom Filter class
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Category> filteredList = new ArrayList<>();

                for (Category item : allElements) {
                    // Implement your filter logic here
                    if (item.getCategoryEng().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }

                filterResults.count = filteredList.size();
                filterResults.values = filteredList;
            } else {
                filterResults.count = allElements.size();
                filterResults.values = allElements;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredElements = (List<Category>) results.values;
            notifyDataSetChanged();
        }
    }

    class CategoryRecyclerHolder extends ViewHolder {

        private Category category;


        public CategoryRecyclerHolder( View itemView) {
            super(itemView);
           // binding = RecyclerHolderTableBinding.bind(itemView);
            binding.linearLayoutExpanded.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        }

        void bind(Category category){
            this.category = category;

            binding.holderVerbId.setText(String.valueOf(category.getCategoryID() + 1));
            binding.holderVerbEnglish.setText(category.getCategoryEng());
            binding.holderVerbNativeLang.setText(ChoosingNativeLang(category,    binding.tvFlagNativeLang));

            binding.tvExpandedExamples.setText(category.getCategoryExamples());
            binding.imgSongs.setOnClickListener(v -> {
                String txt = category.getCategoryEng();
                if (speech != null) {
                    Toast.makeText(v.getContext(), txt, Toast.LENGTH_SHORT).show();
                    speech.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
                }
               //-------speech
            });
            binding.tvExpandedExamples.setVisibility(View.GONE);
           if(!categoryType.equals(Constants.SENTENCE_NAME) && !categoryType.equals(Constants.IDIOM_NAME))
           {
               binding.btnExampleExpanded.setTextColor(Color.CYAN);
               binding.btnExampleExpanded.setOnClickListener(view -> {
                   if(   binding.tvExpandedExamples.getVisibility() == View.GONE) {
                       binding.tvExpandedExamples.setVisibility(View.VISIBLE);
                       binding.btnExampleExpanded.setTextColor(Color.GRAY);
                   } else {
                       binding.tvExpandedExamples.setVisibility(View.GONE);
                       binding.btnExampleExpanded.setTextColor(Color.CYAN);
                   }
                   TransitionManager.beginDelayedTransition(   binding.linearLayoutExpanded,new AutoTransition());

               });
           }
           else {
               binding.btnExampleExpanded.setVisibility(View.GONE);
           }

        }

    }

    //----- Functions------------
    private String ChoosingNativeLang(Category element , TextView tvLangFlag){
        switch (Utils.nativeLanguage) {
            case Constants.LANGUAGE_NATIVE_SPANISH:
                tvLangFlag.setText("Sp");
                return element.getCategorySp();
            case Constants.LANGUAGE_NATIVE_ARABIC:
                tvLangFlag.setText("Ar");
                return element.getCategoryAr();
            default: tvLangFlag.setText("Fr");
                return element.getCategoryFr();
        }
    }

}
