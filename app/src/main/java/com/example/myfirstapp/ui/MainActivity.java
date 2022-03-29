package com.example.myfirstapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;

import com.example.myfirstapp.R;

public class MainActivity extends BaseActivity{
    String[] currentVisits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        setTitle(getString(R.string.app_name));
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.nav_none);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment,new MainFragment())
                .commit();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d("Conf", "Changed");
    }

}