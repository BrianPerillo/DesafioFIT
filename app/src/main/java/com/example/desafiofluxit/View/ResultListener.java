package com.example.desafiofluxit.View;

public interface ResultListener<T, String> {
    public void onFinish(T result, String error);
}
