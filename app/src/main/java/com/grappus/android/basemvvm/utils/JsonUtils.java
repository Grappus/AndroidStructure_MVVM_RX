package com.grappus.android.basemvvm.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;


public final class JsonUtils {

    private static Gson mGson = getGsonObject();

    private static Gson getGsonObject() {
        return new Gson();
    }


    public static String jsonify(Object object) {
        if (object == null) return null;
        return mGson.toJson(object);
    }

    public static Object objectify(String jsonString, Class<?> T) {
        return mGson.fromJson(jsonString, T);
    }

    public static <T> Object arrayObjectify(String jsonString, Type listType) {
        return mGson.fromJson(jsonString, listType);
    }


    public static String getStringFromJsonAssetFile(Context context, String assetFileName) {
        try {
            InputStream is = context.getAssets().open(assetFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonFromFile = new String(buffer, "UTF-8");
            return jsonFromFile;
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }
}