package com.daniel.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;
import java.time.LocalDateTime;

public class AuthService {
    // active sessions: apiKey -> userId
    private static ConcurrentHashMap<String, Integer> activeSessions = new ConcurrentHashMap<>();
    // persistent storage: userId -> saved apiKey
    private static ConcurrentHashMap<Integer, String> savedKeys = new ConcurrentHashMap<>();
    private static SecureRandom random = new SecureRandom();
    private static final String AUTH_FILE = "auth_data.ser";
    
    // load saved sessions on startup
    static {
        loadSessions();
    }
    
    // generate new API key for a user
    public static String generateApiKey(int userId) {
        // revoke old key if exists
        if(savedKeys.containsKey(userId)) {
            String oldKey = savedKeys.get(userId);
            activeSessions.remove(oldKey);
        }
        
        // generate new key
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        String apiKey = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        
        // store in both maps
        activeSessions.put(apiKey, userId);
        savedKeys.put(userId, apiKey);
        
        // save to file
        saveSessions();
        
        return apiKey;
    }
    
    // validate API key and return userId
    public static Integer validateApiKey(String apiKey) {
        if(apiKey == null || apiKey.isEmpty()) return null;
        return activeSessions.get(apiKey);
    }
    
    // revoke API key on logout
    public static void revokeApiKey(String apiKey) {
        Integer userId = activeSessions.remove(apiKey);
        if(userId != null) {
            savedKeys.remove(userId);
            saveSessions();
        }
    }
    
    // check if authenticated
    public static boolean isAuthenticated(String apiKey) {
        return apiKey != null && activeSessions.containsKey(apiKey);
    }
    
    // get active session count
    public static int getActiveSessionCount() {
        return activeSessions.size();
    }
    
    // get user ID from API key
    public static Integer getUserId(String apiKey) {
        return activeSessions.get(apiKey);
    }
    
    // save sessions to file
    private static void saveSessions() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(AUTH_FILE))) {
            oos.writeObject(savedKeys);
        } catch(IOException e) {
            System.out.println("Failed to save auth data: " + e.getMessage());
        }
    }
    
    // load sessions from file
    @SuppressWarnings("unchecked")
    private static void loadSessions() {
        File file = new File(AUTH_FILE);
        if(!file.exists()) return;
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            savedKeys = (ConcurrentHashMap<Integer, String>) ois.readObject();
            // rebuild active sessions
            for(var entry : savedKeys.entrySet()) {
                activeSessions.put(entry.getValue(), entry.getKey());
            }
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Failed to load auth data: " + e.getMessage());
        }
    }
    
    // display current user's API key
    public static void showApiKey(int userId) {
        String key = savedKeys.get(userId);
        if(key == null) {
            System.out.println("No API key found for user " + userId);
        } else {
            System.out.println("Your API Key: " + key);
            System.out.println("Copy this key. You will need it for all operations.");
        }
    }
}