package com.example.visitapp.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitapp.R;
import com.example.visitapp.adapter.RecyclerAdapter;
import com.example.visitapp.database.entity.PersonEntity;
import com.example.visitapp.ui.BaseActivity;
import com.example.visitapp.ui.MainActivity;
import com.example.visitapp.util.RecyclerViewItemClickListener;
import com.example.visitapp.viewmodel.PersonListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends BaseActivity {

    private static final String TAG = "PersonActivity";

    private List<PersonEntity> persons;
    private RecyclerAdapter<PersonEntity> recyclerAdapter;
    private PersonListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "created");
        getLayoutInflater().inflate(R.layout.activity_person, frameLayout);

        setTitle(getString(R.string.activityTitlePerson));
        bottomNavigationView.setSelectedItemId(R.id.person);
        //fragmentManager.beginTransaction().replace(R.id.flFragment, person).commit();

        //View view = getLayoutInflater().inflate(R.layout.activity_person, frameLayout);
        // Inflate the layout for this fragment
        RecyclerView recyclerView = findViewById(R.id.personsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        persons = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + persons.get(position).toString());

                Intent intent = new Intent(PersonActivity.this, PersonDetails.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("personId", persons.get(position).getIdPerson());
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + persons.get(position).toString());
            }
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view2 -> {
                    Intent intent = new Intent(PersonActivity.this, PersonDetails.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        PersonListViewModel.Factory factory = new PersonListViewModel.Factory(getApplication());
        //viewModel = ViewModelProviders.of(this, factory).get(PersonListViewModel.class);
        viewModel = new ViewModelProvider(this, factory).get(PersonListViewModel.class);
        viewModel.getPersons().observe(this, personEntities -> {
            if (personEntities != null) {
                persons = personEntities;
                recyclerAdapter.setData(persons);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "resumed");
        setTitle(getString(R.string.activityTitlePerson));
        bottomNavigationView.setSelectedItemId(R.id.person);
    }*/
/*    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == BaseActivity.position) {
            return false;
        }
        finish();
        return super.onNavigationItemSelected(item);
    }*/

    public void onBackPressed() {

        finish();
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }



}
