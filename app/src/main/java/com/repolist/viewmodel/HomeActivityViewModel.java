package com.repolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        mRepositories = mAppRepository.getRepositories();
    }

    public void query(String query){
        Log.d(TAG, "query: ");
        //mRepositories.postValue(mAppRepository.getRepos("aea7").getValue());
    }

    public LiveData<List<Repository>> getRepos(){
        mAppRepository.getRepositories();
        Log.d(TAG, "getRepos: ");
        return mRepositories;
    }

}
