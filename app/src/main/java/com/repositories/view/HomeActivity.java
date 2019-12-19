package com.repositories.view;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.repositories.R;

public class HomeActivity extends AppCompatActivity implements RepoDetailFragment.OnFragmentInteractionListener, TestInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        TestInterface t = new TestInterface() {
            @Override
            public void greet(String name) {

            }
        };
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    @Override
    public void greet(String name) {

    }
}
