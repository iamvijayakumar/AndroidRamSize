package com.example.ramsizememorysize;

import java.io.File;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seekbar);

       HalfSeekabr halfSeekBar = (HalfSeekabr)findViewById(R.id.volume_bar);
     
        
        if(externalMemoryAvailable()){
        Toast.makeText(this, "Size :: "+getAvailableInternalMemorySize(), Toast.LENGTH_LONG).show();
    	Log.e("ram", " ********:: " +getAvailableExternalMemorySize() + getAvailableInternalMemorySize() +getTotalExternalMemorySize() + getTotalInternalMemorySize());
    	 File path = Environment.getDataDirectory();
         StatFs stat = new StatFs(path.getPath());
     
         int blockSize = stat.getBlockSize();
         int totalBlocks = stat.getBlockCount();
         HalfSeekabr.DEFAULT_MAX  = blockSize*totalBlocks;
    	  halfSeekBar.setMax(blockSize*totalBlocks);
    	  
    	
         
          int availableBlocks = stat.getAvailableBlocks();
          halfSeekBar.setProgress(blockSize*availableBlocks);
          Log.v("ram", " :: " + blockSize*totalBlocks +" :: "+ blockSize*availableBlocks);
          TextView textFree = (TextView)findViewById(R.id.free_text);
          TextView totalSize = (TextView)findViewById(R.id.total_size);
          textFree.setText("Total :: " +getTotalExternalMemorySize());
          totalSize.setText("Available : "+ getAvailableExternalMemorySize());
        }
        else{
        	
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatSize(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatSize(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "Not Found";
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(totalBlocks * blockSize);
        } else {
            return "Not Found";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

}
