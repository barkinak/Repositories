package com.repolist.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.repolist.R;
import com.repolist.model.Repo;
import com.repolist.network.GetReposTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    ListView mRepoList;
    EditText mSearchField;
    Button mSubmitButton;
    RepoListAdapter repoListAdapter;

    // lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
        event_listener();
        //test();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initialize(){
        mRepoList = findViewById(R.id.repo_list);
        mSearchField = findViewById(R.id.repo_text_field);
        mSubmitButton = findViewById(R.id.submit_button);

        // toolbar
        Toolbar myToolbar = findViewById(R.id.repo_list_toolbar);
        setSupportActionBar(myToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private void event_listener(){
        // item click listener for repo list
        mRepoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
                Toast.makeText(HomeActivity.this, "Clicked on position " + position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HomeActivity.this, RepoDetailActivity.class);
                startActivity(intent);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String githubUserName = mSearchField.getText().toString();
                Toast.makeText(HomeActivity.this, "User wants the repos of  " + githubUserName, Toast.LENGTH_LONG).show();

                GetReposTask task = new GetReposTask();
                task.setGithubUserID(githubUserName);
                task.execute();

                try {
                    Log.d(TAG, "RESPONSE: " + task.get());
                    repoListAdapter = new RepoListAdapter(getApplicationContext(), parseJSON(task.get()));
                    mRepoList.setAdapter(repoListAdapter);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Parsing JSON Object returned from server:
    public ArrayList<Repo> parseJSON(String output){
        ArrayList<Repo> testRepos = new ArrayList<>();
        try {
            JSONObject jObject;
            JSONArray jsonArray = new JSONArray(output);
            String data, time, date;
            for(int i=0; i<jsonArray.length(); i++){
                jObject = jsonArray.getJSONObject(i);
                String id = jObject.getString("id");
                String name = jObject.getString("name");
                testRepos.add(new Repo(name, id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testRepos;
    }

    // Determines whether input string is JSON:
    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException e1) {
            try {
                new JSONArray(test);
            } catch (JSONException e2) {
                return false;
            }
        }
        return true;
    }

    private void test(){
        ArrayList<Repo> testRepos = new ArrayList<>();
        testRepos.add(new Repo("Repo 1" , "1000"));
        testRepos.add(new Repo("Repo 2" , "1001"));
        testRepos.add(new Repo("Repo 3" , "1002"));
        repoListAdapter = new RepoListAdapter(getApplicationContext(), testRepos);
        mRepoList.setAdapter(repoListAdapter);
    }
}
