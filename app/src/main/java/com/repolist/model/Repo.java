package com.repolist.model;

public class Repo {
    String repoName;
    String repoID;

    public Repo(){ }

    public Repo(String repoName, String repoID) {
        this.repoName = repoName;
        this.repoID = repoID;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoID() {
        return repoID;
    }

    public void setRepoID(String repoID) {
        this.repoID = repoID;
    }
}
