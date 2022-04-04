package com.example.visitapp.database.async.Person;

import android.content.Context;
import android.os.AsyncTask;

import com.example.visitapp.database.AppDatabase;
import com.example.visitapp.database.entity.PersonEntity;
import com.example.visitapp.util.OnAsyncEventListener;

public class DeletePerson extends AsyncTask<PersonEntity,Void,Void> {
    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeletePerson(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(PersonEntity... params) {
        try {
            for (PersonEntity person : params)
            database.personDao().delete(person);
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
