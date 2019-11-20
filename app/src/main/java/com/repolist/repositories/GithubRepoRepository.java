package com.repolist.repositories;

import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.repolist.model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Singleton pattern
 */

public class GithubRepoRepository {
    private static final String TAG = "GithubRepoRepository";

    private static GithubRepoRepository instance;
    private ArrayList<Repository> dataSet = new ArrayList<>();

    public static GithubRepoRepository getInstance(){
        if(instance == null){
            instance = new GithubRepoRepository();
        }
        return instance;
    }

    // Get data from a webservice or online source
    public MutableLiveData<List<Repository>> getRepos(String username){
        Log.d(TAG, "**** 1");
        GetReposTask task = new GetReposTask();
        task.setGithubUserID(username);
        task.execute();

        try {
            String result = task.get();
            Log.d(TAG, "****  RESULT " + result);
            dataSet = parseJSON(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MutableLiveData<List<Repository>> data = new MutableLiveData<>();
        data.setValue(dataSet);
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
                String name           = jObject.getString("name");
                String description    = jObject.getString("description");
                int stargazers_count  = jObject.getInt("stargazers_count");
                int watchers_count  = jObject.getInt("watchers_count");
                testRepos.add(new Repository(name, description, stargazers_count, watchers_count));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testRepos;
    }

    private void setRepos(){

    }
}
