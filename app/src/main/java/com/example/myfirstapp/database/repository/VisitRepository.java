package com.example.myfirstapp.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.async.Visit.CreateVisit;
import com.example.myfirstapp.database.async.Visit.DeleteVisit;
import com.example.myfirstapp.database.async.Visit.UpdateVisit;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.util.OnAsyncEventListener;

import java.util.List;

public class VisitRepository {
    private static VisitRepository instance;

    private VisitRepository(){

    }
    //singleton who return only 1 instance of VisitRepository
    public static VisitRepository getInstance() {
        if ((instance == null)) {

            synchronized (VisitRepository.class) {
                if (instance == null) {
                    instance = new VisitRepository();
                }
            }

        }
        return instance;
    }
    public LiveData<VisitEntity> getVisit(final Long visitId, Application application){
        return ((BaseApp) application).getDatabase().visitDao().getById(visitId);
    }
    public LiveData<List<VisitEntity>> getVisits(Application application) {
        return ((BaseApp) application).getDatabase().visitDao().getAll();
    }

    public void insert(final VisitEntity visit, OnAsyncEventListener callback,
                       Application application) {
        new CreateVisit(application, callback).execute(visit);
    }

    public void update(final VisitEntity visit, OnAsyncEventListener callback,
                       Application application) {
        new UpdateVisit(application, callback).execute(visit);
    }

    public void delete(final VisitEntity visit, OnAsyncEventListener callback,
                       Application application) {
        new DeleteVisit(application, callback).execute(visit);
    }

}