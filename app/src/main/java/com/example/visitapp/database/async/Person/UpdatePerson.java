package com.example.visitapp.database.async.Person;

import android.content.Context;
import android.os.AsyncTask;

import com.example.visitapp.database.AppDatabase;
import com.example.visitapp.database.entity.PersonEntity;
import com.example.visitapp.util.OnAsyncEventListener;

public class UpdatePerson extends AsyncTask<PersonEntity, Void, Void> {
    private AppDatabase database;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdatePerson(Context context, OnAsyncEventListener calback) {
        database = AppDatabase.getInstance(context);
        this.calback = calback;
    }

    @Override
    protected Void doInBackground(PersonEntity... params) {
        try {
            for (PersonEntity person : params)
            database.personDao().update(person);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}
