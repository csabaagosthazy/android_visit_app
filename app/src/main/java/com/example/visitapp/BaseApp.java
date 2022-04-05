/**
 * Android Application class. Used for accessing singletons.
 */
package com.example.visitapp;

import android.app.Application;

import com.example.visitapp.database.repository.VisitorRepository;
import com.example.visitapp.database.repository.VisitRepository;

public class BaseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public VisitRepository getVisitRepository() {
        return VisitRepository.getInstance();
    }

    public VisitorRepository getVisitorRepository() {
        return VisitorRepository.getInstance();
    }

}