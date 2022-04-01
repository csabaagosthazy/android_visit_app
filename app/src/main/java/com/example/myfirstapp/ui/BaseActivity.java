package com.example.myfirstapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myfirstapp.ui.person.PersonActivity;
import com.example.myfirstapp.ui.person.PersonsList;
import com.example.myfirstapp.ui.settings.SettingsActivity;
import com.example.myfirstapp.ui.visitor.VisitorsActivity;
import com.example.myfirstapp.ui.visits.VisitsActivity;
import com.example.myfirstapp.ui.visits.VisitsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.example.myfirstapp.R;

import java.util.Locale;

/**
 * Base activity with bottom navigation
 */
public class BaseActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    //Using frame layout
    protected FrameLayout frameLayout;

    //Bottom navigation
    protected BottomNavigationView bottomNavigationView;

    //using locale
    private Locale locale;

    protected static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        frameLayout = findViewById(R.id.flFragment);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setAppLocale(sharedPrefs.getString("pref_lang", "en"));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        BaseActivity.position = 0;
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }
    //bottom nav selection
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment person = new PersonsList();
        final Fragment visit = new VisitsFragment();

        int id = item.getItemId();
        if (id == BaseActivity.position) {
            return true;
        }

        BaseActivity.position = id;
        Intent intent = null;

        switch (id) {
            case R.id.person:
                intent = new Intent(this, PersonActivity.class);
                break;
            case R.id.visitor:
                intent = new Intent(this, VisitorsActivity.class);
                break;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.visits:
                intent = new Intent(this, VisitsActivity.class);
                break;
        }
        //later we can log out
        if (intent != null) {
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
            );
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.infoHeader);
        alertDialog.setCancelable(false);
        String s1 = "<b>" + "App Created by"+ "</b>";
        String s2 = "Loan Rey";
        String s3 = "Csaba Agosthazy";
        Spanned strMessage = Html.fromHtml(s1+  "<br>" + s2 +  "<br>" + s3);
        alertDialog.setMessage(strMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Great job", (dialog, which) -> alertDialog.dismiss());


        int id = item.getItemId();
        if (id == R.id.btnAbout) {
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);

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
}
