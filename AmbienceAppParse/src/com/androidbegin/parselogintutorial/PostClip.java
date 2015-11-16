package com.androidbegin.parselogintutorial;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.parse.ParseObject;
import com.parse.ParseFile;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;



public class PostClip extends Activity {
	
  // UI references.
  private EditText postEditText;
  private TextView characterCountTextView;
  private Button postButton;

  private int maxCharacterCount = 140;
  
  private String outputFile = null;
  ParseFile songfile;
  File recording;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.postclip);
    
   
    songfile=new ParseFile(Util.convert());

    postEditText = (EditText) findViewById(R.id.post_edittext);
    postEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
      }

      @Override
      public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        updatePostButtonState();
        updateCharacterCountTextViewText();
      }
    });

    characterCountTextView = (TextView) findViewById(R.id.character_count_textview);

    postButton = (Button) findViewById(R.id.post_button);
    postButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        post();
      }
    });

    updatePostButtonState();
    updateCharacterCountTextViewText();
  }

  private void post () {
    String text = postEditText.getText().toString().trim();

    // Set up a progress dialog
    final ProgressDialog dialog = new ProgressDialog(PostClip.this);
    dialog.setMessage(getString(R.string.progress_post));
    dialog.show();

    // Create a post.
    AnywallPost post = new AnywallPost();

 
    post.setText(text);
    post.setUser(ParseUser.getCurrentUser().getString("username"));
    ParseACL acl = new ParseACL();
    post.setAudio(songfile);
    // Give public read access
    acl.setPublicReadAccess(true);
    post.setACL(acl);
    try {
		extracttags(text,post);
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    // Save the post
    post.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
//    	  e.printStackTrace(); 	  

        dialog.dismiss();
        Intent intent=new Intent(PostClip.this, Welcome.class);
        startActivity(intent);
        finish();
      }
    });
  }

  private String getPostEditTextText () {
    return postEditText.getText().toString().trim();
  }

  private void updatePostButtonState () {
    int length = getPostEditTextText().length();
    boolean enabled = length > 0 && length < maxCharacterCount;
    postButton.setEnabled(enabled);
  }

  private void updateCharacterCountTextViewText () {
    String characterCountString = String.format("%d/%d", postEditText.length(), maxCharacterCount);
    characterCountTextView.setText(characterCountString);
  }
  
  
  public void extracttags(String test,AnywallPost post) throws ParseException{
		
		Pattern TAG_REGEX  = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");
		 
		      Matcher matcher = TAG_REGEX.matcher(test);
		      while (matcher.find()) {
		    	   HashTag tag = new HashTag();

		    	   tag.setTag(matcher.group());
		    	   tag.setClip(post);
		    	    Log.v("Found this tag",matcher.group());
		    	   tag.save();
		        
		         

		     }
		      return;
		 }

  
}
