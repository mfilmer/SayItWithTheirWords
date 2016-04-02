package com.example.siwtw.sayitwiththeirwords;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MediaPlayer> mps = new ArrayList<MediaPlayer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        final String soundRootDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/SIWTW";

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, soundRootDirectory, duration);
        toast.show();

        //final String soundRootDirectory = "/storage/emulated/0/Music/SIWTW";
        ListView lv;
        final ArrayList<String> FilesInFolder = GetFiles(soundRootDirectory);
        final ArrayList<SoundItem> soundItems = new ArrayList();
        soundItems.add(new SoundItem(R.drawable.ic_launcher, "test name 1"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher2, "test name 2"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher, "test name 1"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher2, "test name 2"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher, "test name 1"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher2, "test name 2"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher, "test name 1"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher2, "test name 2"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher, "test name 1"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher2, "test name 2"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher, "test name 1"));
        soundItems.add(new SoundItem(R.drawable.ic_launcher2, "test name 2"));

        lv = (ListView)findViewById(R.id.soundsListView);
        SoundItemListAdapter adapter=new SoundItemListAdapter(this, soundItems);

        lv.setAdapter(adapter);
        //lv.setAdapter(new ArrayAdapter<String>(this,
                //R.layout.sound_list_item, R.id.soundDisplayName, FilesInFolder));

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                playChosenSound(soundRootDirectory + "/" + FilesInFolder.get(position));
            }
        });*/
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE//,
            //Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
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

    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }

    public void stopPlayback(View v)
    {
        for (MediaPlayer mp: mps) {
            mp.stop();
            mp.release();
        }
        mps.clear();
    }

    public void playChosenSound(String audioPathName)
    {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(audioPathName);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mplayer) {
                mps.remove(mplayer);
                mplayer.release();
            }
        });
        mps.add(mp);
    }

    public void playTestSound(View v)
    {
        MediaPlayer mp = new MediaPlayer();

        //String testAudioFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/flimflams.ogg";
        //String testAudioFileName = "/data/media/Music/flimflams.ogg";
        String testAudioFileName = "/storage/emulated/0/Music/SIWTW/I Noticed You Left out Film Flams.wav";

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, testAudioFileName, duration);
        toast.show();

        try {
            mp.setDataSource(testAudioFileName);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }
}
