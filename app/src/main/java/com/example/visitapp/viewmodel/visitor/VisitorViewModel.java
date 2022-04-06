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
import com.example.visitapp.util.OnAsyncEventListener;

public class VisitorViewModel extends AndroidViewModel {
    private VisitorRepository repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<VisitorEntity> observablePerson;


    public VisitorViewModel(@NonNull Application application, final String visitorId, VisitorRepository visitorRepository) {
        super(application);

        this.repository = visitorRepository;
        this.application=application;

        observablePerson = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observablePerson.setValue(null);
        if(visitorId != null){
            LiveData<VisitorEntity> person = repository.getVisitor(visitorId);

            // observe the changes of the client entity from the database and forward them
            observablePerson.addSource(person, observablePerson::setValue);
        }
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String visitorId;

        private final VisitorRepository repository;

        public Factory(@NonNull Application application, String visitorId) {
            this.application = application;
            this.visitorId = visitorId;
            this.repository = ((BaseApp)application).getVisitorRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VisitorViewModel(application, visitorId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<VisitorEntity> getVisitor() {
        return observablePerson;
    }

    public void createVisitor(VisitorEntity visitor, OnAsyncEventListener callback) {
        repository.insert(visitor, callback);
    }

    public void updateVisitor(VisitorEntity visitor, OnAsyncEventListener callback) {
        repository.update(visitor, callback);
    }

    public void deleteVisitor(VisitorEntity visitor, OnAsyncEventListener callback) {
        repository.delete(visitor, callback);
    }

}
