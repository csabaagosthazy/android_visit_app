package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    //Initialize
    BottomNavigationView bottomNavigationView;

    private ListView listView;
    private int digit[] = new int[] {1,2,3,4,5,6,7,8,9,10};
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        //getSupportActionBar().setTitle("My new title"); // set the top title
        setContentView(R.layout.activity_main);
        //list view
        listView = findViewById(R.id.listView);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        //Add Header View
        View headerView = inflater.inflate(R.layout.listview_header, null, false);
        listView.addHeaderView(headerView);//Add view to list view as header view

        listAdapter = new ListAdapter(this,digit);
        listView.setAdapter(listAdapter);

        //create bottom navigation view object
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment person = new Person();
        final Fragment visit = new Visits();
        final Fragment visitor = new Visitor();
        final Fragment history = new History();

        //bottom nav selection
        bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.person:
                                fragment = person;
                                break;
                            case R.id.visitor:
                                fragment = visitor;
                                break;
                            case R.id.history:
                                fragment = history;
                                break;
                            default:
                                fragment = visit;
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit();
                        return true;
                    }
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.personFragment);

    }

    private void setAppLocale(String language){
        Locale locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);

        res.updateConfiguration(config, displayMetrics);

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        final MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);

        // referencing button views
        MenuItem btnLanguage = menu.findItem(R.id.btnLang);

        // setting up on click listener event over the button
        // in order to change the language with the help of
        // LocaleHelper class
      /*  btnLanguage.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String lang = Locale.getDefault().getLanguage();
                Log.d("language",lang);
                String newLang = "en";
                if(lang.equals("en")) newLang = "fr";
                if(lang.equals("fr")) newLang = "en";

                Log.d("new language",newLang);
                setAppLocale(newLang);
                return false;
            }
        });*/

        return true;
    }

  @Override
    public boolean onOptionsItemSelected(MenuItem item){
      int id = item.getItemId();

      if (id == R.id.btnLang) {
          String lang = Locale.getDefault().getLanguage();
          Log.d("language",lang);
          String newLang = "en";
          if(lang.equals("en")) newLang = "fr";
          if(lang.equals("fr")) newLang = "en";

          Log.d("new language",newLang);
          setAppLocale(newLang);
          return true;
      }

      return super.onOptionsItemSelected(item);

    }
}