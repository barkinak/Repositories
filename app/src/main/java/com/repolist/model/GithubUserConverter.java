package com.repolist.model;

import androidx.room.TypeConverter;

public class GithubUserConverter {

    @TypeConverter
    public static GithubUser toGithubUser(String avatar_url){
        return new GithubUser(avatar_url);
    }

    @TypeConverter
    public static String toURL(GithubUser githubUser){
        return githubUser.avatar_url;
    }
}
