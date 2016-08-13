package com.example.annchih.classplanner;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.UUID;

@ParseClassName("LocalPlanner")
public class LocalPlanner extends ParseObject {

    public String getID() {
        return getString("id");
    }

    public void setID(String id) {
        put("id", id);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public boolean getTaken() {
        return getBoolean("taken");
    }

    public void setTaken(boolean taken) {
        put("taken", taken);
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser currentUser) {
        put("author", currentUser);
    }

    public boolean isDraft() {
        return getBoolean("isDraft");
    }

    public void setDraft(boolean isDraft) {
        put("isDraft", isDraft);
    }

    public void setUuidString() {
        UUID uuid = UUID.randomUUID();
        put("uuid", uuid.toString());
    }

    public String getUuidString() {
        return getString("uuid");
    }

    public static ParseQuery<LocalPlanner> getQuery() {
        return ParseQuery.getQuery(LocalPlanner.class);
    }
}