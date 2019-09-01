package com.repolist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.repolist.R;
import com.squareup.picasso.Picasso;

public class RepoDetailActivity extends AppCompatActivity {
    TextView mOwner, mOpenIssues, mStars;
    ImageView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        initialize();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize(){
        mOwner = findViewById(R.id.owner);
        mOpenIssues= findViewById(R.id.open_issues);
        mStars = findViewById(R.id.stars);
        mAvatar = findViewById(R.id.avatar);

        // toolbar
        Toolbar myToolbar = findViewById(R.id.repo_detail_toolbar);
        setSupportActionBar(myToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // get data from previous activity via intent
        Bundle extras = getIntent().getExtras();
        if (extras == null) { return; }
        // get data from HomeActivity via key
        String repo_name = extras.getString("repo_name");
        String owner_login = extras.getString("owner_login");
        String avatar_url = extras.getString("avatar_url");
        int stars = extras.getInt("stars");
        int open_issues = extras.getInt("open_issues");

        // setting variables
        mOwner.setText("Owner: " + owner_login);
        mOpenIssues.setText("Open Issues: " + Integer.toString(open_issues));
        mStars.setText("Stars: " + Integer.toString(open_issues));
        getSupportActionBar().setTitle(repo_name);
        Picasso.with(getApplicationContext()).load(avatar_url).into(mAvatar);
    }
}
