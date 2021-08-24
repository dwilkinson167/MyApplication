package com.example.myapplication;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseSingleton {

    private static com.example.myapplication.AppDatabase db;

    public static com.example.myapplication.AppDatabase getDatabase(Context context) {
        if(db == null) {
            db = Room.databaseBuilder(context,
                    com.example.myapplication.AppDatabase.class, context.getString(R.string.sample_database))
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}
