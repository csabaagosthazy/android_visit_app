package com.example.myfirstapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.database.repository.PersonRepository;
import com.example.myfirstapp.database.repository.VisitRepository;
import com.example.myfirstapp.util.OnAsyncEventListener;

import java.util.List;

public class VisitViewModel extends AndroidViewModel {
    private VisitRepository repository;
    private PersonRepository personRepository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<VisitEntity> observableVisit;
    private final MediatorLiveData<List<PersonEntity>> observablePerson, observableVisitor;

    public VisitViewModel(@NonNull Application application,
                           final Long idVisit, VisitRepository visitRepository, PersonRepository personRepository) {
        super(application);

        repository = visitRepository;
        this.personRepository = personRepository;

        applicationContext = application.getApplicationContext();

        observableVisit = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisit.setValue(null);

        LiveData<VisitEntity> visit = repository.getVisit(idVisit, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableVisit.addSource(visit, observableVisit::setValue);

        observablePerson = new MediatorLiveData<>();
        observablePerson.setValue(null);

        LiveData<List<PersonEntity>> employees = personRepository.getAllEmployees(applicationContext);
        observablePerson.addSource(employees, observablePerson::setValue);

        observableVisitor = new MediatorLiveData<>();
        observableVisitor.setValue(null);

        LiveData<List<PersonEntity>> visitors = personRepository.getAllVisitors(applicationContext);
        observableVisitor.addSource(visitors, observableVisitor::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long idVisit;

        private final VisitRepository repository;
        private final PersonRepository personRepository;

        public Factory(@NonNull Application application, Long idVisit) {
            this.application = application;
            this.idVisit = idVisit;
            repository = VisitRepository.getInstance();
            this.personRepository = PersonRepository.getInstance();

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitViewModel(application, idVisit, repository,personRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<VisitEntity> getVisit() {
        return observableVisit;
    }

    public LiveData<List<PersonEntity>> getEmployees(){return observablePerson;}

    public LiveData<List<PersonEntity>> getVisitors(){return observableVisitor;}

    public void createVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.insert(visit, callback, applicationContext);
    }

    public void updateVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.update(visit, callback, applicationContext);
    }

    public void deleteVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.delete(visit, callback, applicationContext);
    }
}

