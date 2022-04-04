package com.example.myfirstapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirstapp.BaseApp;
import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.database.repository.PersonRepository;

import java.util.List;

public class PersonListViewModel extends AndroidViewModel {
    private PersonRepository repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<PersonEntity>> observablePersons;

    public PersonListViewModel(@NonNull Application application, PersonRepository personRepository) {
        super(application);

        this.repository = personRepository;
        this.application = application;

        observablePersons = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observablePersons.setValue(null);

        LiveData<List<PersonEntity>> persons = repository.getAllPersons(application);

        // observe the changes of the entities from the database and forward them
        observablePersons.addSource(persons, observablePersons::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final PersonRepository personRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.personRepository = ((BaseApp)application).getPersonRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PersonListViewModel(application, personRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<PersonEntity>> getPersons() {
        return observablePersons;
    }
}


