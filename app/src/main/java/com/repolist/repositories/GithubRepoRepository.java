package com.repolist.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.repolist.model.Repo;

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
    private ArrayList<Repo> dataSet = new ArrayList<>();

    public static GithubRepoRepository getInstance(){
        if(instance == null){
            instance = new GithubRepoRepository();
        }
        return instance;
    }

    // Get data from a webservice or online source
    public MutableLiveData<List<Repo>> getRepos(){
        Log.d(TAG, "**** 1");
        GetReposTask task = new GetReposTask();
        task.setGithubUserID("aea7");
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

        MutableLiveData<List<Repo>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    // parsing JSON Object returned from server:
    public ArrayList<Repo> parseJSON(String output){
        ArrayList<Repo> testRepos = new ArrayList<>();
        try {
            JSONObject jObject;
            JSONArray jsonArray = new JSONArray(output);
            for(int i=0; i<jsonArray.length(); i++){
                jObject = jsonArray.getJSONObject(i);
                int id                = jObject.getInt("id");
                String name           = jObject.getString("name");
                String avatar_url     = jObject.getJSONObject("owner").getString("avatar_url");
                String owner_login    = jObject.getJSONObject("owner").getString("login");
                int owner_id          = jObject.getJSONObject("owner").getInt("id");
                int open_issues_count = jObject.getInt("open_issues_count");
                int stargazers_count  = jObject.getInt("stargazers_count");
                testRepos.add(new Repo(name, id, avatar_url, owner_login, open_issues_count, stargazers_count, owner_id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testRepos;
    }

    private void setRepos(){

    }
}
