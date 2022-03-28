package com.example.myfirstapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.database.repository.VisitRepository;
import com.example.myfirstapp.util.OnAsyncEventListener;

public class VisitViewModel extends AndroidViewModel {
    private VisitRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<VisitEntity> observableVisit;

    public VisitViewModel(@NonNull Application application,
                           final Long idVisit, VisitRepository visitRepository) {
        super(application);

        repository = visitRepository;

        applicationContext = application.getApplicationContext();

        observableVisit = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisit.setValue(null);

        LiveData<VisitEntity> visit = repository.getVisit(idVisit, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableVisit.addSource(visit, observableVisit::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long idVisit;

        private final VisitRepository repository;

        public Factory(@NonNull Application application, Long idVisit) {
            this.application = application;
            this.idVisit = idVisit;
            repository = VisitRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitViewModel(application, idVisit, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<VisitEntity> getVisit() {
        return observableVisit;
    }

    public void createVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.insert(visit, callback, applicationContext);
    }

    public void updateClient(VisitEntity visit, OnAsyncEventListener callback) {
        repository.update(visit, callback, applicationContext);
    }

    public void deleteClient(VisitEntity visit, OnAsyncEventListener callback) {
        repository.delete(visit, callback, applicationContext);
    }
}

