package com.repolist.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.repolist.R;
import com.repolist.view.adapter.RepoListAdapter;
import com.repolist.model.Repo;
import com.repolist.viewmodel.HomeActivityViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private EditText mSearchField;
    private Button mSubmitButton;
    private ListView mRepoList;
    private RepoListAdapter mRepoListAdapter;
    private HomeActivityViewModel mHomeActivityViewModel;

    // lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        event_listeners();

        mHomeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        mHomeActivityViewModel.init();
        mHomeActivityViewModel.getRepos().observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> repos) {
                mRepoListAdapter.notifyDataSetChanged();
            }
        });

        initialize();
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

        mRepoListAdapter = new RepoListAdapter(getApplicationContext(), mHomeActivityViewModel.getRepos().getValue());
        mRepoList.setAdapter(mRepoListAdapter);
    }

    private void event_listeners(){
        // item click listener for repo list
        mRepoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
                Intent i = new Intent(HomeActivity.this, RepoDetailActivity.class);
                startActivity(i);
            }
        });
    }

}
