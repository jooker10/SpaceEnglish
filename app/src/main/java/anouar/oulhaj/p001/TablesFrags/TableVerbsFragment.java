package anouar.oulhaj.p001.TablesFrags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anouar.oulhaj.p001.Adapters.VerbRecyclerAdapter;
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.R;


public class TableVerbsFragment extends Fragment {


    public TableVerbsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tables_verbs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = view.findViewById(R.id.recycler_table_verbs);

        DbAccess db = DbAccess.getInstance(getActivity());
        db.open_to_read();
        List<Verb> allVerbs = db.getAllVerbs();
        db.close();


        VerbRecyclerAdapter adapter = new VerbRecyclerAdapter(allVerbs,getActivity(), new VerbRecyclerAdapter.onRecyclerListener() {

            @Override
            public void onDataChanged() {
                
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);


    }


}