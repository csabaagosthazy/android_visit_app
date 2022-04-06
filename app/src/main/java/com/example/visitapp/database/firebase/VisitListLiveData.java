package com.example.visitapp.database.firebase;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.visitapp.database.entity.VisitEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VisitListLiveData extends LiveData<List<VisitEntity>> {

    private static final String TAG = "VisitListLiveData";

    private final DatabaseReference reference;
    private final String hostId;
    private Long from = null;
    private Long to = null;
    private final MyValueEventListener listener = new MyValueEventListener();

    public VisitListLiveData(DatabaseReference ref, String hostId) {
        reference = ref;
        this.hostId = hostId;
    }

    public VisitListLiveData(DatabaseReference ref, String hostId, Long from, Long to) {
        reference = ref;
        this.hostId = hostId;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        if(from == null || to == null ){

            reference.addValueEventListener(listener);
        } else {
            reference.orderByChild("visitDate").startAt(from).endAt(to).addValueEventListener(listener);
        }
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toVisitList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<VisitEntity> toVisitList(DataSnapshot snapshot) {
        List<VisitEntity> visits = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            VisitEntity entity = childSnapshot.getValue(VisitEntity.class);
            entity.setVisitId(childSnapshot.getKey());
            entity.setHostId(hostId);
            visits.add(entity);
        }
        return visits;
    }
}