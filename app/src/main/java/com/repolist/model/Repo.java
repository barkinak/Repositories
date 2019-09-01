package com.repolist.model;

public class Repo {
    String repoName;
    String repoID;
    String avatar_url;
    String owner_login;
    int open_issues_count;
    int stargazers_count;

    public Repo(){ }

    public Repo(String repoName, String repoID, String avatar_url, String owner_login, int open_issues_count, int stargazers_count) {
        this.repoName = repoName;
        this.repoID = repoID;
        this.avatar_url = avatar_url;
        this.owner_login = owner_login;
        this.open_issues_count = open_issues_count;
        this.stargazers_count = stargazers_count;
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

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getOwner_login() {
        return owner_login;
    }

    public void setOwner_login(String owner_login) {
        this.owner_login = owner_login;
    }

    public int getOpen_issues_count() {
        return open_issues_count;
    }

    public void setOpen_issues_count(int open_issues_count) {
        this.open_issues_count = open_issues_count;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }
}
