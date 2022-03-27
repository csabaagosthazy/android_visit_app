/**
 * Android Application class. Used for accessing singletons.
 */
package com.example.myfirstapp;

import android.app.Application;

import com.example.myfirstapp.database.AppDatabase;
import com.example.myfirstapp.database.repository.PersonRepository;
import com.example.myfirstapp.database.repository.VisitRepository;

public class BaseApp extends Application {

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
}