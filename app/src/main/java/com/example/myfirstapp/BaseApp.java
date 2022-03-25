/**
 * Android Application class. Used for accessing singletons.
 */
package com.example.myfirstapp;

import android.app.Application;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.myfirstapp.database.AppDatabase;
import com.example.myfirstapp.database.repository.PersonRepository;
import com.example.myfirstapp.database.repository.VisitRepository;
import com.google.android.material.navigation.NavigationView;

public class BaseApp extends Application implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public VisitRepository getVisitRepository() {
        return VisitRepository.getInstance();
    }

    public PersonRepository getPersonRepository() {
        return PersonRepository.getInstance();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}