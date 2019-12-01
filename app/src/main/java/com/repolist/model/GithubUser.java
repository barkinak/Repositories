package com.repolist.model;

/**
 * Represents the owner of a Repository returned from Github API.
 */

public class GithubUser {
    public String avatar_url;
    public String login;

    public GithubUser(String avatar_url, String login){
        this.avatar_url = avatar_url;
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public String getLogin() { return login; }
}
