package com.example.visitapp.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.visitapp.R;
import com.example.visitapp.ui.BaseActivity;
import com.example.visitapp.ui.MainActivity;

public class SettingsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);

        setTitle(getString(R.string.app_name));
        bottomNavigationView.setSelectedItemId(R.id.settings);

        getFragmentManager().beginTransaction()
                .replace(R.id.flFragment,new SettingsFragment())
                .commit();


    }
/*    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.app_name));
        bottomNavigationView.setSelectedItemId(R.id.settings);
    }*/

    @Override
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
