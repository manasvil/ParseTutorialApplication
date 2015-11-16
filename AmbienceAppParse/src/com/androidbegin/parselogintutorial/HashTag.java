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
@ParseClassName("Hashtag")
public class HashTag extends ParseObject {


	  public String getTag() {
		    return getString("hashtag");
		  }

		  public void setTag(String value) {
		    put("hashtag", value);
		  }

		  public AnywallPost getClip() {
		    return (AnywallPost) getParseObject("songclip");
		  }

		  public void setClip(AnywallPost clip) {
			   put("songclip",clip);
			  }
		  
  public static ParseQuery<HashTag> getQuery() {
    return ParseQuery.getQuery(HashTag.class);
  }


}
