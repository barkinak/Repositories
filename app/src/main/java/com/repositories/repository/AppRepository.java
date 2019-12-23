package com.repositories.repository;

import android.content.Context;

import com.repositories.repository.model.Repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class AppRepository {
    private static AppRepository instance;
    private AppDatabase mDb;

    /**
     * Singleton pattern
     */
    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
    }

    public static AppRepository getInstance(Context context){
        if(instance == null){
            instance = new AppRepository(context);
        }
        return instance;
    }

    //----------------------------------------------------------------------------------------------
    // DetailFragmentViewModel
    //----------------------------------------------------------------------------------------------
    /**
     * Update the is_favorite field of a repository in db
     * @param id id of repository
     * @param b boolean to indicate whether repository is favorite
     */
    public Completable updateIsFavoriteRepository(int id, Boolean b){
        return mDb.repositoryDao().updateIsFavorite(id, b);
    }

    /**
     * Get a single repository from db
     * @param id id of repository
     * @return returns the repository matching the id
     */
    public Single<Repository> getRepositoryById(int id){
        return mDb.repositoryDao().getRepositoryById(id);
    }

    //----------------------------------------------------------------------------------------------
    // ListFragmentViewModel
    //----------------------------------------------------------------------------------------------
    /**
     * Delete all repositories in db
     */
    public Completable deleteRepositories(){
        return mDb.repositoryDao().deleteAll();
    }

    /**
     * Get all repositories in db
     * @return list of repositories
     */
    public Flowable<List<Repository>> getRepositories(){
        return mDb.repositoryDao().getAll();
    }

    /**
     * Insert repositories to db
     * @param repositories list of repositories
     * @return Completable object
     */
    public Completable insertReposToDB(List<Repository> repositories){
        return mDb.repositoryDao().insertAll(repositories);
    }

}
