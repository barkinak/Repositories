package com.repolist.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

    ArrayList<Repo> repoList;

    // lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
        event_listener();
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
        /*
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
        }*/
    }

    private void event_listener(){
        // item click listener for repo list
        mRepoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
                Intent i = new Intent(HomeActivity.this, RepoDetailActivity.class);
                i.putExtra("repo_name", repoList.get(position).getRepoName());
                i.putExtra("owner_login", repoList.get(position).getOwner_login());
                i.putExtra("avatar_url", repoList.get(position).getAvatar_url());
                i.putExtra("stars", repoList.get(position).getStargazers_count());
                i.putExtra("open_issues", repoList.get(position).getOpen_issues_count());
                startActivity(i);
            }
        });

        // gets repos from Github when clicked
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String githubUserName = mSearchField.getText().toString();
                Toast.makeText(HomeActivity.this, "Getting repos of  " + githubUserName, Toast.LENGTH_LONG).show();

                GetReposTask task = new GetReposTask();
                task.setGithubUserID(githubUserName);
                task.execute();

                try {
                    repoList = parseJSON(task.get());
                    repoListAdapter = new RepoListAdapter(getApplicationContext(), repoList);
                    mRepoList.setAdapter(repoListAdapter);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // parsing JSON Object returned from server:
    public ArrayList<Repo> parseJSON(String output){
        ArrayList<Repo> testRepos = new ArrayList<>();
        try {
            JSONObject jObject;
            JSONArray jsonArray = new JSONArray(output);
            for(int i=0; i<jsonArray.length(); i++){
                jObject = jsonArray.getJSONObject(i);
                String id = jObject.getString("id");
                String name = jObject.getString("name");
                String avatar_url = jObject.getJSONObject("owner").getString("avatar_url");
                String owner_login = jObject.getJSONObject("owner").getString("login");
                int open_issues_count = jObject.getInt("open_issues_count");
                int stargazers_count = jObject.getInt("stargazers_count");
                testRepos.add(new Repo(name, id, avatar_url, owner_login, open_issues_count, stargazers_count));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testRepos;
    }

    // determines whether input string is JSON:
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
}
