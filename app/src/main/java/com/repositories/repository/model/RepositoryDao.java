package com.repositories.repository.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Repository> repositories);

    @Query("SELECT * FROM repositories ORDER BY id DESC")
    Flowable<List<Repository>> getAll();

    @Query("DELETE FROM repositories")
    Completable deleteAll();

    //----------------------------------------------------------------------------------------------
    @Query("SELECT * FROM repositories WHERE id = :id")
    Single<Repository> getRepositoryById(int id);

    @Query("UPDATE repositories SET is_favorite =:b WHERE id = :id")
    Completable updateIsFavorite(int id, boolean b);

    @Query("SELECT COUNT(*) FROM repositories")
    int getCount();

    //----------------------------------------------------------------------------------------------
    @Query("SELECT * FROM repositories WHERE id = :id")
    Repository getRepositoryByIdTest(int id);
}
