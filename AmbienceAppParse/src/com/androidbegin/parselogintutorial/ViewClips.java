package com.androidbegin.parselogintutorial;


import java.io.IOException;

import android.app.Activity;

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




public class ViewClips extends Activity  {
	
	private ParseQueryAdapter<AnywallPost> postsQueryAdapter;
	private String output; 
	MediaPlayer  m = new MediaPlayer();;

		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		
		    setContentView(R.layout.clipview);
		    //stores after conversion from parse file
		      output = Environment.getExternalStorageDirectory().
		    	      getAbsolutePath() + "/output.3gp";
		    // Set up a customized query
		    ParseQueryAdapter.QueryFactory<AnywallPost> factory =
		        new ParseQueryAdapter.QueryFactory<AnywallPost>() {
		          public ParseQuery<AnywallPost> create() {
		        
		            ParseQuery<AnywallPost> query = AnywallPost.getQuery();
		           query.orderByDescending("createdAt");   
		            return query;
		          }
		        };

		    // Set up the query adapter
		    postsQueryAdapter = new ParseQueryAdapter<AnywallPost>(this, factory) {
		      @Override
		      public View getItemView(AnywallPost post, View view, ViewGroup parent) {
		        if (view == null) {
		          view = View.inflate(getContext(), R.layout.task_row_item, null);
		        }
		        TextView contentView = (TextView) view.findViewById(R.id.description);
		        TextView usernameView = (TextView) view.findViewById(R.id.uname);
		        contentView.setText(post.getText());
		        usernameView.setText(post.getUser());
		        return view;
		      }
		    };
		    
	
		    
		    
		    

		    // Disable automatic loading when the adapter is attached to a view.
		//    postsQueryAdapter.setAutoload(false);

		    // Disable pagination, we'll manage the query limit ourselves
		 //   postsQueryAdapter.setPaginationEnabled(false);

		    // Attach the query adapter to the view
		    ListView postsListView = (ListView) findViewById(R.id.list);
		    postsListView.setAdapter(postsQueryAdapter);
		    

		    // Set up the handler for an item's selection
		    postsListView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		        final AnywallPost item = postsQueryAdapter.getItem(position);
		        m.stop();
		        m.reset();
		        ParseFile songFile = (ParseFile) item.get("songfile");
			
				
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
		  
		  protected void onPause(Bundle savedInstanceState) {
			  super.onCreate(savedInstanceState);
			  m.stop();
			  m.release();
			  
			  
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