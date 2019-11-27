package com.repolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;
import android.util.Log;

import com.repolist.model.Repository;
import com.repolist.repositories.AppRepository;

import java.util.List;

public class HomeActivityViewModel extends AndroidViewModel {
    private static final String TAG = "HomeActivityViewModel";

    private AppRepository mAppRepository;
    public LiveData<List<Repository>> mRepositories;

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());
        mRepositories = mAppRepository.getRepositories("barkinak");
    }

    public void query(String query){
        mRepositories = mAppRepository.getRepositories(query);
    }

}
