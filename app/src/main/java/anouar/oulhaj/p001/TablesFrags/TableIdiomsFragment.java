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

import anouar.oulhaj.p001.Adapters.IdiomsRecyclerAdapter;
import anouar.oulhaj.p001.Adapters.PhrasalRecyclerAdapter;
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Idiom;
import anouar.oulhaj.p001.DB.Phrasal;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class TableIdiomsFragment extends Fragment {

    private RecyclerView recycler;
    private DbAccess db;



    public TableIdiomsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table_idioms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler_table_idioms);
        TextView tvHeadTitleVerbs = view.findViewById(R.id.headTitleTableIdiom);
        tvHeadTitleVerbs.setText("Table of Idioms (" + Utils.idiomsList.size() + ")");

     /*   db = DbAccess.getInstance(getActivity());
        db.open_to_read();
        List<Idiom> AllIdioms = db.getAllIdioms();
        db.close();
*/

        IdiomsRecyclerAdapter adapter = new IdiomsRecyclerAdapter(Utils.idiomsList, getActivity(), new IdiomsRecyclerAdapter.onRecyclerListener() {
            @Override
            public void onDataChanged() {

            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);

    }


}