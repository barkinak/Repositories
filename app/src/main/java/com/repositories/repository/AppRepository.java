package com.repositories.repository;

import android.content.Context;

import com.repositories.model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    /**
     * Updates the is_favorite field of a repository in db
     * @param id id of repository
     * @param b boolean to indicate whether repository is favorite
     */
    public Completable updateIsFavoriteRepository(int id, Boolean b){
        return mDb.repositoryDao().updateIsFavorite(id, b);
    }

    /**
     * Method to get a single repository from db
     * @param id id of repository
     * @return returns the repository matching the id
     */
    public Single<Repository> getRepositoryById(int id){
        return mDb.repositoryDao().getRepositoryById(id);
    }

    //----------------------------------------------------------------------------------------------
    /**
     * Method to delete all repositories in db
     */
    public Completable deleteRepositories(){
        return mDb.repositoryDao().deleteAll();
    }

    /**
     * Get all repositories in db
     * @return list of repositories in db
     */
    public Flowable<List<Repository>> getRepositories(){
        return mDb.repositoryDao().getAll();
    }

    /**
     * Starts an AsynchTask to query GitHub and inserts results to db
     * @param query ID of GitHub user
     */
    public Completable startRepositoryRequest(String query){
        return mDb.repositoryDao().insertAll(getReposFromGithub(query));
    }

    /**
     * AsyncTask method to get repositories of queried user from GitHub
     * @param username ID of GitHub user
     * @return List of repositories of a GitHub user
     */
    private List<Repository> getReposFromGithub(String username){
        List<Repository> repositories = new ArrayList<>();
        GetReposAsyncTask task = new GetReposAsyncTask();
        task.setGithubUserID(username);
        task.execute();
        try {
            repositories = parseJSON(task.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return repositories;
    }

    /**
     * Parsing JSON Object returned from server
     * @param output JSON String returned from GitHub
     * @return array of parsed repositories
     */
    private ArrayList<Repository> parseJSON(String output){
        ArrayList<Repository> repositories = new ArrayList<>();
        try {
            JSONObject repository, owner;
            JSONArray jsonArray = new JSONArray(output);
            for(int i=0; i<jsonArray.length(); i++){
                repository = jsonArray.getJSONObject(i);
                int id               = repository.getInt("id");
                String name          = repository.getString("name");
                String description   = repository.getString("description");
                int stargazers_count = repository.getInt("stargazers_count");
                int watchers_count   = repository.getInt("watchers_count");
                String language      = repository.getString("language");

                owner = repository.getJSONObject("owner");
                String avatar_url    = owner.getString("avatar_url");
                String user_id       = owner.getString("login");

                Repository r = new Repository(id, name, description, stargazers_count, watchers_count, language);
                r.setAvatarUrl(avatar_url);
                r.setUserId(user_id);
                repositories.add(r);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repositories;
    }

}
