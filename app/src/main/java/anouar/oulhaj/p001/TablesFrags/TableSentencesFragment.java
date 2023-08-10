package anouar.oulhaj.p001.TablesFrags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import anouar.oulhaj.p001.Adapters.SentencesRecyclerAdapter;
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Sentence;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class TableSentencesFragment extends Fragment {

    private RecyclerView recycler;
    private DbAccess db;
  //  private AutoCompleteTextView autoTxt_table;



    public TableSentencesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tables_sentenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler_table_sentences);
        TextView tvHeadTitleVerbs = view.findViewById(R.id.headTitleTableSentences);
        tvHeadTitleVerbs.setText("Table of Sentences (" + Utils.sentencesList.size() + ")");
//        autoTxt_table = view.findViewById(R.id.autoTxt_table);


     /*   db = DbAccess.getInstance(getActivity());
        db.open_to_read();
        List<Sentence> AllSentences = db.getAllSentences();
        db.close();*/

       SentencesRecyclerAdapter adapter = new SentencesRecyclerAdapter(Utils.sentencesList,getActivity(), new SentencesRecyclerAdapter.onRecyclerListener() {
           @Override
           public void onDataChanged() {

           }
       });

       recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);


        /*ArrayAdapter adapter_category = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.categoryOfSentence));*/

      /*  autoTxt_table.setAdapter(adapter_category);
          autoTxt_table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String txt = autoTxt_table.getText().toString();
                switch(txt){
                    case "category 0":
                        adapter.ChangeCategoryList(listSentence0);
                        break;
                    case "category 1":
                        adapter.ChangeCategoryList(listSentences1);
                        break;
                    case "category 2":
                        adapter.ChangeCategoryList(listSentences2);
                        break;

                }
            }
        });*/

    }


}