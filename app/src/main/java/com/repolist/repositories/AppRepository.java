package com.repolist.repositories;

import androidx.lifecycle.LiveData;

import android.content.Context;

import com.repolist.model.AppDatabase;
import com.repolist.model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Singleton pattern
 */

public class AppRepository {
    private static final String TAG = "AppRepository";

    private static AppRepository instance;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
    }

    public static AppRepository getInstance(Context context){
        if(instance == null){
            instance = new AppRepository(context);
        }
        return instance;
    }

    public void updateIsRepository(int id, Boolean b){
        executor.execute(() -> mDb.repositoryDao().updateIsFavorite(id, b));
    }

    public Repository getRepositoryById(int id){
        return mDb.repositoryDao().getRepositoryById(id);
    }

    public void setRepositories(String query){
        List<Repository> repositories = getReposFromGithub(query);
        executor.execute(() -> mDb.repositoryDao().insertAll(repositories));
    }

    public void deleteRepositories(){
        executor.execute(() -> mDb.repositoryDao().deleteAll());
    }

    /**
     * Gets repositories of the last queried user
     * @return
     */
    public LiveData<List<Repository>> getRepositories(){
        return mDb.repositoryDao().getAll();
    }

    /**
     * Gets repositories of queried user
     * @param query
     * @return
     */
    public LiveData<List<Repository>> getRepositories(String query){
        setRepositories(query);
        return mDb.repositoryDao().getAll();
    }

    /**
     * Method that starts an AsyncTask to get repositories of queried user from GitHub
     * @param username ID of GitHub user
     * @return List of repositories of a GitHub user
     */
    public List<Repository> getReposFromGithub(String username){
        List<Repository> repositories = new ArrayList<>();
        GetReposTask task = new GetReposTask();
        task.setGithubUserID(username);
        task.execute();
        try {
            String result = task.get();
            repositories = parseJSON(result);
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
    public ArrayList<Repository> parseJSON(String output){
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
