package com.example.visitapp.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.visitapp.BaseApp;
import com.example.visitapp.database.async.Person.CreatePerson;
import com.example.visitapp.database.async.Person.DeletePerson;
import com.example.visitapp.database.async.Person.UpdatePerson;
import com.example.visitapp.database.entity.PersonEntity;
import com.example.visitapp.util.OnAsyncEventListener;

import java.util.List;

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
    public LiveData<PersonEntity> getPerson(final Long personId, Application application){
        return ((BaseApp) application).getDatabase().personDao().getById(personId);
    }
    public LiveData<List<PersonEntity>> getAllPersons(Application application) {
        return ((BaseApp) application).getDatabase().personDao().getAll();
    }

    public LiveData<List<PersonEntity>> getAllEmployees(Application application){
        return ((BaseApp) application).getDatabase().personDao().getAllEmployees();
    }

    public LiveData<List<PersonEntity>> getAllVisitors(Application application){
        return ((BaseApp) application).getDatabase().personDao().getAllVisitors();
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
