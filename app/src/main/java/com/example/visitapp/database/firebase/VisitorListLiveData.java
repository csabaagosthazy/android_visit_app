package com.example.visitapp.database.firebase;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.visitapp.database.entity.VisitorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VisitorListLiveData extends LiveData<List<VisitorEntity>> {

    private static final String TAG = "VisitorListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener =
            new MyValueEventListener();

    public VisitorListLiveData(DatabaseReference ref) {
        reference = ref;
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
            setValue(toVisitorList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<VisitorEntity> toVisitorList(DataSnapshot snapshot) {
        List<VisitorEntity> visitors = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            VisitorEntity entity = childSnapshot.getValue(VisitorEntity.class);
            entity.setVisitorId(childSnapshot.getKey());
            visitors.add(entity);
        }
        return visitors;
    }
}
