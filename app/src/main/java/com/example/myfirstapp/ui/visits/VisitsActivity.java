package com.example.myfirstapp.ui.visits;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.ListAdapter;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.ui.BaseActivity;
import com.example.myfirstapp.ui.MainActivity;
import com.example.myfirstapp.util.RecyclerViewItemClickListener;
import com.example.myfirstapp.viewmodel.VisitsListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisitsActivity extends BaseActivity {
    private static final String TAG = "VisitsActivity";
    private ListAdapter listAdapter;
    private Locale locale;

    private List<VisitEntity> visits;
    private RecyclerAdapter recyclerAdapter;
    private VisitsListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visits);

        getLayoutInflater().inflate(R.layout.activity_visits, frameLayout);

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

        bottomNavigationView.setSelectedItemId(R.id.visits);

        //Get phone default language
        String lang = Locale.getDefault().getLanguage();
        //not en or fr, set to en
        if(!lang.equals("en") || !lang.equals("fr") ) lang = "en";
        setAppLocale(lang);

        //list view
        listView = findViewById(R.id.listView);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);



        // Set default selection
        //bottomNavigationView.setSelectedItemId(R.id.nav_none);

    }
    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.app_name));
        bottomNavigationView.setSelectedItemId(R.id.visits);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle(getString(R.string.action_logout));
        //just a copy
        alertDialog.setTitle("test dialog");
        alertDialog.setCancelable(false);
        //alertDialog.setMessage(getString(R.string.logout_msg));
        alertDialog.setMessage("test message");
        //alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_logout), (dialog, which) -> logout());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialog, which) -> alertDialog.dismiss());
        //alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "cancel", (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
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

        if (id == R.id.btnLang) {
            String lang = locale.getLanguage();
            Log.d("language",lang);
            String newLang = "en";
            if(lang.equals("en")) newLang = "fr";
            else if(lang.equals("fr")) newLang = "en";

            Log.d("new language",newLang);
            setAppLocale(newLang);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
