package com.example.visitapp.database.firebase;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.visitapp.database.entity.VisitEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class VisitLiveData extends LiveData<VisitEntity> {

    private static final String TAG = "VisitLiveData";

    private final DatabaseReference reference;
    private final String host;
    private final MyValueEventListener listener = new MyValueEventListener();

    public VisitLiveData(DatabaseReference ref) {
        reference = ref;
        host = ref.getParent().getParent().getKey();
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            VisitEntity entity = dataSnapshot.getValue(VisitEntity.class);
            entity.setVisitId(dataSnapshot.getKey());
            entity.setHostId(host);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
