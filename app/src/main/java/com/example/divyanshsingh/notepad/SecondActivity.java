package com.example.divyanshsingh.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Divyansh Singh on 06-11-2017.
 */

public class SecondActivity extends MainActivity {
    EditText readingView;
    TextView appRestartVIew;

    @Override
    protected void onPause() {
        super.onPause();
        saveTextFile(readingView.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        readingView.setText(getTextFile());
    }

    private static String DATA_FILE ;
    private int times = 0;
    private static final String NUMBER_OF_TIMES = "NUMBER_OF_TIMES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        readingView = (EditText) findViewById(R.id.displayView);
        appRestartVIew = (TextView) findViewById(R.id.run);
        Intent intent = getIntent();
        DATA_FILE = intent.getStringExtra("ID");

        int defaultValue = 0;

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        times = sharedPreferences.getInt(NUMBER_OF_TIMES, defaultValue);
        times++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUMBER_OF_TIMES, times);
        editor.commit();

        appRestartVIew.setText(String.valueOf(times));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        saveTextFile(readingView.getText().toString());
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

        return true;
    }
    public void saveTextFile(String content) {

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
        } catch (FileNotFoundException e) {
            Log.e("FILE", "Could'nt find that file ");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("FILE", "IO exception");
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getTextFile() {
        FileInputStream fileInputStream = null;
        String fileData = null;

        try {
            fileInputStream = openFileInput(DATA_FILE);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            fileData = new String(buffer, "UTF-8");
        } catch (FileNotFoundException e) {
            Log.e("FILE", "Could'nt find that file ");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("FILE", "IO exception");
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileData;
    }
}
