package com.grappus.android.basemvvm.controllers.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grappus.android.basemvvm.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ListTypeConverter {

    @TypeConverter
    public static List<String> fromString(String value) {

        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<String> list) {
        return JsonUtils.jsonify(list);
    }
}
