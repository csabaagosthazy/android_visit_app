package com.example.myfirstapp.database.async.Person;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.AppDatabase;
import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.util.OnAsyncEventListener;

public class CreatePerson extends AsyncTask<PersonEntity,Void,Void> {
    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreatePerson(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(PersonEntity... params) {
        try {
            for (PersonEntity person : params)
                database.personDao().insert(person);
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
