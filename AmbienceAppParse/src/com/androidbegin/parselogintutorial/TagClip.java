package com.androidbegin.parselogintutorial;


import java.io.IOException;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import android.media.MediaPlayer;




public class TagClip extends Activity  {
	
	private ParseQueryAdapter<HashTag> postsQueryAdapter;
	private String output; 
	EditText tag;
	String hash;
	 MediaPlayer m = new MediaPlayer();
   
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		   
		    setContentView(R.layout.tagclipview);
		   tag = (EditText) findViewById(R.id.tag);
		
		   Button search = (Button) findViewById(R.id.findbutton);
			
		    //stores after conversion from parse file
		      output = Environment.getExternalStorageDirectory().
		    	      getAbsolutePath() + "/output.3gp";
		      
				search.setOnClickListener(new OnClickListener() {

					public void onClick(View view) {
					 hash=tag.getText().toString();
				     	update();						
					}
				});

		
	}
		  @Override
		   public boolean onCreateOptionsMenu(Menu menu) {
		      // Inflate the menu; this adds items to the action bar if it is present.
		      getMenuInflater().inflate(R.menu.welcome, menu);
		      return true;
		   }

		  @Override
		  public boolean onOptionsItemSelected(MenuItem item) {
		      // Handle presses on the action bar items
		      switch (item.getItemId()) {
		          case R.id.action_stop:
		              m.stop();
		              return true;
		      } return true;
		  }
		  

		  
		  public void update()
		  {
  
		    // Set up a customized query
		    ParseQueryAdapter.QueryFactory<HashTag> factory =
		        new ParseQueryAdapter.QueryFactory<HashTag>() {
		          public ParseQuery<HashTag> create() {		        	
		            ParseQuery<HashTag> query = HashTag.getQuery();
		           query.orderByDescending("createdAt");
		           query.whereEqualTo("hashtag",hash);
		           Log.e("hash",hash);
		           query.include("songclip");
		            return query;
		          }
		        };

		    // Set up the query adapter
		    postsQueryAdapter = new ParseQueryAdapter<HashTag>(this, factory) {
		      @Override
		      public View getItemView(HashTag post, View view, ViewGroup parent) {
		        if (view == null) {
		          view = View.inflate(getContext(), R.layout.task_row_item, null);
		        }
		        TextView contentView = (TextView) view.findViewById(R.id.description);
		        TextView usernameView = (TextView) view.findViewById(R.id.uname);
		        contentView.setText(post.getClip().getText());
		        usernameView.setText(post.getClip().getUser());
		        return view;
		      }
		    };
	    

		    // Disable automatic loading when the adapter is attached to a view.
		//    postsQueryAdapter.setAutoload(false);

		    // Disable pagination, we'll manage the query limit ourselves
		 //   postsQueryAdapter.setPaginationEnabled(false);

		    // Attach the query adapter to the view
		    ListView postsListView = (ListView) findViewById(R.id.cliplist);
		    postsListView.setAdapter(postsQueryAdapter);
		    

		    // Set up the handler for an item's selection
		    postsListView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	m.stop();
		    	m.reset();
		        final HashTag item = postsQueryAdapter.getItem(position);
		        AnywallPost clip= item.getClip();
		        ParseFile songFile = (ParseFile) clip.get("songfile");
		        songFile.getDataInBackground(new GetDataCallback() {
						@Override
				public void done(byte[] data, ParseException e) {
					// TODO Auto-generated method stub
					if(e==null)
					{ 
						Log.e("FILE HAS COME","LOOK ");
						Util.convert2(data);
					
						   try {
						    	m.stop();
						    	m.reset();
						    	m.setDataSource(output);
						    	m.setLooping(true);
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SecurityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalStateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						   try {
							m.prepare();
						} catch (IllegalStateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						   m.start();
						
					}
					
					else{
						
						 Toast.makeText(getApplicationContext(), "Could not get File", Toast.LENGTH_LONG).show();	
					}
				}
											
		        });
		      }
		    });			  
			  
		  }
		  protected void onExit(Bundle savedInstanceState) {
			  super.onCreate(savedInstanceState);
			  m.stop();
			  m.release();
			  
			  
		  }
		  
		  @Override
		  public void onBackPressed(){

		     m.release();
		     finish();
		  }

		  
		  
}