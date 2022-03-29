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

import java.util.Date;
import java.util.List;

public class VisitsByDateViewModel extends AndroidViewModel {
    private VisitRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<VisitEntity>> observableVisits;

    public VisitsByDateViewModel(@NonNull Application application, final Date date, VisitRepository visitRepository) {
        super(application);

        repository = visitRepository;

        applicationContext = application.getApplicationContext();

        observableVisits = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisits.setValue(null);

        LiveData<List<VisitEntity>> visits = repository.getByDate(date, applicationContext);

        // observe the changes of the entities from the database and forward them
        observableVisits.addSource(visits, observableVisits::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        @NonNull
        private final Date date;

        private final VisitRepository visitRepository;

        public Factory(@NonNull Application application, @NonNull Date date) {
            this.application = application;
            this.date = date;
            visitRepository = VisitRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitsByDateViewModel(application, date, visitRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<VisitEntity>> getVisitsByDate() {
        return observableVisits;
    }
}
