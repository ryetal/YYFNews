package com.yyf.www.project_quicknews.okhttp;

import okhttp3.Response;

public interface Parser<T> {
    T parse(Response response);
}
