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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;
import java.util.Locale;

import anouar.oulhaj.p001.Constants;
import anouar.oulhaj.p001.DB.Category;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;
import anouar.oulhaj.p001.databinding.RecyclerHolderTableBinding;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryRecyclerHolder> {
  RecyclerHolderTableBinding binding;

    private List<Category> elements;
    private onRecyclerListener listener;
    private Context context;
    private String categoryType;
    private TextToSpeech speech;
    public CategoryRecyclerAdapter(List<Category> elements, Context context,String categoryType,TextToSpeech speech, onRecyclerListener listener) {

        this.elements = elements;
        this.context = context;
        this.listener = listener;
        this.categoryType = categoryType;
        this.speech = speech;
    }

    @NonNull
    @Override
    public CategoryRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_holder_table,parent,false);
        binding = RecyclerHolderTableBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CategoryRecyclerHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerHolder holder, int position) {

        holder.bind(elements.get(position));

        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_animation));


    }

    @Override
    public int getItemCount() {
        return elements.size();
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
    //-------interfaces----------
    public interface onRecyclerListener{
        void onDataChanged();
    }

    /*TextToSpeech speech = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if(status == TextToSpeech.SUCCESS){
                speech.setLanguage(Locale.ENGLISH);
            }
        }
    });*/

}
