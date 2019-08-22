package com.restAPIApplication.service;

import com.restAPIApplication.database.DatabaseClass;
import com.restAPIApplication.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileService {

    private Map<String, Profile> profiles = DatabaseClass.getProfile();

    public ProfileService(){
        profiles.put("Muhammad Ali", new Profile(1L, "Muhammad Ali", "Muhammad", "Ali"));
    }

    public List<Profile> getAllProfiles() {
        return new ArrayList<Profile>(profiles.values());
    }

    public Profile getProfile(String profileName) {
        return profiles.get(profileName);
    }

    public Profile addProfile(Profile profile){
        profile.setId(profiles.size()+1);
        profiles.put(profile.getProfileName(), profile);
        return  profile;
    }

    public  Profile updateProfile(Profile profile){
        if(profile.getProfileName().isEmpty()) return null;
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    public Profile removeProfile(String profileName){
        return profiles.remove(profileName);
    }


}