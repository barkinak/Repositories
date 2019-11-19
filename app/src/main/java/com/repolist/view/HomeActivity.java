package com.repolist.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import com.repolist.R;
import com.repolist.viewmodel.HomeActivityViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private EditText mSearchField;
    private Button mSubmitButton;

    private HomeActivityViewModel mHomeActivityViewModel;

    // lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() { super.onPause(); }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
