package com.sharma.aditya.udacityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Family extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ListView listView ;
    private final ArrayList<ModelClass> arrayList = new ArrayList<ModelClass>();
    private AudioManager audioManager;

    private AudioManager.OnAudioFocusChangeListener listener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT||
                    focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK ) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaFile();
            }
        }
    };

    /**
     * OnCompletionListener when music completed its playing
     */
    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaFile();
        }
    };

    /**
     * Release Media File When App Goes To Background
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaFile();
        arrayList.clear();
    }

    private void releaseMediaFile() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(listener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        listView = findViewById(R.id.lvFamily);

        arrayList.add(new ModelClass("Father","әpә",R.drawable.family_father,R.raw.family_father));
        arrayList.add(new ModelClass("Mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        arrayList.add(new ModelClass("Son","angsi",R.drawable.family_son, R.raw.family_son));
        arrayList.add(new ModelClass("Daughter","tune",R.drawable.family_daughter, R.raw.family_daughter));
        arrayList.add(new ModelClass("Older Brother","taachi",R.drawable.family_older_brother, R.raw.family_older_brother));
        arrayList.add(new ModelClass("Younger Brother","chalitti",R.drawable.family_younger_brother, R.raw.family_younger_brother));
        arrayList.add(new ModelClass("Older Sister","teṭe",R.drawable.family_older_sister, R.raw.family_older_sister));
        arrayList.add(new ModelClass("Younger Sister","kolliti",R.drawable.family_younger_sister, R.raw.family_younger_sister));
        arrayList.add(new ModelClass("Grandmother","ama",R.drawable.family_grandmother, R.raw.family_grandmother));
        arrayList.add(new ModelClass("Grandfather","paapa",R.drawable.family_grandfather, R.raw.family_grandfather));

        ModelClassAdapter adapter = new ModelClassAdapter(this, arrayList,R.color.background_family);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelClass modelClass = arrayList.get(position);


                //Release Media Player if previous media file is playing
                releaseMediaFile();

                int result = audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Start Media Player
                    mediaPlayer = MediaPlayer.create(Family.this, modelClass.getMusic());
                    mediaPlayer.start();

                    //OnCompletionListener
                    mediaPlayer.setOnCompletionListener(completionListener);
                }

            }
        });
    }
}