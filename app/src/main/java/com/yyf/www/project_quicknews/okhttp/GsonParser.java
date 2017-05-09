package com.yyf.www.project_quicknews.okhttp;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import okhttp3.Response;


public class GsonParser<T> implements Parser<T> {

    private Type mType;

    public GsonParser(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("type can not be null");
        }
        mType = type;
    }

    @Override
    public T parse(Response response) {
        Gson gson = new Gson();
        return gson.fromJson(response.body().charStream(), mType);
    }
}
