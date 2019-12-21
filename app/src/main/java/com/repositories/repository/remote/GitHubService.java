package com.repositories.repository.remote;

import com.repositories.repository.model.Repository;

import java.util.List;

import io.reactivex.Flowable;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    @GET("users/{user}/repos")
    Flowable<List<Repository>> listRepos(@Path("user") String user);
}
