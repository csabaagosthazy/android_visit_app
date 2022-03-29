package com.example.myfirstapp.ui.visits;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.R;

import com.example.myfirstapp.adapter.RecyclerAdapter;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.ui.BaseActivity;

import com.example.myfirstapp.util.RecyclerViewItemClickListener;

import com.example.myfirstapp.viewmodel.VisitsListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisitsActivity extends BaseActivity {

    private static final String TAG = "VisitsActivity";

    private List<VisitEntity> visits;
    private RecyclerAdapter recyclerAdapter;
    private VisitsListViewModel viewModel;

    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_visits, frameLayout);

        bottomNavigationView.setSelectedItemId(R.id.visits);
        //Factory factory = new PersonListViewModel.Factory(getActivity().getApplication());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, new Visits())
                .commit();


        setContentView(R.layout.activity_visits);

        //getLayoutInflater().inflate(R.layout.activity_visits, frameLayout);

        setTitle(getString(R.string.activityTitleVisits));

        RecyclerView recyclerView = findViewById(R.id.visitsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        visits = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + visits.get(position).toString());

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
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + visits.get(position).toString());
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
                recyclerAdapter.setVisitData(visits);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        bottomNavigationView.setSelectedItemId(R.id.visits);

        //list view
        //listView = findViewById(R.id.listView);
        //LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        // Set default selection
        //bottomNavigationView.setSelectedItemId(R.id.nav_none);

    }
    @Override
    protected void onResume() {
        super.onResume();
        //setTitle(getString(R.string.app_name));
        bottomNavigationView.setSelectedItemId(R.id.visits);
    }

    private void setAppLocale(String language){
        locale = new Locale(language);
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        //DisplayMetrics displayMetrics = res.getDisplayMetrics();

        Locale.setDefault(locale);
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d("Conf", "Changed");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }
}
