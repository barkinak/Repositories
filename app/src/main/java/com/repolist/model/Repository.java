package com.repolist.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Represents a Repository object returned from Github API.
 */

@Entity(tableName = "repositories")
public class Repository {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int repo_id;
    public String name;
    public String description;
    public int stargazers_count;
    public int watchers_count;
    public String language;
    public String avatar_url;
    public String user_id;
    public Boolean is_favorite = false;

    @Ignore
    public Repository() {}

    public Repository(int id, int repo_id, String name, String description, int stargazers_count, int watchers_count, String language) {
        this.id = id;
        this.repo_id = repo_id;
        this.name = name;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.watchers_count = watchers_count;
        this.language = language;
    }

    @Ignore
    public Repository(int repo_id, String name, String description, int stargazers_count, int watchers_count, String language) {
        this.repo_id = repo_id;
        this.name = name;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.watchers_count = watchers_count;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public int getRepoId() {
        return repo_id;
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

    public String getAvatarUrl() {
        return avatar_url;
    }

    public String getUserId() {
        return user_id;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public Boolean getIsFavorite() {
        return is_favorite;
    }

    public void setIsFavorite(Boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

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
