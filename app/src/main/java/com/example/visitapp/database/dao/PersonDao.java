package com.example.visitapp.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.visitapp.database.entity.VisitorEntity;

import java.util.List;
//import com.example.myfirstapp.database.pojo.ClientWithAccounts;

/**
 * https://developer.android.com/topic/libraries/architecture/room.html#no-object-references
 */
@Dao
public interface PersonDao {

    @Query("SELECT * FROM persons WHERE idPerson = :idPerson")
    LiveData<VisitorEntity> getById(Long idPerson);

    @Query("SELECT * FROM persons")
    LiveData<List<VisitorEntity>> getAll();

    @Query("SELECT * FROM persons where is_employee = 0")
    LiveData<List<VisitorEntity>> getAllVisitors();

    @Query("SELECT * FROM persons where is_employee = 1")
    LiveData<List<VisitorEntity>> getAllEmployees();

    @Insert
    long insert(VisitorEntity person) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<VisitorEntity> persons);

    @Update
    void update(VisitorEntity person);

    @Delete
    void delete(VisitorEntity person);

    @Query("DELETE FROM persons")
    void deleteAll();
}
