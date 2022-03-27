package com.example.myfirstapp.database.async.Person;

import android.app.Application;
import android.os.AsyncTask;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.util.OnAsyncEventListener;

public class DeletePerson extends AsyncTask<PersonEntity,Void,Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeletePerson(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(PersonEntity... params) {
        try {
            for (PersonEntity person : params)
                ((BaseApp) application).getDatabase().personDao()
                        .delete(person);
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
