package com.example.visitapp.viewmodel.visit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.visitapp.BaseApp;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.database.repository.VisitRepository;

import java.util.List;

public class VisitListByDateViewModel extends AndroidViewModel {
    private Application application;
    private VisitRepository repository;
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<VisitEntity>> observableVisits;

    public VisitListByDateViewModel(@NonNull Application application, final String hostId, final Long from, final Long to, VisitRepository visitRepository) {
        super(application);

        this.repository = visitRepository;
        this.application = application;


        observableVisits = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisits.setValue(null);

        LiveData<List<VisitEntity>> visits = repository.getVisitsByHostAndDate(hostId, from,to );

        // observe the changes of the entities from the database and forward them
        observableVisits.addSource(visits, observableVisits::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final Long from;
        private final Long to;
        private final String hostId;
        private final VisitRepository visitRepository;

        public Factory(@NonNull Application application,String hostId, Long from, Long to) {
            this.application = application;
            this.from = from;
            this.to = to;
            this.hostId = hostId;
            this.visitRepository = ((BaseApp)application).getVisitRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitListByDateViewModel(application,hostId, from,to, visitRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<VisitEntity>> getVisitsByDate() {
        return observableVisits;
    }
}
