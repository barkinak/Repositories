package com.repositories.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;
import android.util.Log;

import com.repositories.model.Repository;
import com.repositories.repository.AppRepository;
import com.repositories.utilities.SampleData;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeActivityViewModel extends AndroidViewModel {
    private static final String TAG = "HomeActivityViewModel";

    private AppRepository mAppRepository;
    public List<Repository> mRepositories;

    private Executor executor = Executors.newSingleThreadExecutor();
    private Disposable mDisposable;

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = AppRepository.getInstance(getApplication());
        //mRepositories = mAppRepository.getRepositories();

        mDisposable = getRepositories("barkinak")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    // do something
                    Log.d(TAG, "HomeActivityViewModel: result " + result);
                });
    }

    public Observable<Repository> getRepositories(String query){
        mRepositories = SampleData.getRepositories();
        return Observable.fromIterable(mRepositories);
    }

    public void deleteAllRepositories(){
        mAppRepository.deleteRepositories();
    }

}
