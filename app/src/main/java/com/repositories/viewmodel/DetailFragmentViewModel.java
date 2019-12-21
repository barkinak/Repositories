package com.repositories.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import android.app.Application;
import android.util.Log;

import com.repositories.repository.model.Repository;
import com.repositories.repository.AppRepository;

public class DetailFragmentViewModel extends AndroidViewModel {
    private static final String TAG = "DetailFragmentViewModel";

    private AppRepository mAppRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    public MutableLiveData<Repository> mRepository = new MutableLiveData<>();

    public DetailFragmentViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());
    }

    /**
     * Gets repository from db with given id and sets it to MutableLiveData object
     * @param id id of repository
     */
    public void getRepositoryById(int id){
        mDisposable.add(mAppRepository.getRepositoryById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Repository>() {
                    @Override
                    public void onSuccess(Repository repository) {
                        Log.d(TAG, "onSuccess: repository ");
                        mRepository.postValue(repository);
                    }

                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }
                }));
    }

    /**
     * Updates the is_favorite field of repository with the given id, after user clicks favorite
     * button. The is_favorite field will be used inside the adapter when setting the star icons
     * of repositories.
     * @param id id of repository
     * @param b is favorite boolean
     */
    public void updateIsFavorite(int id, Boolean b){
        mDisposable.add(mAppRepository.updateIsFavoriteRepository(id, b)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "Successfully updated repository"),
                        throwable -> Log.e(TAG, "Unable to update repositories", throwable)));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}