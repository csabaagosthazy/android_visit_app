package com.example.visitapp.database.repository;

import androidx.lifecycle.LiveData;

import com.example.visitapp.database.entity.VisitorEntity;
import com.example.visitapp.database.firebase.VisitorListLiveData;
import com.example.visitapp.database.firebase.VisitorLiveData;
import com.example.visitapp.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class VisitorRepository {

    private static final String TAG = "VisitorRepository";
    private static VisitorRepository instance;

    private VisitorRepository(){

    }
    //singleton who return only 1 instance of VisitorRepository
    public static VisitorRepository getInstance(){
        if (instance == null){

            synchronized (VisitRepository.class){
                if (instance == null){
                    instance = new VisitorRepository();
                }
            }
        }
        return instance;
    }

    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public LiveData<VisitorEntity> getVisitor(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("visitors")
                .child(id);
        return new VisitorLiveData(reference);
    }
    public LiveData<List<VisitorEntity>> getAllVisitors(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("visitors");
        return new VisitorListLiveData(reference);
    }

    public void insert(final VisitorEntity visitor, OnAsyncEventListener callback){
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("visitors");
                String key = ref.push().getKey();
                FirebaseDatabase.getInstance()
                .getReference("visitors").child(key)
                .setValue(visitor, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final VisitorEntity visitor, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("visitors")
                .child(visitor.getVisitorId())
                .updateChildren(visitor.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final VisitorEntity visitor, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("visitors")
                .child(visitor.getVisitorId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
