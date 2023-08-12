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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;
import java.util.Locale;

import anouar.oulhaj.p001.AbrLanguage;
import anouar.oulhaj.p001.DB.Category;
import anouar.oulhaj.p001.EnumCategory;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryRecyclerHolder> {


    private List<Category> elements;
    private onRecyclerListener listener;
    private Context context;
    private String categoryType;
    public CategoryRecyclerAdapter(List<Category> elements, Context context,String categoryType, onRecyclerListener listener) {

        this.elements = elements;
        this.context = context;
        this.listener = listener;
        this.categoryType = categoryType;
    }

    @NonNull
    @Override
    public CategoryRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_holder_table,parent,false);
        return new CategoryRecyclerHolder(view);
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

        private Category element;
        private TextView tv_id, tv_verbNativeLang,tv_verbEng;
        private ImageView img_songs;
        private Button btn_example;
        private LinearLayout layout_expanded;
        private TextView tv_example_expanded;
        private TextView tvKhtissarNativeLang;

        public CategoryRecyclerHolder( View itemView) {
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

        void bind(Category element){
            this.element = element;

            tv_id.setText(String.valueOf(element.getCategoryID() + 1));
            tv_verbEng.setText(element.getCategoryEng());
            tv_verbNativeLang.setText(ChoosingNativeLang(element,tvKhtissarNativeLang));

            tv_example_expanded.setText(element.getCategoryExamples());
            img_songs.setOnClickListener(v -> {
                String txt = element.getCategoryEng();
                speech.speak(txt,TextToSpeech.QUEUE_FLUSH,null);
            });
            tv_example_expanded.setVisibility(View.GONE);
           if(!categoryType.equals(EnumCategory.SENTENCE.name()) && !categoryType.equals(EnumCategory.IDIOM.name()))
           {
               btn_example.setTextColor(Color.CYAN);
               btn_example.setOnClickListener(view -> {
                   if(tv_example_expanded.getVisibility() == View.GONE) {
                       tv_example_expanded.setVisibility(View.VISIBLE);
                       btn_example.setTextColor(Color.GRAY);
                   } else {
                       tv_example_expanded.setVisibility(View.GONE);
                       btn_example.setTextColor(Color.CYAN);
                   }
                   TransitionManager.beginDelayedTransition(layout_expanded,new AutoTransition());

               });
           }
           else {
               btn_example.setVisibility(View.GONE);
           }

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
    private String ChoosingNativeLang(Category element , TextView tvKhtissarNativeLang){
        switch (Utils.language) {
            case SPANISH:
                tvKhtissarNativeLang.setText(AbrLanguage.Sp.toString());
                return element.getCategorySp();
            case ARABIC:
                tvKhtissarNativeLang.setText(AbrLanguage.Ar.toString());
                return element.getCategoryAr();
            default: tvKhtissarNativeLang.setText(AbrLanguage.Fr.toString());
                return element.getCategoryFr();
        }
    }
    //-------interfaces----------
    public interface onRecyclerListener{
        void onDataChanged();
    }


}
