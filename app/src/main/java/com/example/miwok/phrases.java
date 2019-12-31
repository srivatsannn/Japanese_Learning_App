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

public class phrases extends AppCompatActivity {
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
        words.add(new word("Where are you going?", "minto wuksus\n" +
                "\n",R.raw.phrase_where_are_you_going));
        words.add(new word("What is your name?\n" +
                "\n", "tinnә oyaase'nә\n" +
                "\n",R.raw.phrase_what_is_your_name));
        words.add(new word("My name is...\n" +
                "\n", "oyaaset",R.raw.phrase_my_name_is));
        words.add(new word("How are you feeling?\n" +
                "\n", "michәksәs?\n" +
                "\n",R.raw.phrase_how_are_you_feeling));
        words.add(new word("I’m feeling good.\n" +
                    "\n", "kuchi achit\n" +
                "\n",R.raw.phrase_im_feeling_good));
        words.add(new word("Are you coming?\n" +
                "\n", "әәnәs'aa?\n" +
                "\n",R.raw.phrase_are_you_coming));
        words.add(new word("Yes, I’m coming.\n" +
                "\n", "hәә’ әәnәm\n" +
                "\n",R.raw.phrase_yes_im_coming));
        words.add(new word("I’m coming.\n" +
                "\n", "әәnәm",R.raw.phrase_im_coming));
        words.add(new word("Let’s go.\n" +
                "\n", "yoowutis",R.raw.phrase_lets_go));
        words.add(new word("Come here.\n" +
                "\n", "әnni'nem",R.raw.phrase_come_here));


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);

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


                    mmediaPlayer = MediaPlayer.create(phrases.this, wordd.getMmedia());
                    mmediaPlayer.start();
                    mmediaPlayer.setOnCompletionListener(mcompletionListener);
                    Toast.makeText(phrases.this, "Clicked?>", Toast.LENGTH_SHORT).show();
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
