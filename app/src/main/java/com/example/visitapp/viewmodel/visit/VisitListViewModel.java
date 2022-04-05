package com.example.visitapp.viewmodel.visit;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.visitapp.BaseApp;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.database.repository.VisitRepository;

import java.util.List;

public class VisitListViewModel extends AndroidViewModel {
    private Application application;

    private VisitRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<VisitEntity>> observableVisits;

    public VisitListViewModel(@NonNull Application application, final String hostId, VisitRepository visitRepository) {
        super(application);

        this.repository = visitRepository;
        this.application = application;

        observableVisits = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisits.setValue(null);

        LiveData<List<VisitEntity>> visits = repository.getVisitsByHost(hostId);

        // observe the changes of the entities from the database and forward them
        observableVisits.addSource(visits, observableVisits::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final VisitRepository visitRepository;
        private final String hostId;

        public Factory(@NonNull Application application, final String hostId) {
            this.application = application;
            this.hostId = hostId;
            this.visitRepository = ((BaseApp)application).getVisitRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitListViewModel(application, hostId, visitRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<VisitEntity>> getVisits() {
        return observableVisits;
    }
}
