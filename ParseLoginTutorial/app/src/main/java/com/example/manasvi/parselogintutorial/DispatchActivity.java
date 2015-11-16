package com.example.manasvi.parselogintutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

import java.text.ParseException;

/**
 * Activity which starts an intent for either the logged in (MainActivity) or logged out
 * (SignUpOrLoginActivity) activity.
 */
public class DispatchActivity extends Activity {

  public DispatchActivity() {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    // Determine whether the current user is an anonymous user
    try {       Log.e("DEBUG ", "Got to 1st check");
      if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
        Log.e("DEBUG","Got to 2nd check" );
        // If user is anonymous, send the user to LoginSignupActivity.class
        Intent intent = new Intent(DispatchActivity.this,
                WelcomeActivity.class);
        startActivity(intent);
        finish();
      }

    else {
      // If current user is NOT anonymous user
      // Get current user data from Parse.com
      ParseUser currentUser = ParseUser.getCurrentUser();
      if (currentUser != null) {
        Log.e("DEBUG","Got to 3rd check" );
        Intent intent = new Intent(DispatchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      } else {
        // Send user to LoginSignupActivity.class
        Intent intent = new Intent(DispatchActivity.this,
                WelcomeActivity.class);
        startActivity(intent);
        finish();
      }
    }
  }
    catch (android.net.ParseException e)
    {
      e.printStackTrace();
    }
  }


}
