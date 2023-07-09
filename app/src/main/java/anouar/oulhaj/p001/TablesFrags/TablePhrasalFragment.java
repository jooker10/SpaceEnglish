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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Phrasal;
import anouar.oulhaj.p001.DialogFragment;
import anouar.oulhaj.p001.Adapters.PhrasalRecyclerAdapter;
import anouar.oulhaj.p001.R;


public class TablePhrasalFragment extends Fragment {

    private RecyclerView recycler;
    private DbAccess db;



    public TablePhrasalFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table_phrasal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler_table_phrasal);





        db = DbAccess.getInstance(getActivity());
        db.open_to_read();
        List<Phrasal> AllPhrasal = db.getAllPhrasal();
        db.close();


        PhrasalRecyclerAdapter adapter = new PhrasalRecyclerAdapter(AllPhrasal,getActivity(), new PhrasalRecyclerAdapter.onRecyclerListener() {
            @Override
            public void onDataChanged() {

            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);

    }


}