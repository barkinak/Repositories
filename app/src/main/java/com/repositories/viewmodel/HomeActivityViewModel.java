package com.repositories.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;

import com.repositories.model.Repository;
import com.repositories.repository.AppRepository;

import java.util.List;

public class HomeActivityViewModel extends AndroidViewModel {
    private static final String TAG = "HomeActivityViewModel";

    private AppRepository mAppRepository;
    public LiveData<List<Repository>> mRepositories;

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());
        mRepositories = mAppRepository.getRepositories();
    }

    public void getRepositories(String query){
        mRepositories = mAppRepository.getRepositories(query);
    }

    public void deleteAllRepositories(){
        mAppRepository.deleteRepositories();
    }

}
