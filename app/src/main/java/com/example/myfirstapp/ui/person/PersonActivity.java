package com.example.myfirstapp.ui.person;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.ListAdapter;
import com.example.myfirstapp.ui.BaseActivity;

import java.util.Locale;

public class PersonActivity extends BaseActivity {


    private ListView listView;
    private int digit[] = new int[] {1,2,3,4,5,6,7,8,9,10};
    private ListAdapter listAdapter;
    private Locale locale;
    final Fragment person = new PersonsList();
    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_persons_list, frameLayout);

        setTitle(getString(R.string.app_name));
        bottomNavigationView.setSelectedItemId(R.id.person);
        fragmentManager.beginTransaction().replace(R.id.flFragment, person).commit();



    }
    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.app_name));
        bottomNavigationView.setSelectedItemId(R.id.person);
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
