package com.example.visitapp.database.repository;


import androidx.lifecycle.LiveData;

import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.database.firebase.VisitListLiveData;
import com.example.visitapp.database.firebase.VisitLiveData;
import com.example.visitapp.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class VisitRepository {

    private static final String TAG = "VisitRepository";
    private static VisitRepository instance;

    private VisitRepository(){

    }
    //singleton who return only 1 instance of VisitRepository
    public static VisitRepository getInstance() {
        if (instance == null) {

            synchronized (VisitRepository.class) {
                if (instance == null) {
                    instance = new VisitRepository();
                }
            }
        }
        return instance;
    }
    public LiveData<VisitEntity> getVisit(final String visitId){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("visits")
                .child(userId)
                .child(visitId);
        return new VisitLiveData(reference);
    }
    public LiveData<List<VisitEntity>> getVisitsByHost(final String hostId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("visits")
                .child(hostId);
        return new VisitListLiveData(reference, hostId);
    }

    public LiveData<List<VisitEntity>> getVisitsByHostAndDate(final String hostId, final Long from, final Long to) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("visits")
                .child(hostId);
        return new VisitListLiveData(reference, hostId, from, to);
    }

    public void insert(final VisitEntity visit, OnAsyncEventListener callback) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("visits")
                .child(userId);
        String key = ref.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("visits").child(userId).child(key)
                .setValue(visit, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final VisitEntity visit, OnAsyncEventListener callback) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("visits")
                .child(userId)
                .child(visit.getVisitId())
                .updateChildren(visit.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final VisitEntity visit, OnAsyncEventListener callback) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("visits")
                .child(userId)
                .child(visit.getVisitId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
