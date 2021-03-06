package com.example.visitapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visitapp.ui.visitor.VisitorActivity;
import com.example.visitapp.ui.settings.SettingsActivity;
import com.example.visitapp.ui.visits.VisitsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.example.visitapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/**
 * Base activity with bottom navigation
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

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
        return false;
    }
    //bottom nav selection
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == BaseActivity.position) {
            return true;
        }

        BaseActivity.position = id;
        Intent intent = null;

        switch (id) {
            case R.id.home:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                break;
            case R.id.person:
                intent = new Intent(getApplicationContext(), VisitorActivity.class);
                break;
            case R.id.settings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                break;
            case R.id.visits:
                intent = new Intent(getApplicationContext(), VisitsActivity.class);
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


    private void setAppLocale(String language){
        locale = new Locale(language);
        Resources res = getResources();
        Configuration config = res.getConfiguration();

        Locale.setDefault(locale);
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
