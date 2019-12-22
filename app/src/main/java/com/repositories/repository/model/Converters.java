package com.repositories.repository.model;

import com.google.gson.Gson;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Owner gsonToOwner(String json) {
        return new Gson().fromJson(json, Owner.class);
    }

    @TypeConverter
    public static String ownerToGson(Owner owner) {
        return new Gson().toJson(owner);
    }
}