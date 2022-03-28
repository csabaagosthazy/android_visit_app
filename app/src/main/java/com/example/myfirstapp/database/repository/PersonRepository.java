package com.example.myfirstapp.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.AppDatabase;
import com.example.myfirstapp.database.async.Person.CreatePerson;
import com.example.myfirstapp.database.async.Person.DeletePerson;
import com.example.myfirstapp.database.async.Person.UpdatePerson;
import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.util.OnAsyncEventListener;

import java.util.List;

import com.example.myfirstapp.ui.person.PersonsList;

public class PersonRepository {
    private static PersonRepository instance;
    private PersonRepository(){

    }
    //singleton who return only 1 instance of PersonRepository
    public static PersonRepository getInstance(){
        if (instance == null){
            synchronized (VisitRepository.class){
                if (instance == null){
                    instance = new PersonRepository();
                }
            }
        }
        return instance;
    }
    public LiveData<PersonEntity> getPerson(final Long personId, Context context){
        return AppDatabase.getInstance(context).personDao().getById(personId);
    }

    public LiveData<List<PersonEntity>> getAllClients(Context context) {
        return AppDatabase.getInstance(context).personDao().getAll();
    }

    public void insert(final PersonEntity person, OnAsyncEventListener callback,
                       Application application) {
        new CreatePerson(application, callback).execute(person);
    }

    public void update(final PersonEntity person, OnAsyncEventListener callback,
                       Application application) {
        new UpdatePerson(application, callback).execute(person);
    }

    public void delete(final PersonEntity person, OnAsyncEventListener callback,
                       Application application) {
        new DeletePerson(application, callback).execute(person);
    }
}
