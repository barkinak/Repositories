package com.repolist.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.repolist.R;
import com.repolist.repositories.DatabaseHelper;
import com.squareup.picasso.Picasso;

public class RepoDetailActivity extends AppCompatActivity {
    private static final String TAG = "RepoDetailActivity";

    DatabaseHelper mDbHelper;
    TextView mOwner, mOpenIssues, mStars;
    Button mFavoriteButton;
    ImageView mAvatar;
    int ownerID;
    int repoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        initialize();
        event_listeners();
    }
    @Override
    public void onResume() {
        super.onResume();
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
        mFavoriteButton = findViewById(R.id.favorite_button);

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
        ownerID = extras.getInt("owner_id");
        repoID = extras.getInt("repo_id");

        // setting variables
        mOwner.setText("Owner: " + owner_login);
        mOpenIssues.setText("Open Issues: " + Integer.toString(open_issues));
        mStars.setText("Stars: " + Integer.toString(stars));
        getSupportActionBar().setTitle(repo_name);
        Picasso.with(getApplicationContext()).load(avatar_url).into(mAvatar);

        mDbHelper = DatabaseHelper.getInstance(getApplicationContext());
    }

    private void event_listeners() {
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDbHelper.checkFavorite(Integer.toString(repoID))){
                    mDbHelper.deleteRepo(Integer.toString(repoID));
                    mFavoriteButton.setBackgroundResource(R.drawable.baseline_star_border_24);
                } else {
                    mDbHelper.addRepo(ownerID, repoID);
                    mFavoriteButton.setBackgroundResource(R.drawable.baseline_star_24);
                }
            }
        });
    }
}
