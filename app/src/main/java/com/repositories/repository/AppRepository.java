package com.repositories.repository;

import android.content.Context;

import com.repositories.repository.model.Repository;
import com.repositories.repository.remote.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
