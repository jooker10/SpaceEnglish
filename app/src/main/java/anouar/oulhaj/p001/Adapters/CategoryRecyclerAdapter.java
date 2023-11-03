package anouar.oulhaj.p001.Adapters;

import android.animation.LayoutTransition;
import android.app.Activity;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;

import anouar.oulhaj.p001.DB.Category;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001._Main.Utils;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryRecyclerHolder>  implements Filterable {

    private static final String ADS_NATIVE_ID = "ca-app-pub-1653992568721047/3630761505"; // reel
    //private static final String ADS_NATIVE_ID = "ca-app-pub-3940256099942544/1044960115"; // test
    private List<Category> originalElements;
    private List<Category> filteredElements; // For filtered data
    private Context context;
    private Activity activity;
    private String categoryType;
    private TextToSpeech speech;
   private CustomFilter customFilter;
   private static boolean isNativeAds = false;
    public CategoryRecyclerAdapter(List<Category> originalElements, Context context, String categoryType, TextToSpeech speech) {

        this.originalElements = originalElements;
        this.context = context;
        this.categoryType = categoryType;
        this.speech = speech;
        this.activity = (Activity) context;

      filteredElements = new ArrayList<>(originalElements);
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
       if((position + 1) % 10 == 0 && position != 0) {
           holder.frameNativeAdsHolder.setVisibility(View.VISIBLE);
           holder.bindNativeData();
       }
       else {
           holder.frameNativeAdsHolder.setVisibility(View.GONE);
       }


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

                for (Category item : originalElements) {
                    // Implement your filter logic here
                    if (item.getCategoryEng().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }

                filterResults.count = filteredList.size();
                filterResults.values = filteredList;
            } else {
                filterResults.count = originalElements.size();
                filterResults.values = originalElements;
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
        private TextView holderVerbId;
        private TextView holderVerbEnglish;
        private TextView holderVerbNativeLang;
        private TextView tvExpandedExamples;
        private LinearLayout linearLayoutExpanded;
        private ImageView imgSongs;
        private Button btnExampleExpanded;
        private TextView tvFlagNativeLang;
        private FrameLayout frameNativeAdsHolder;
        NativeAdView nativeAdView;


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
            frameNativeAdsHolder = itemView.findViewById(R.id.frameNativeAdsHolder);
            frameNativeAdsHolder.setVisibility(View.GONE);

            linearLayoutExpanded.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGE_APPEARING);

            nativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ad_unit, null);
        }

        void bind(Category category){
            this.category = category;
            holderVerbId.setText(String.valueOf(category.getCategoryID() + 1));  // changed
            holderVerbEnglish.setText(category.getCategoryEng());
            holderVerbNativeLang.setText(ChoosingNativeLang(category,    tvFlagNativeLang));

            tvExpandedExamples.setText(category.getCategoryExamples());
            imgSongs.setOnClickListener(v -> {
                String txt = category.getCategoryEng();
                if (speech != null) {
                    Toast.makeText(v.getContext(), txt, Toast.LENGTH_SHORT).show();
                    speech.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
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
        void bindNativeData() {
            AdLoader adLoader = new AdLoader.Builder(context, ADS_NATIVE_ID)
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            populateNativeAdView(nativeAd, nativeAdView);
                            frameNativeAdsHolder.removeAllViews();
                            frameNativeAdsHolder.addView(nativeAdView);
                        }
                    })
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());
        }

        private void populateNativeAdView(NativeAd nativeAd, NativeAdView nativeAdView) {
            MediaView mediaView = nativeAdView.findViewById(R.id.native_ad_media);
            mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            nativeAdView.setMediaView(mediaView);
            mediaView.setMediaContent(nativeAd.getMediaContent());

            TextView headLine = nativeAdView.findViewById(R.id.native_ad_headline);
            TextView body = nativeAdView.findViewById(R.id.native_ad_body);
            TextView rating = nativeAdView.findViewById(R.id.native_ad_ratingFloat);
            TextView price = nativeAdView.findViewById(R.id.native_ad_price);
            Button install = nativeAdView.findViewById(R.id.native_ad_call_to_action);
            ImageView icon = nativeAdView.findViewById(R.id.native_ad_icon);

            headLine.setText(nativeAd.getHeadline());
            if (nativeAd.getBody() == null){
                body.setVisibility(View.INVISIBLE);
            }
            else {
                body.setText(nativeAd.getBody());
                body.setVisibility(View.VISIBLE);
            }

            if (nativeAd.getIcon() == null){
                icon.setVisibility(View.GONE);
            }
            else {
                icon.setImageDrawable(nativeAd.getIcon().getDrawable());
                icon.setVisibility(View.VISIBLE);
            }

             if (nativeAd.getPrice() == null){
                price.setVisibility(View.INVISIBLE);
            }
            else {
                price.setText(nativeAd.getPrice());
                price.setVisibility(View.VISIBLE);
            }
            if (nativeAd.getStarRating() == null){
                rating.setVisibility(View.INVISIBLE);
            }
            else {
                rating.setText(String.valueOf(nativeAd.getStarRating().floatValue()));
                rating.setVisibility(View.VISIBLE);
            }
            if (nativeAd.getCallToAction() == null){
                install.setVisibility(View.INVISIBLE);
            }
            else {
                install.setText(nativeAd.getCallToAction());
                install.setVisibility(View.VISIBLE);
            }

            nativeAdView.setNativeAd(nativeAd);

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
