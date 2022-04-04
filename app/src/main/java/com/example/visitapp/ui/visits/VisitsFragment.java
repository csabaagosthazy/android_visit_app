package com.example.visitapp.ui.visits;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitapp.R;
import com.example.visitapp.adapter.RecyclerAdapter;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.util.RecyclerViewItemClickListener;
import com.example.visitapp.viewmodel.VisitsListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisitsFragment#} factory method to
 * create an instance of this fragment.
 */
public class VisitsFragment extends Fragment {

    private List<VisitEntity> visits;
    private RecyclerAdapter recyclerAdapter;
    private VisitsListViewModel viewModel;

    public VisitsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_visits, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.visitsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        visits = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(getActivity(), VisitDetails.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("idVisit", visits.get(position).getIdVisit());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view2 -> {
                    Intent intent = new Intent(getActivity(), VisitDetails.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        VisitsListViewModel.Factory factory = new VisitsListViewModel.Factory(getActivity().getApplication());
        //viewModel = ViewModelProviders.of(this, factory).get(PersonListViewModel.class);
        viewModel = new ViewModelProvider(this, factory).get(VisitsListViewModel.class);


        viewModel.getVisits().observe(getViewLifecycleOwner(), visitEntities -> {
            if (visitEntities != null) {
                visits = visitEntities;
                recyclerAdapter.setData(visits);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }


    }