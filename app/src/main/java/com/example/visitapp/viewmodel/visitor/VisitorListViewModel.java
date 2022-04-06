package com.example.visitapp.viewmodel.visitor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.visitapp.BaseApp;
import com.example.visitapp.database.entity.VisitorEntity;
import com.example.visitapp.database.repository.VisitorRepository;

import java.util.List;

public class VisitorListViewModel extends AndroidViewModel {
    private VisitorRepository repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<VisitorEntity>> observableVisitors;

    public VisitorListViewModel(@NonNull Application application, VisitorRepository visitorRepository) {
        super(application);

        this.repository = visitorRepository;
        this.application = application;

        observableVisitors = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableVisitors.setValue(null);

        LiveData<List<VisitorEntity>> persons = repository.getAllVisitors();

        // observe the changes of the entities from the database and forward them
        observableVisitors.addSource(persons, observableVisitors::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final VisitorRepository visitorRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.visitorRepository = ((BaseApp)application).getVisitorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitorListViewModel(application, visitorRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<VisitorEntity>> getPersons() {
        return observableVisitors;
    }
}


