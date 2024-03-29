package com.repositories.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.util.Log;

import com.repositories.repository.model.Repository;
import com.repositories.repository.AppRepository;
import com.repositories.repository.remote.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class ListFragmentViewModel extends AndroidViewModel {
    private static final String TAG = "ListFragmentViewModel";

    private AppRepository mAppRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    public MutableLiveData<List<Repository>> mRepositories = new MutableLiveData<>();

    public ListFragmentViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());

        mDisposable.add(mAppRepository.getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    mRepositories.postValue(result);
                }));
    }

    /**
     * Get GitHub repositories of queried user
     * @param query ID of GitHub user
     */
    public void getRepositoriesFromWebService(String query){
        mDisposable.add(RetrofitInstance.getService().listRepos(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories -> insertReposToDB(repositories)));
    }

    /**
     * Insert repositories to db
     * @param repositories list of repositories
     */
    public void insertReposToDB(List<Repository> repositories){
        mDisposable.add(mAppRepository.insertReposToDB(repositories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "Successfully acquired repositories"),
                        throwable -> Log.e(TAG, "Unable to get repositories", throwable)));
    }

    /**
     * Deletes all repos in db when user enters a new query via SearchView
     */
    public void deleteAllRepositories(){
        mDisposable.add(mAppRepository.deleteRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "Successfully deleted repositories"),
                        throwable -> Log.e(TAG, "Unable to delete repositories", throwable)));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }

}
