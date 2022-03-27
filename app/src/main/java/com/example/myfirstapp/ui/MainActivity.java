package com.example.myfirstapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.ListAdapter;
import com.example.myfirstapp.ui.person.Person_old;
import com.example.myfirstapp.ui.person.PersonsList;
import com.example.myfirstapp.ui.settings.Settings;
import com.example.myfirstapp.ui.visitor.Visitor;
import com.example.myfirstapp.ui.visits.Visits;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;

public class MainActivity extends BaseActivity{


    private ListView listView;
    private int digit[] = new int[] {1,2,3,4,5,6,7,8,9,10};
    private ListAdapter listAdapter;
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        setTitle(getString(R.string.app_name));
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.nav_none);

        //Get phone default language
        String lang = Locale.getDefault().getLanguage();
        //not en or fr, set to en
        if(!lang.equals("en") || !lang.equals("fr") ) lang = "en";
        setAppLocale(lang);

        //list view
        listView = findViewById(R.id.listView);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        //Add Header View
        View headerView = inflater.inflate(R.layout.listview_header, null, false);
        TextView headerText = headerView.findViewById(R.id.listHeader);
        headerText.setText(R.string.mainListHeader);
        listView.addHeaderView(headerView);//Add view to list view as header view

        listAdapter = new ListAdapter(this,digit);
        listView.setAdapter(listAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        //setTitle(getString(R.string.app_name));
        //bottomNavigationView.setSelectedItemId(R.id.nav_none);
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
