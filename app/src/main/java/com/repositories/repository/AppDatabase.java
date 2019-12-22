package com.repositories.repository;

import android.content.Context;

import com.repositories.repository.model.Converters;
import com.repositories.repository.model.Repository;
import com.repositories.repository.model.RepositoryDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Repository.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract RepositoryDao repositoryDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}




