package com.sharma.aditya.udacityapp;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Colors extends AppCompatActivity {

    private ListView lvColors;
    private final ArrayList<ModelClass> arrayList = new ArrayList<ModelClass>();
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener audioFocusListener = new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {
            if ( focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ) {

                //Pause playback
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);  //beginning of the audio file

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN ) {
                //AUDIOFOCUS_GAIN case means we have regained focus and can resume playback
                mediaPlayer.start();

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //Loss The Focus So We will Clean the memory
                releaseMediaPlayer();
            }
        }
    };

    MediaPlayer.OnCompletionListener mediaInterface = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            /**
             * Releasing the mediaPlayer resources
             */
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            //Abandon audio focus when playback complete
            audioManager.abandonAudioFocus(audioFocusListener);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        lvColors = findViewById(R.id.lvColors);

        arrayList.add(new ModelClass("Red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        arrayList.add(new ModelClass("Green", "chokokki", R.drawable.color_green, R.raw.color_green));
        arrayList.add(new ModelClass("Brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        arrayList.add(new ModelClass("Gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        arrayList.add(new ModelClass("Black", "kululli", R.drawable.color_black, R.raw.color_black));
        arrayList.add(new ModelClass("White", "kelelli", R.drawable.color_white, R.raw.color_white));
        arrayList.add(new ModelClass("Dusty Yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        arrayList.add(new ModelClass("Mustard Yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        ModelClassAdapter adapter = new ModelClassAdapter(this, arrayList, R.color.background_colors);
        lvColors.setAdapter(adapter);

        lvColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelClass modelClass = arrayList.get(position);

                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(audioFocusListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //We have audio focus now
                    mediaPlayer = MediaPlayer.create(Colors.this, modelClass.getMusic());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mediaInterface);
                }
            }
        });
    }

    private void circularReveal(View view) {
        int teal_200 = view.getContext().getResources().getColor(R.color.teal_200);
        int white = view.getContext().getResources().getColor(R.color.white);

        boolean isVeggie = ((ColorDrawable) view.getBackground()) != null && ((ColorDrawable) view.getBackground()).getColor() == teal_200;
        int finalRadius = (int) Math.hypot(view.getWidth()/2, view.getHeight()/2);

        if (isVeggie) {
//            text1.setText(baconTitle);
//            text2.setText(baconText);
//            view.setBackgroundColor(white);
        } else {
            Animator anim = ViewAnimationUtils.createCircularReveal(view, (int) view.getWidth()/2, (int) view.getHeight()/2, 0, finalRadius);
            //Setting Text To the TextView
//            text1.setText(veggieTitle);
//            text2.setText(veggieText);
            //Setting the background of the textView

            //Setting the background of the view that is being clicked to GREEN
//            view.setBackgroundColor(green);
            anim.start();
        }
    }

}