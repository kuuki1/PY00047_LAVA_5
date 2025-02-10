package com.example.Lab5.services;

public interface SessionService {
    void remove(String name);

	void set(String name, Object value);

	<T> T get(String name);
}
