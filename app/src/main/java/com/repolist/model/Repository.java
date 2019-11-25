package com.repolist.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Represents a Repository object returned from Github API.
 */

@Entity(tableName = "repositories")
public class Repository {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String description;
    public int stargazers_count;
    public int watchers_count;
    public String language;

    /* These fields can be added later as the app progresses
    public String node_id;
    public String full_name;
    public Boolean _private;
    public GithubUser owner;
    public String html_url;
    public Boolean fork;
    public String url;
    public String forks_url;
    public String keys_url;
    public String collaborators_url;
    public String teams_url;
    public String hooks_url;
    public String issue_events_url;
    public String events_url;
    public String assignees_url;
    public String branches_url;
    public String tags_url;
    public String blobs_url;
    public String git_tags_url;
    public String git_refs_url;
    public String trees_url;
    public String statuses_url;
    public String languages_url;
    public String stargazers_url;
    public String contributors_url;
    public String subscribers_url;
    public String subscription_url;
    public String commits_url;
    public String git_commits_url;
    public String comments_url;
    public String issue_comment_url;
    public String contents_url;
    public String compare_url;
    public String merges_url;
    public String archive_url;
    public String downloads_url;
    public String issues_url;
    public String pulls_url;
    public String milestones_url;
    public String notifications_url;
    public String labels_url;
    public String releases_url;
    public String deployments_url;
    public Date created_at;
    public Date updated_at;
    public Date pushed_at;
    public String git_url;
    public String ssh_url;
    public String clone_url;
    public String svn_url;
    public String homepage;
    public int size;
    public boolean has_issues;
    public boolean has_projects;
    public boolean has_downloads;
    public boolean has_wiki;
    public boolean has_pages;
    public int forks_count;
    public Boolean mirror_url;
    public int open_issues_count;
    public Boolean license;
    public int forks;
    public int open_issues;
    public int watchers;
    public String default_branch;
    */

    @Ignore
    public Repository() {}

    public Repository(int id, String name, String description, int stargazers_count, int watchers_count, String language) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.watchers_count = watchers_count;
        this.language = language;
    }

    @Ignore
    public Repository(String name, String description, int stargazers_count, int watchers_count, String language) {
        this.name = name;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.watchers_count = watchers_count;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStargazersCount() {
        return stargazers_count;
    }

    public int getWatchersCount() {
        return watchers_count;
    }

    public String getLanguage() { return language; }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", stargazers_count=" + stargazers_count +
                ", watchers_count=" + watchers_count +
                ", language='" + language + '\'' +
                '}';
    }
}
