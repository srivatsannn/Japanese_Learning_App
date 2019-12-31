package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class familymembers extends AppCompatActivity {
    private MediaPlayer mmediaPlayer;
    private AudioManager maudioManager;
    AudioManager.OnAudioFocusChangeListener mlistener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mmediaPlayer.pause();
                mmediaPlayer.seekTo(0);
            } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                mmediaPlayer.start();
            } else if (i ==AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();

            }
        }
    };
    private MediaPlayer.OnCompletionListener mcompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        maudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words = new ArrayList<word>();
        words.add(new word("father", "әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new word("mother", "әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new word("son", "angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new word("daughter", "tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new word("olderbrother", "taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new word("youngerbrother", "chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new word("oldersister", "teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new word("youngersister", "kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new word("grandmother", "ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new word("grandfather", "paapa",R.drawable.family_grandfather,R.raw.family_grandfather));



        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words,R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word wordd=words.get(i);
                releaseMediaPlayer();
                int result=maudioManager.requestAudioFocus(mlistener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mmediaPlayer = MediaPlayer.create(familymembers.this, wordd.getMmedia());
                    mmediaPlayer.start();
                    mmediaPlayer.setOnCompletionListener(mcompletionListener);
                    Toast.makeText(familymembers.this, "Clicked?>", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mmediaPlayer != null) {
            // Regardless of therrent state of the media player, release its resources
            // because we no longer need it.
            mmediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mmediaPlayer = null;
            maudioManager.abandonAudioFocus(mlistener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
}


