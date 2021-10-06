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

public class Phrases extends AppCompatActivity {

    private final ArrayList<ModelClass> list = new ArrayList<ModelClass>();
    private ListView lvPhrases;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener listener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT ||
            focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    MediaPlayer.OnCompletionListener mediaInterface = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Release Media Player After Playing is Done
     */
    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(listener);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        lvPhrases = findViewById(R.id.lvPhrases);

        list.add(new ModelClass("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        list.add(new ModelClass("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        list.add(new ModelClass("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        list.add(new ModelClass("How are you feeling?","michәksәs?", R.raw.phrase_how_are_you_feeling));
        list.add(new ModelClass("I'm feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        list.add(new ModelClass("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        list.add(new ModelClass("Yes, I'm coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        list.add(new ModelClass("I'm coming.","әәnәm",R.raw.phrase_im_coming));
        list.add(new ModelClass("Let's go.","yoowutis",R.raw.phrase_lets_go));
        list.add(new ModelClass("Come here.","әnni'nem",R.raw.phrase_come_here));

        ModelClassAdapter adapter = new ModelClassAdapter(this, list,R.color.background_number);
        lvPhrases.setAdapter(adapter);

        lvPhrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelClass modelClass = list.get(position);

                releaseMediaPlayer();
                int result = audioManager.requestAudioFocus(listener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(Phrases.this, modelClass.getMusic());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mediaInterface);
                }

            }
        });

    }
}