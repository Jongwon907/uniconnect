package com.example.demo.common.verify;

public interface StoreHandler {
    public void save(String key);
    public void delete(String key);
    public String get(String key);
}
