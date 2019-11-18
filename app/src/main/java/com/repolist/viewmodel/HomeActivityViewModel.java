package com.repolist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.repolist.model.Repo;
import com.repolist.repositories.GithubRepoRepository;

import java.util.List;

public class HomeActivityViewModel extends ViewModel {
    private static final String TAG = "HomeActivityViewModel";

    private GithubRepoRepository mGithubRepoRepository;
    private MutableLiveData<List<Repo>> mRepos;

    public void init(){
        Log.d(TAG, "**** 2");
        if(mRepos != null){
            Log.d(TAG, "**** 3");
            return;
        }
        Log.d(TAG, "**** 4");
        mGithubRepoRepository = GithubRepoRepository.getInstance();
        mRepos = mGithubRepoRepository.getRepos();
    }

    public LiveData<List<Repo>> getRepos(){
        Log.d(TAG, "**** 5");
        return mRepos;
    }

}
