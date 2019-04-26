package com.kr_raviteja.lab10b_new;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    public MediaPlayer mediaplayer;
    public ListView listview;
    public File folder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        File extdirectory = Environment.getExternalStorageDirectory();
        String folder_name = "MyREcordings";
        folder = new File(extdirectory.getAbsolutePath(),folder_name);
        listview = (ListView)findViewById(R.id.listview);

        InflateUI();
    }

    public void InflateUI() {


        File [] fileslist = folder.listFiles();
        String [] filenames = new String[fileslist.length];
        for(int i=0;i<fileslist.length;i++) {
            filenames[i] = fileslist[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.individualView,filenames);
        listview.setAdapter(adapter);
    }

    public void startPlaying(View view) {

        try {
            if(mediaplayer != null) {
                mediaplayer.release();
                mediaplayer = null;
            }
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(this,"cannot delete properly",Toast.LENGTH_LONG);
            return;
        }

        mediaplayer = new MediaPlayer();
        TextView t = (TextView)view;
        String music_filename = t.getText().toString();

        String filepath = folder.getAbsolutePath() + "/" + music_filename;
        Toast.makeText(this,"playing",Toast.LENGTH_SHORT).show();

        try {
            mediaplayer.setDataSource(filepath);
            mediaplayer.prepare();
            mediaplayer.start();
        }
        catch (IOException e) {
            Toast.makeText(this,"error playing",Toast.LENGTH_LONG).show();
        }
    }


    public void stopPlaying(View view) {
        mediaplayer.release();
        mediaplayer = null;
        return ;
    }
}
