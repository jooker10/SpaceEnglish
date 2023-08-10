package anouar.oulhaj.p001.TablesFrags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anouar.oulhaj.p001.Adapters.AdjectivesRecyclerAdapter;
import anouar.oulhaj.p001.Adapters.PhrasalRecyclerAdapter;
import anouar.oulhaj.p001.DB.Adjective;
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Phrasal;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class TableAdjectivesFragment extends Fragment {

    private RecyclerView recycler;
    private DbAccess db;



    public TableAdjectivesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table_adjectives, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler_table_adjectives);
        TextView tvHeadTitleVerbs = view.findViewById(R.id.headTitleTableAdjs);
        tvHeadTitleVerbs.setText("Table of Adjectives (" + Utils.adjsList.size() + ")");

     /*   db = DbAccess.getInstance(getActivity());
        db.open_to_read();
        List<Adjective> AllAdjs = db.getAllAdjs();
        db.close();
*/

        AdjectivesRecyclerAdapter adapter = new AdjectivesRecyclerAdapter(Utils.adjsList, getActivity(), new AdjectivesRecyclerAdapter.onRecyclerListener() {
            @Override
            public void onDataChanged() {

            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);

    }


}