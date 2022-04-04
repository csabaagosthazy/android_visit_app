package com.example.visitapp.database.async.Visit;

import android.content.Context;
import android.os.AsyncTask;

import com.example.visitapp.database.AppDatabase;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.util.OnAsyncEventListener;

public class DeleteVisit extends AsyncTask<VisitEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteVisit(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(VisitEntity... params) {
        try {
            for (VisitEntity account : params)
                database.visitDao()
                        .delete(account);
        } catch (Exception e) {
            exception = e;
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