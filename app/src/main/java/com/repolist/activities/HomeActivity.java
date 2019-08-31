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

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
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
        test();
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
            }
        });
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
