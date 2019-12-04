package com.repositories.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.util.Log;

import com.repositories.model.Repository;
import com.repositories.repository.AppRepository;

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

    /**
     * Gets repository from db with given id and sets it to MutableLiveData object
     * @param id id of repository
     */
    public void getRepositoryById(int id){
        executor.execute(() -> {
            mRepository.postValue(mAppRepository.getRepositoryById(id));
        });
    }

    /**
     * Updates the is_favorite field of repository with the given id, after user clicks favorite
     * button. The is_favorite field will be used inside the adapter when setting the star icons
     * of repositories.
     * @param id id of repository
     * @param b is favorite boolean
     */
    public void updateIsFavorite(int id, Boolean b){
        mAppRepository.updateIsRepository(id, b);
        mRepository.getValue().setIsFavorite(b);
    }

}