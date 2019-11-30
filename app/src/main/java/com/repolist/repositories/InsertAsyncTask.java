package com.repolist.repositories;

import android.os.AsyncTask;

import com.repolist.model.Repository;
import com.repolist.model.RepositoryDao;

public class InsertAsyncTask extends AsyncTask<Void, Void, Boolean> {

    RepositoryDao mRepositoryDao;
    Repository mRepository;

    public InsertAsyncTask(RepositoryDao repositoryDao){
        mRepositoryDao = repositoryDao;
    }

    @Override
    protected Boolean doInBackground(Void... args) {
        mRepositoryDao.insertRepository(mRepository);
        return true;
    }
}
