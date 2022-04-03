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

import java.util.Date;
import java.util.List;

@Dao
public interface VisitDao {
    @Query("SELECT * FROM visits WHERE idVisit = :id")
    LiveData<VisitEntity> getById(Long id);

    @Query("SELECT * FROM visits")
    LiveData<List<VisitEntity>> getAll();
    //get date between 2 dates 00 and 23:59
    //Room using long numbers to store dates (unix)
    @Query("SELECT * FROM visits WHERE visit_date BETWEEN :from AND :to")
    LiveData<List<VisitEntity>> getByDate(Long from, Long to);



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
