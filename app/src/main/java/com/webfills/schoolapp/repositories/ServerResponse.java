package com.webfills.schoolapp.repositories;

public interface ServerResponse<T> {
    void onSuccess(T body);
    void onFailure(Throwable error);
}
