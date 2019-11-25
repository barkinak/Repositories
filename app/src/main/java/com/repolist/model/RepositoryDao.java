package com.repolist.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepository(Repository repository);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Repository> repositories);

    @Delete
    void deleteRepository(Repository repository);

    @Query("SELECT * FROM repositories WHERE id = :id")
    Repository getRepositoryById(int id);

    @Query("SELECT * FROM repositories ORDER BY id DESC")
    LiveData<List<Repository>> getAll();

    @Query("DELETE FROM repositories")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM repositories")
    int getCount();
}
