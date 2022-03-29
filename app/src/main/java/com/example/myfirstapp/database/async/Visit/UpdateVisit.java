package com.example.myfirstapp.database.async.Visit;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.AppDatabase;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.util.OnAsyncEventListener;
import com.example.myfirstapp.database.dao.VisitDao;

public class UpdateVisit extends AsyncTask<VisitEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateVisit(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(VisitEntity... params) {
        try {
            for (VisitEntity account : params)
                database.visitDao().update(account);
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
