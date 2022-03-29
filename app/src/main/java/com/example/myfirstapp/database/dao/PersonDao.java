package com.example.myfirstapp.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myfirstapp.database.entity.PersonEntity;

import java.util.List;

import com.example.myfirstapp.database.entity.PersonEntity;
//import com.example.myfirstapp.database.pojo.ClientWithAccounts;

/**
 * https://developer.android.com/topic/libraries/architecture/room.html#no-object-references
 */
@Dao
public interface PersonDao {

    @Query("SELECT * FROM persons WHERE idPerson = :idPerson")
    LiveData<PersonEntity> getById(Long idPerson);

    @Query("SELECT * FROM persons")
    LiveData<List<PersonEntity>> getAll();

    @Query("SELECT * FROM persons where is_employee != 1")
    LiveData<List<PersonEntity>> getAllVisitors();

    @Query("SELECT * FROM persons where is_employee = 1")
    LiveData<List<PersonEntity>> getAllEmployees();


    @Insert
    long insert(PersonEntity person) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PersonEntity> persons);

    @Update
    void update(PersonEntity person);

    @Delete
    void delete(PersonEntity person);

    @Query("DELETE FROM persons")
    void deleteAll();
}
