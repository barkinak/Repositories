package com.repositories.repository.model;

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
    public Owner owner;
    //----------------------------------------------------------------------------------------------
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

    public Boolean getIsFavorite() {
        return is_favorite;
    }

    public Owner getOwner() {
        return owner;
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
