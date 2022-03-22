package com.example.myfirstapp.database.async.Person;

import android.app.Application;
import android.os.AsyncTask;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.util.OnAsyncEventListener;

public class UpdatePerson extends AsyncTask<PersonEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdatePerson(Application application, OnAsyncEventListener calback) {
        this.application = application;
        this.calback = calback;
    }

    @Override
    protected Void doInBackground(PersonEntity... params) {
        try {
            for (PersonEntity client : params)
                ((BaseApp) application).getDatabase().personDao()
                        .update(client);
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
