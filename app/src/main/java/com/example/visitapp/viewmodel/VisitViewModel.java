package com.example.visitapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.visitapp.BaseApp;
import com.example.visitapp.database.entity.PersonEntity;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.database.repository.PersonRepository;
import com.example.visitapp.database.repository.VisitRepository;
import com.example.visitapp.util.OnAsyncEventListener;

import java.util.List;

public class VisitViewModel extends AndroidViewModel {
    private Application application;
    private VisitRepository repository;
    private PersonRepository personRepository;


    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<VisitEntity> observableVisit;
    private final MediatorLiveData<List<PersonEntity>> observablePersons, observableVisitors;

    public VisitViewModel(@NonNull Application application,
                           final Long idVisit, VisitRepository visitRepository, PersonRepository personRepository) {
        super(application);

        repository = visitRepository;
        this.personRepository = personRepository;
        this.application = application;

        observableVisit = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisit.setValue(null);

        LiveData<VisitEntity> visit = repository.getVisit(idVisit, application);

        // observe the changes of the client entity from the database and forward them
        observableVisit.addSource(visit, observableVisit::setValue);

        observablePersons = new MediatorLiveData<>();
        observablePersons.setValue(null);

        LiveData<List<PersonEntity>> employees = personRepository.getAllEmployees(application);
        observablePersons.addSource(employees, observablePersons::setValue);

        observableVisitors = new MediatorLiveData<>();
        observableVisitors.setValue(null);

        LiveData<List<PersonEntity>> visitors = personRepository.getAllVisitors(application);
        observableVisitors.addSource(visitors, observableVisitors::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long idVisit;

        private final VisitRepository visitRepository;
        private final PersonRepository personRepository;

        public Factory(@NonNull Application application, Long idVisit) {
            this.application = application;
            this.idVisit = idVisit;
            this.visitRepository = ((BaseApp)application).getVisitRepository();
            this.personRepository = ((BaseApp)application).getPersonRepository();

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitViewModel(application, idVisit, visitRepository,personRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<VisitEntity> getVisit() {
        return observableVisit;
    }

    public LiveData<List<PersonEntity>> getEmployees(){return observablePersons;}

    public LiveData<List<PersonEntity>> getVisitors(){return observableVisitors;}

    public void createVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.insert(visit, callback, application);
    }

    public void updateVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.update(visit, callback, application);
    }

    public void deleteVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.delete(visit, callback, application);
    }
}

