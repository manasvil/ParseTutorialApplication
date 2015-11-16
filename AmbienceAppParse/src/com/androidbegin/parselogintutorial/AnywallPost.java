package com.androidbegin.parselogintutorial;

import com.parse.ParseClassName;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseFile;

/**
 * Data model for a post.
 */
@ParseClassName("Song")
public class AnywallPost extends ParseObject {
  public String getText() {
    return getString("text");
  }

  public void setText(String value) {
    put("text", value);
  }

  public String getUser() {
    return getString("user");
  }



  public void setAudio(ParseFile file) {
	    put("songfile", file);
	  }
  public ParseFile getAudio(ParseFile file) throws ParseException {
	  ParseFile audioFile = (ParseFile) this.get("songfile");
	  audioFile.getDataInBackground(new GetDataCallback() {
	    public void done(byte[] data, ParseException e) {
	      if (e == null) {
	        // data has the bytes for the image
	      } else {
	        // something went wrong
	      }
	    }
	  });
	return audioFile;  
	  
	  
	  
	  }
  



  public static ParseQuery<AnywallPost> getQuery() {
    return ParseQuery.getQuery(AnywallPost.class);
  }

public void setUser(String string) {
	 put("user", string);
}
}
