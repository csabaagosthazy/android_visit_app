package com.example.myfirstapp.database;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;

import com.example.myfirstapp.database.entity.PersonEntity;

import com.example.myfirstapp.database.entity.VisitEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final com.example.myfirstapp.database.AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addPerson(final com.example.myfirstapp.database.AppDatabase db, final String email, final String firstName,
                                  final String lastName, final boolean isEmployee) {
        PersonEntity client = new PersonEntity(firstName, lastName,email,isEmployee,null);
        db.personDao().insert(client);
    }

    private static void addVisit(final com.example.myfirstapp.database.AppDatabase db, final String description, final Date visitDate, final Long visitor, final Long employee){
        VisitEntity visit = new VisitEntity(description, visitDate, visitor, employee);
        db.visitDao().insert(visit);
    }


    private static void populateWithTestData(com.example.myfirstapp.database.AppDatabase db) throws ParseException {
        db.personDao().deleteAll();
        db.visitDao().deleteAll();

        addPerson(db, "michel.platini@fifa.com", "Michel", "Platini",false);
        addPerson(db, "sepp.blatter@fifa.com", "Sepp", "Blatter",false);
        addPerson(db, "ebbe.schwartz@fifa.com", "Ebbe", "Schwartz", false);
        addPerson(db, "aleksander.ceferin@fifa.com", "Aleksander", "Ceferin",true);

        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addVisit(db, "Visite of the HR", new Date(),(long)1,(long)4);
        addVisit(db, "CEO visit", new Date(),(long)2,(long)4);
        addVisit(db, "College visit", new Date(),(long)3,(long)4);

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final com.example.myfirstapp.database.AppDatabase database;

        PopulateDbAsync(com.example.myfirstapp.database.AppDatabase db) {
            database = db;
        }


        @Override
        protected Void doInBackground(final Void... params) {
            try {
                populateWithTestData(database);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
