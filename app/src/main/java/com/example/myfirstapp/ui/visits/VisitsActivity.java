package com.example.myfirstapp.ui.visits;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.RecyclerAdapter;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.ui.BaseActivity;
import com.example.myfirstapp.util.RecyclerViewItemClickListener;
import com.example.myfirstapp.viewmodel.VisitsListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VisitsActivity extends BaseActivity {

    private static final String TAG = "VisitsActivity";

    private List<VisitEntity> visits;
    private RecyclerAdapter<VisitEntity> recyclerAdapter;
    private VisitsListViewModel viewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "created");
        getLayoutInflater().inflate(R.layout.activity_visits, frameLayout);
        setTitle(getString(R.string.activityTitleVisits));

        bottomNavigationView.setSelectedItemId(R.id.visits);

        RecyclerView recyclerView = findViewById(R.id.visitsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        visits = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(VisitsActivity.this, VisitDetails.class);
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

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view2 -> {
                    Intent intent = new Intent(VisitsActivity.this, VisitDetails.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        VisitsListViewModel.Factory factory = new VisitsListViewModel.Factory(getApplication());
        //viewModel = ViewModelProviders.of(this, factory).get(PersonListViewModel.class);
        viewModel = new ViewModelProvider(this, factory).get(VisitsListViewModel.class);


        viewModel.getVisits().observe(this, visitEntities -> {
            if (visitEntities != null) {
                visits = visitEntities;
                recyclerAdapter.setData(visits);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);


    }
/*    @Override
    protected void onResume() {
        super.onResume();
        //setTitle(getString(R.string.app_name));
        bottomNavigationView.setSelectedItemId(R.id.visits);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }
}
