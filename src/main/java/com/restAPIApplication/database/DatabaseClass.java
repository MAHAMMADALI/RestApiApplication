package com.restAPIApplication.database;

import com.restAPIApplication.model.Message;
import com.restAPIApplication.model.Profile;
import java.util.HashMap;
import java.util.Map;

public class DatabaseClass {
    private static Map<Long, Message> messages = new HashMap<>();
    private static Map<String, Profile> profile = new HashMap<>();

    public static Map<Long, Message> getMessages() {
        return messages;
    }

    public static Map<String, Profile> getProfile() {
        return profile;
    }
}
