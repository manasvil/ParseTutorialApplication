package com.androidbegin.parselogintutorial;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import android.app.Application;
 
public class ParseApplication extends Application {
 
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(AnywallPost.class);
        ParseObject.registerSubclass(HashTag.class);
        // Add your initialization code here
        Parse.initialize(this, "uUAi7FhU7rz4u0ng0JGzxU4RBoHihaEqD3hMhA67", "1Q7vqmP7z28lAaWTdC94eWroJHSbtvRkkVrGRkJW");
        
 
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
 
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
 
        ParseACL.setDefaultACL(defaultACL, true);
    }
 
}