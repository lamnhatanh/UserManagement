package com.brian.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class LoginAttemptService {

    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;
    private LoadingCache<String, Integer> loginAttemptCache;

    // Initialize the cache
    public LoginAttemptService() {
        super();
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, MINUTES)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void evictUserFromLoginAttemptCache(String username) {
        loginAttemptCache.invalidate(username);
        System.out.println("Clear the cache for that username");
        try {
            System.out.println("after clear: " + loginAttemptCache.get(username));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUserToLoginAttemptCache(String username) throws ExecutionException {
        int attempts = 0;
        attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
        loginAttemptCache.put(username, attempts);
    }

    public boolean hasExceededMaxAttempts(String username) {
        try {
            return ((loginAttemptCache.get(username))  >= MAXIMUM_NUMBER_OF_ATTEMPTS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
