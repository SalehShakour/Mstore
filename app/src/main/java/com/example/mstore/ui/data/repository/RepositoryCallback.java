package com.example.mstore.ui.data.repository;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
