package com.sharma.aditya.udacityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Numbers extends AppCompatActivity {

    private final ArrayList<ModelClass> numbers = new ArrayList<ModelClass>();
    private static final String TAG = "Numbers";
    private ListView lvNumber;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    MediaPlayer.OnCompletionListener mediaInterface = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            /**
             * Releasing the mediaPlayer resources
             */
            releaseMediaPlayer();
        }
    };

    AudioManager.OnAudioFocusChangeListener listener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange ==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
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
            audioManager.abandonAudioFocus(listener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(this.getResources()
                                        // Image to be rounded -> ,new Bitmap());
        //roundedBitmapDrawable.setCircular(true);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        lvNumber = findViewById(R.id.lvNumbers);

        numbers.add(new ModelClass("0","noll",R.drawable.color_black,R.raw.number_one));
        numbers.add(new ModelClass("One","ett",R.drawable.number_one,R.raw.number_one));
        numbers.add(new ModelClass("Two","tva",R.drawable.number_two,R.raw.number_two));
        numbers.add(new ModelClass("Three","tre",R.drawable.number_three,R.raw.number_three));
        numbers.add(new ModelClass("Four","fyra",R.drawable.number_four,R.raw.number_four));
        numbers.add(new ModelClass("Five","fem",R.drawable.number_five,R.raw.number_five));
        numbers.add(new ModelClass("Six","sex",R.drawable.number_six,R.raw.number_six));
        numbers.add(new ModelClass("Seven","sju",R.drawable.number_seven,R.raw.number_seven));
        numbers.add(new ModelClass("Eight","atta",R.drawable.number_eight,R.raw.number_eight));
        numbers.add(new ModelClass("Nine","nio",R.drawable.number_nine,R.raw.number_nine));
        numbers.add(new ModelClass("Ten","tio",R.drawable.number_ten,R.raw.number_ten));

        ModelClassAdapter adapter = new ModelClassAdapter(this,numbers,R.color.background_number);
        lvNumber.setAdapter(adapter);

        lvNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelClass modelClass = numbers.get(position);
                Log.i(TAG, "onCreate: "+modelClass.toString());

                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(listener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //We Have audio focus

                    mediaPlayer = MediaPlayer.create(Numbers.this, modelClass.getMusic());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mediaInterface);
                }

            }
        });


    }
}