package com.example.myfirstapp.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.database.entity.VisitEntity;

import java.util.List;

@Dao
public interface VisitDao {
    @Query("SELECT * FROM visits WHERE idVisit = :id")
    LiveData<VisitEntity> getById(Long id);

    @Query("SELECT * FROM visits")
    LiveData<List<VisitEntity>> getAll();

    /**
     * This method is used to populate the transaction activity.
     * It returns all OTHER users and their accounts.
     * @param id Id of the client who should be excluded from the list.
     * @return A live data object containing a list of ClientAccounts with
     * containing all clients but the @id.
     */


    @Insert
    long insert(VisitEntity visit) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<VisitEntity> visits);

    @Update
    void update(VisitEntity visit);

    @Delete
    void delete(VisitEntity visit);

    @Query("DELETE FROM visits")
    void deleteAll();
}
