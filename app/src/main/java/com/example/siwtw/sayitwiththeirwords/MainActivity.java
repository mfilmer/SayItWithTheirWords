package com.example.siwtw.sayitwiththeirwords;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        final String soundRootDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/SIWTW/";
        //final String iconRootDirectory = soundRootDirectory + "/icons/";
        final String configFileName = soundRootDirectory + "config.cfg";

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, soundRootDirectory, duration);
        toast.show();

        ListView lv;
        final ArrayList<SoundItem> soundItems = new ArrayList<>();

        //Read text from file
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFileName));
            String line;

            while ((line = br.readLine()) != null) {
                String[] paths = line.split(",");
                switch (paths.length) {
                    case 1:
                        soundItems.add(new SoundItem(paths[0]));
                        break;
                    case 2:
                        soundItems.add(new SoundItem(paths[0], paths[1]));
                        break;
                    case 3:
                        soundItems.add(new SoundItem(paths[0], paths[1], paths[2]));
                }
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        lv = (ListView)findViewById(R.id.soundsListView);
        SoundItemListAdapter adapter=new SoundItemListAdapter(this, soundItems);

        if (lv != null) {
            lv.setAdapter(adapter);
            //lv.setAdapter(new ArrayAdapter<String>(this,
            //R.layout.sound_list_item, R.id.soundDisplayName, FilesInFolder));

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    playChosenSound(soundItems.get(position).soundID);
                }
            });

            // Set up soundpool
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            for (SoundItem thisSound : soundItems) {
                thisSound.soundID = soundPool.load(soundRootDirectory + thisSound.audioPath, 0);
            }
        }
    }

    //@Override
    //public void on

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE//,
            //Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void stopPlayback(View v)
    {
        soundPool.autoPause();
    }

    public void playChosenSound(int audioIndex)
    {
        soundPool.play(audioIndex,1,1,0,0,1);
    }
}
