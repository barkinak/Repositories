package com.repolist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.util.Log;

import com.repolist.model.Repository;
import com.repolist.repositories.AppRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailFragmentViewModel extends AndroidViewModel {
    private static final String TAG = "HomeActivityViewModel";

    private AppRepository mAppRepository;
    public MutableLiveData<Repository> mRepository = new MutableLiveData<>();
    private Executor executor = Executors.newSingleThreadExecutor();

    public DetailFragmentViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());
    }

    public void getRepositoryById(int id){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Repository repository = mAppRepository.getRepositoryById(id);
                mRepository.postValue(repository);
            }
        });
    }

}