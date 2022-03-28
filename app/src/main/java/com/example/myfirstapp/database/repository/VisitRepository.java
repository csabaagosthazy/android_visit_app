package com.example.myfirstapp.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.AppDatabase;
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
    public LiveData<VisitEntity> getVisit(final Long visitId, Context context){
        return AppDatabase.getInstance(context).visitDao().getById(visitId);
    }
    public LiveData<List<VisitEntity>> getVisits(Context context) {
        return AppDatabase.getInstance(context).visitDao().getAll();
    }

    public LiveData<List<VisitEntity>> getByDate(Context context) {
        return AppDatabase.getInstance(context).visitDao().getAll();
    }

    public void insert(final VisitEntity visit, OnAsyncEventListener callback,
                       Context context) {
        new CreateVisit(context, callback).execute(visit);
    }

    public void update(final VisitEntity visit, OnAsyncEventListener callback,
                       Context context) {
        new UpdateVisit(context, callback).execute(visit);
    }

    public void delete(final VisitEntity visit, OnAsyncEventListener callback,
                       Context context) {
        new DeleteVisit(context, callback).execute(visit);
    }

}
