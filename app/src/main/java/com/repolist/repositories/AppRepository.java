package com.repolist.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.util.Log;

import com.repolist.model.AppDatabase;
import com.repolist.model.Repository;
import com.repolist.utilities.SampleData;

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
    private LiveData<Repository> mRepositories;
    private Executor executor = Executors.newSingleThreadExecutor();

    private AppRepository(Context context) {
        Log.d(TAG, "AppRepository: ");
        mDb = AppDatabase.getInstance(context);
        setRepositories(SampleData.getRepositories());
    }

    public static AppRepository getInstance(Context context){
        Log.d(TAG, "getInstance: ");
        if(instance == null){
            instance = new AppRepository(context);
        }
        return instance;
    }

    public void setRepositories(List<Repository> repos){
        Log.d(TAG, "setRepositories: ");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.repositoryDao().insertAll(repos);
            }
        });
    }

    public LiveData<List<Repository>> getRepositories(){
        Log.d(TAG, "getRepositories: ");
        return mDb.repositoryDao().getAll();
    }

    // Get data from a webservice or online source
    public LiveData<List<Repository>> getRepos(String username){
        Log.d(TAG, "**** 1");
        GetReposTask task = new GetReposTask();
        task.setGithubUserID(username);
        task.execute();

        try {
            String result = task.get();
            Log.d(TAG, "****  RESULT " + result);
            //dataSet = parseJSON(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MutableLiveData<List<Repository>> data = new MutableLiveData<>();
        data.setValue(SampleData.getRepositories());
        return data;
    }

    // parsing JSON Object returned from server:
    public ArrayList<Repository> parseJSON(String output){
        ArrayList<Repository> testRepos = new ArrayList<>();
        try {
            JSONObject jObject;
            JSONArray jsonArray = new JSONArray(output);
            for(int i=0; i<jsonArray.length(); i++){
                jObject = jsonArray.getJSONObject(i);

                int id               = jObject.getInt("id");
                String name          = jObject.getString("name");
                String description   = jObject.getString("description");
                int stargazers_count = jObject.getInt("stargazers_count");
                int watchers_count   = jObject.getInt("watchers_count");
                String language      = jObject.getString("language");

                testRepos.add(new Repository(id, name, description, stargazers_count, watchers_count, language));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testRepos;
    }

    private void setRepos(){

    }
}
