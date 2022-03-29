package com.example.myfirstapp.database.async.Person;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.AppDatabase;
import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.util.OnAsyncEventListener;

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
