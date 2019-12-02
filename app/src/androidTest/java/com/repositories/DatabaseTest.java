package com.repositories;

import android.content.Context;
import android.util.Log;

import com.repositories.db.AppDatabase;
import com.repositories.model.Repository;
import com.repositories.db.RepositoryDao;
import com.repositories.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private RepositoryDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.repositoryDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveRepositories() {
        mDao.insertAll(SampleData.getRepositories());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveRepositories: count: " + count);
        assertEquals(SampleData.getRepositories().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAll(SampleData.getRepositories());
        Repository original = SampleData.getRepositories().get(0);
        Repository fromDb = mDao.getRepositoryById(1);
        assertEquals(original.getDescription(), fromDb.getDescription());
        assertEquals(1, fromDb.getId());
    }

}