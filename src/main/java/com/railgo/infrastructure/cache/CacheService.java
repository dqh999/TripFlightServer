package com.railgo.infrastructure.cache;

public interface CacheService {

    /**
     * Store a value in the cache with a specified key.
     *
     * @param key   The key to store the value.
     * @param value The value to be stored.
     */
    void put(String key, Object value);

    /**
     * Store a value in ttl in the cache with a specified key
     *
     * @param key   The key to store the value
     * @param value The value to be stored.
     * @param ttl   The time to live in store.
     */
    void put(String key, Object value, long ttl);

    /**
     * Try to acquire a lock for the given key for a specified TTL (time to live).
     *
     * @param key The key to lock.
     * @param ttl The time to live (TTL) in milliseconds.
     * @return true if the lock was successfully acquired, false if not.
     */
    boolean acquireLock(String key, long ttl);

    /**
     * Release the lock for the given key.
     *
     * @param key The key to release the lock.
     */
    void releaseLock(String key);

    /**
     * Retrieve a value from the cache by key.
     *
     * @param key The key of the data to retrieve.
     * @return The value stored in the cache, or null if not found.
     */
    Object get(String key);

    /**
     * Remove a value from the cache by key.
     *
     * @param key The key of the value to remove.
     */
    void delete(String key);

    /**
     * Check if a value exists in the cache.
     *
     * @param key The key to check for existence.
     * @return true if the value exists in the cache, false if not.
     */
    boolean exists(String key);
}
