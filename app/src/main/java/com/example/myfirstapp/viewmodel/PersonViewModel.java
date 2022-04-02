package com.example.myfirstapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.database.repository.PersonRepository;
import com.example.myfirstapp.util.OnAsyncEventListener;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<PersonEntity> observablePerson;


    public PersonViewModel(@NonNull Application application, final Long personId, PersonRepository personRepository) {
        super(application);

        repository = personRepository;
        this.application=application;

        observablePerson = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observablePerson.setValue(null);

        LiveData<PersonEntity> person = repository.getPerson(personId, application);

        // observe the changes of the client entity from the database and forward them
        observablePerson.addSource(person, observablePerson::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long personId;

        private final PersonRepository repository;

        public Factory(@NonNull Application application, Long personId) {
            this.application = application;
            this.personId = personId;
            repository = ((BaseApp)application).getPersonRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PersonViewModel(application, personId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<PersonEntity> getPerson() {
        return observablePerson;
    }

    public void createPerson(PersonEntity person, OnAsyncEventListener callback) {
        repository.insert(person, callback, application);
    }

    public void updatePerson(PersonEntity person, OnAsyncEventListener callback) {
        repository.update(person, callback, application);
    }

    public void deletePerson(PersonEntity person, OnAsyncEventListener callback) {
        repository.delete(person, callback, application);
    }

}
