package com.androidbegin.parselogintutorial;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.parse.Parse;
import com.androidbegin.parselogintutorial.Util;


public class RecordSound extends Activity {
	ParseFile file1;

   private MediaRecorder myAudioRecorder;
   private String outputFile = null;
   private String output = null;
   private Button start,stop,play,postmessage;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      
      Log.e("Record","Error");
      setContentView(R.layout.recordsound);
      
      start = (Button)findViewById(R.id.button1);
      stop = (Button)findViewById(R.id.button2);
      play = (Button)findViewById(R.id.button3);
      postmessage = (Button)findViewById(R.id.postmessage);

      stop.setEnabled(false);
      play.setEnabled(false);
     //stores the recording
      outputFile = Environment.getExternalStorageDirectory().
      getAbsolutePath() + "/myrecording.3gp";
      
      
      //stores after conversion from parse file
      output = Environment.getExternalStorageDirectory().
    	      getAbsolutePath() + "/output.3gp";

      myAudioRecorder = new MediaRecorder();
      myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
      myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
      myAudioRecorder.setOutputFile(outputFile);
      
     postmessage.setOnClickListener(
    		  new OnClickListener() {

    				public void onClick(View view) {
    					Intent intent = new Intent(
    						
    						RecordSound.this,PostClip.class);
    					startActivity(intent);  }  
    			     
    					
    				}
    		  );

   }

   public void start(View view){
      try {
         myAudioRecorder.prepare();
         myAudioRecorder.start();
      } catch (IllegalStateException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      start.setEnabled(false);
      stop.setEnabled(true);
      Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

   }

   public void stop(View view){
      myAudioRecorder.stop();
      myAudioRecorder.release();
      myAudioRecorder  = null;
      stop.setEnabled(false);
      play.setEnabled(true);
      Toast.makeText(getApplicationContext(), "Audio recorded successfully",
      Toast.LENGTH_LONG).show();
      file1=new ParseFile(Util.convert());
   }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.welcome, menu);
      return true;
   }
   public void play(View view) throws IllegalArgumentException,   
   SecurityException, IllegalStateException, IOException, ParseException{
   Util.convert2(file1.getData());
   MediaPlayer m = new MediaPlayer();
   m.setDataSource(output);
   m.prepare();
   m.start();
   Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

   }

}