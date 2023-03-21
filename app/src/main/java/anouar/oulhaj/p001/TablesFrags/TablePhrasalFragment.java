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
import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.DialogFragment;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.VerbRecyclerAdapter;


public class TablePhrasalFragment extends Fragment {

    private RecyclerView recycler;
    private DbAccess db;
    private FloatingActionButton fabAdd;


    public TablePhrasalFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phrasal_verbs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler_table_phrasal);
        fabAdd = view.findViewById(R.id.table_phrasal_fab);

        fabAdd.setOnClickListener(v -> {
                DialogFragment dialog = DialogFragment.newInstance("Add a Verb","You can write the verb in fr and eng",R.drawable.ic_question_answer_24);
                dialog.show(requireActivity().getSupportFragmentManager(),null);
        });


        db = DbAccess.getInstance(getActivity());
        db.open_to_read();
        List<Verb> Allverbs = db.getAllVerbs();
        db.close();


        VerbRecyclerAdapter adapter = new VerbRecyclerAdapter(Allverbs, new VerbRecyclerAdapter.onRecyclerListener() {

            @Override
            public void onDataChanged() {
                
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);


    }


}