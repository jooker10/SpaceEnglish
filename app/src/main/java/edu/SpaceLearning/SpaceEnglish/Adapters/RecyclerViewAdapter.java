package edu.SpaceLearning.SpaceEnglish.Adapters;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.TextToSpeechManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CategoryRecyclerHolder>  implements Filterable {


    private List<Category> originalElements;
    private List<Category> filteredElements; // For filtered data
    private Context context;
    private Activity activity;
    private String categoryType;
    private TextToSpeechManager textToSpeechManager;
    /*private CustomFilter customFilter;*/
    private AdsClickListener adsClickListener;
   private boolean isAdsShowed = false;
    public RecyclerViewAdapter(List<Category> originalElements, Context context, String categoryType , TextToSpeechManager textToSpeechManager , AdsClickListener adsClickListener) {

        this.originalElements = originalElements;
        this.context = context;
        this.categoryType = categoryType;
        this.activity = (Activity) context;
        this.textToSpeechManager = textToSpeechManager;
        filteredElements = new ArrayList<>(originalElements);
        this.adsClickListener = adsClickListener;
    }

    @NonNull
    @Override
    public CategoryRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_holder_table, parent, false);
            return new CategoryRecyclerHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerHolder holder, int position) {

       holder.bind(filteredElements.get(position));
       holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation_table));

       if(position == 10 && !isAdsShowed) {
           adsClickListener.onShowSimpleAdsQuiz();
           isAdsShowed = true;
       }


    }


    @Override
    public int getItemCount() {
        return filteredElements.size();
    }

   @Override
   public Filter getFilter() {
       return myFilter;
   }

    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category> filteredResults = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredResults.addAll(originalElements);
            } else {
                String filterText = constraint.toString().toLowerCase().trim();
                for (Category category : originalElements) {


                    if (filterTxtLanguageNative(category).toLowerCase().contains(filterText) || category.getCategoryEng().toLowerCase().contains(filterText)) {
                        filteredResults.add(category);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredResults;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredElements.clear();
            filteredElements.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class CategoryRecyclerHolder extends ViewHolder {

        private Category category;
        private TextView holderVerbId;
        private TextView holderVerbEnglish;
        private TextView holderVerbNativeLang;
        private TextView tvExpandedExamples;
        private LinearLayout linearLayoutExpanded;
        private ImageView imgSongs;
        private Button btnExampleExpanded;
        private TextView tvFlagNativeLang;


        public CategoryRecyclerHolder( View itemView) {
            super(itemView);
           holderVerbId = itemView.findViewById(R.id.holderVerbId);
           holderVerbEnglish = itemView.findViewById(R.id.holderVerbEnglish);
            holderVerbNativeLang = itemView.findViewById(R.id.holderVerbNativeLang);
            tvExpandedExamples = itemView.findViewById(R.id.tvExpandedExamples);
            linearLayoutExpanded = itemView.findViewById(R.id.linearLayoutExpanded);
            imgSongs = itemView.findViewById(R.id.imgSongs);
            btnExampleExpanded = itemView.findViewById(R.id.btnExampleExpanded);
            tvFlagNativeLang = itemView.findViewById(R.id.tvFlagNativeLang);

            linearLayoutExpanded.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGE_APPEARING);

        }

        private String capitalizeFirstLetter(String text) {
            return text.substring(0,1).toUpperCase() + text.substring(1);
        }

        void bind(Category category){
            this.category = category;
            holderVerbId.setText(String.valueOf(category.getCategoryID() + 1));  // changed
            holderVerbEnglish.setText(capitalizeFirstLetter(category.getCategoryEng()));
            holderVerbNativeLang.setText(capitalizeFirstLetter(ChoosingNativeLang(category,tvFlagNativeLang)));

            tvExpandedExamples.setText(category.getCategoryExamples());
            imgSongs.setOnClickListener(v -> {
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    }
                }).start();

                String txt = category.getCategoryEng();
                if (textToSpeechManager != null) {
                    textToSpeechManager.speak(txt);
                }
               //-------speech
            });
            tvExpandedExamples.setVisibility(View.GONE);
           if(!categoryType.equals(Constants.SENTENCE_NAME) && !categoryType.equals(Constants.IDIOM_NAME))
           {
               btnExampleExpanded.setOnClickListener(view -> {
                   if(   tvExpandedExamples.getVisibility() == View.GONE) {
                       tvExpandedExamples.setVisibility(View.VISIBLE);

                   } else {
                       tvExpandedExamples.setVisibility(View.GONE);

                   }
                   TransitionManager.beginDelayedTransition(linearLayoutExpanded,new AutoTransition());

               });
           }
           else {
               btnExampleExpanded.setVisibility(View.GONE);
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

    private String filterTxtLanguageNative(Category category){
        switch (Utils.nativeLanguage) {
            case Constants.LANGUAGE_NATIVE_SPANISH:
                return category.getCategorySp();
            case Constants.LANGUAGE_NATIVE_ARABIC:
                return category.getCategoryAr();
            default:
                return category.getCategoryFr();
        }
    }



}
