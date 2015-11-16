package com.androidbegin.parselogintutorial;

import java.io.File;
import java.io.*;
import java.io.FileInputStream;

import android.os.Environment;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Util{
	
	public static byte[] convert(){
File file1 = new File (Environment.getExternalStorageDirectory().
	      getAbsolutePath() + "/myrecording.3gp");

FileInputStream fis = null;

try {
    fis = new FileInputStream(file1);

} catch (FileNotFoundException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
ByteArrayOutputStream bos = new ByteArrayOutputStream();
  try {
    while (fis.available() > 0) {
            bos.write(fis.read());
           }
} catch (IOException e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
}


byte[] bytes = bos.toByteArray();
	 return bytes;
	
	
	}
	
	
	
	public static void convert2(byte[] bytes){
		File someFile = new File ( Environment.getExternalStorageDirectory().
	    	      getAbsolutePath() + "/output.3gp");

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(someFile);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fos.write(bytes);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fos.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fos.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		
		
		
		
		
		
		
		
	}
	
	
}