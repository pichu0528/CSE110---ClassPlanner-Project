package com.example.annchih.classplanner;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(getApplicationContext());
        ParseObject.registerSubclass(LocalPlanner.class);
        Parse.initialize(this, "nfKldYlgBloXoaCzcFtkZgVQFP4soJiohABrIX1o", "cX8HgSU6t03OqGMnsreTHYcSKLGxV8D0sAonyZ9v");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

    }

}