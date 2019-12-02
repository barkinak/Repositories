package com.repositories.repository;

import android.os.AsyncTask;

import com.repositories.model.Repository;
import com.repositories.db.RepositoryDao;

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
