package com.kr_raviteja.lab10b_new;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public Button bplay,bstop,brecord;
    public static int thumb = 0;
    public MediaRecorder recorder;
    public File folder;
    public String lastfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bplay = (Button)findViewById(R.id.play);
        bstop = (Button)findViewById(R.id.stop);
        brecord = (Button)findViewById(R.id.record);
        bstop.setEnabled(false);

        Context context = getApplicationContext();
        SharedPreferencesWorkout();

        createDirectory();
    }

    public void SharedPreferencesWorkout() {
        SharedPreferences spref = getSharedPreferences("Mystaticdata",0);
        SharedPreferences.Editor editor = spref.edit();



        if(spref.getInt("thumb",0) == 0) {
            editor.putInt("thumb",0);
        }
        else {
            thumb = spref.getInt("thumb",0);
        }

        editor.commit();
        return ;
    }

    public void startPlaying(View view) {
        Intent i = new Intent(this,Main2Activity.class);
        startActivity(i);
    }

    public void createDirectory() {
        File extdirectory = Environment.getExternalStorageDirectory();
        String folder_name = "MyREcordings";
        folder = new File(extdirectory.getAbsolutePath(),folder_name);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        return ;
    }

    public void startrecording(View view) {

        bstop.setEnabled(true);
        brecord.setEnabled(false);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);



        Long date=new Date().getTime();
        String current_time = new Date(Long.valueOf(date)).toString();
        String file_name =  "raviteja"+ Integer.toString(thumb) + ".amr" ;
        String x = String.valueOf(thumb);
        thumb++;

        lastfile = folder.getAbsolutePath() + "/" + file_name;

        recorder.setOutputFile(folder.getAbsoluteFile()+"/"+file_name);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        }
        catch (IOException e) {
            Toast.makeText(this,"unable to record", Toast.LENGTH_SHORT).show();
        }

        recorder.start();
    }

    public void stoprecording(View view) {
        bstop.setEnabled(false);
        recorder.stop();
        recorder.release();
        recorder = null;
        brecord.setEnabled(true);

        AddtoSharedPreferences();
        return ;
    }

    public void AddtoSharedPreferences() {
        SharedPreferences spref = getApplicationContext().getSharedPreferences("Mystaticdata",0);
        SharedPreferences.Editor editor = spref.edit();

        editor.putInt("thumb",thumb);

        editor.commit();
        return ;
    }
}
