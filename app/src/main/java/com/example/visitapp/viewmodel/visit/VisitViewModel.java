package com.example.visitapp.viewmodel.visit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.visitapp.BaseApp;
import com.example.visitapp.database.entity.VisitorEntity;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.database.repository.VisitorRepository;
import com.example.visitapp.database.repository.VisitRepository;
import com.example.visitapp.util.OnAsyncEventListener;

import java.util.List;

public class VisitViewModel extends AndroidViewModel {
    private Application application;
    private VisitRepository repository;
    private VisitorRepository visitorRepository;


    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<VisitEntity> observableVisit;
    private final MediatorLiveData<List<VisitorEntity>> observableVisitors;

    public VisitViewModel(@NonNull Application application,
                           final String visitId, VisitRepository visitRepository, VisitorRepository visitorRepository) {
        super(application);

        repository = visitRepository;
        this.visitorRepository = visitorRepository;
        this.application = application;

        observableVisit = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisit.setValue(null);

        if(visitId != null){
            LiveData<VisitEntity> visit = repository.getVisit(visitId);

            // observe the changes of the client entity from the database and forward them
            observableVisit.addSource(visit, observableVisit::setValue);

        }

        observableVisitors = new MediatorLiveData<>();
        observableVisitors.setValue(null);

        LiveData<List<VisitorEntity>> visitors = visitorRepository.getAllVisitors();
        observableVisitors.addSource(visitors, observableVisitors::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String idVisit;

        private final VisitRepository visitRepository;
        private final VisitorRepository visitorRepository;

        public Factory(@NonNull Application application, String visitId) {
            this.application = application;
            this.idVisit = visitId;
            this.visitRepository = ((BaseApp)application).getVisitRepository();
            this.visitorRepository = ((BaseApp)application).getVisitorRepository();

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitViewModel(application, idVisit, visitRepository, visitorRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<VisitEntity> getVisit() {
        return observableVisit;
    }

    public LiveData<List<VisitorEntity>> getVisitors(){return observableVisitors;}

    public void createVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.insert(visit, callback);
    }

    public void updateVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.update(visit, callback);
    }

    public void deleteVisit(VisitEntity visit, OnAsyncEventListener callback) {
        repository.delete(visit, callback);
    }
}

