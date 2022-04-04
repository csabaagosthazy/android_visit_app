package com.example.myfirstapp.ui.person;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.RecyclerAdapter;
import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.ui.MainActivity;
import com.example.myfirstapp.util.RecyclerViewItemClickListener;
import com.example.myfirstapp.viewmodel.PersonListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersonsList extends Fragment  {
    private static final String TAG = "PersonListFragment";

    private List<PersonEntity> persons;
    private RecyclerAdapter recyclerAdapter;
    private PersonListViewModel viewModel;
    private Locale locale;

    public PersonsList() {

    }
    public static PersonsList newInstance() {
        PersonsList fragment = new PersonsList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_persons_list, container, false);
        // Inflate the layout for this fragment
        RecyclerView recyclerView = view.findViewById(R.id.personsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        persons = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + persons.get(position).toString());

                Intent intent = new Intent(getActivity(), PersonDetails.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("personId", persons.get(position).getIdPerson());
                ((MainActivity) getActivity()).startActivity(intent);
            }
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + persons.get(position).toString());
            }
            });
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view2 -> {
                    Intent intent = new Intent(getActivity(), PersonDetails.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        PersonListViewModel.Factory factory = new PersonListViewModel.Factory(getActivity().getApplication());
        //viewModel = ViewModelProviders.of(this, factory).get(PersonListViewModel.class);
        viewModel = new ViewModelProvider(this, factory).get(PersonListViewModel.class);
        viewModel.getPersons().observe(getViewLifecycleOwner(), personEntities -> {
            if (personEntities != null) {
                persons = personEntities;
                recyclerAdapter.setData(persons);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }


    private void setAppLocale(String language){
        locale = new Locale(language);
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        //DisplayMetrics displayMetrics = res.getDisplayMetrics();

        Locale.setDefault(locale);
        config.setLocale(locale);
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());

    }


}
