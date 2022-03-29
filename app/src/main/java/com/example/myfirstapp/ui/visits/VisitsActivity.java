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

import com.example.myfirstapp.viewmodel.VisitViewModel;
import com.example.myfirstapp.viewmodel.VisitsListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VisitsActivity extends BaseActivity {

    private static final String TAG = "VisitsActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_visits, frameLayout);

        bottomNavigationView.setSelectedItemId(R.id.visits);
        //Factory factory = new PersonListViewModel.Factory(getActivity().getApplication());



        setContentView(R.layout.activity_visits);

        //getLayoutInflater().inflate(R.layout.activity_visits, frameLayout);

        setTitle(getString(R.string.activityTitleVisits));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, new VisitsFragment())
                .commit();


    }
    @Override
    protected void onResume() {
        super.onResume();
        //setTitle(getString(R.string.app_name));
        bottomNavigationView.setSelectedItemId(R.id.visits);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }
}
