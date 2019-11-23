package com.repolist.model;

/**
 * Represents the owner of a Repository returned from Github API.
 */

public class GithubUser {
    public String avatar_url;

    /*
    public String login;
    public long id;
    public String node_id;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public Boolean site_admin;
    */

    public GithubUser(String avatar_url){
        this.avatar_url = avatar_url;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }
}