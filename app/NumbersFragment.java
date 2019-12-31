package com.example.miwok;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NumbersFragment extends Fragment {
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

    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */

        return rootView;
        maudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words = new ArrayList<word>();
        words.add(new word("one", "ichi", R.drawable.number_one, R.raw.number_one));
        words.add(new word("two", "ni", R.drawable.number_two, R.raw.number_two));
        words.add(new word("three", "san", R.drawable.number_three, R.raw.number_three));
        words.add(new word("four", "yon", R.drawable.number_four, R.raw.number_four));
        words.add(new word("five", "go", R.drawable.number_five, R.raw.number_five));
        words.add(new word("six", "ryoku", R.drawable.number_six, R.raw.number_six));
        words.add(new word("seven", "saatchi", R.drawable.number_seven, R.raw.family_older_brother));
        words.add(new word("eight", "hatchi", R.drawable.number_eight, R.raw.number_eight));
        words.add(new word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

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
                word wordd = words.get(i);
                releaseMediaPlayer();
                int result=maudioManager.requestAudioFocus(mlistener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mmediaPlayer = MediaPlayer.create(numbers.this, wordd.getMmedia());
                    mmediaPlayer.start();
                    mmediaPlayer.setOnCompletionListener(mcompletionListener);
                    Toast.makeText(numbers.this, "Audio playing.....", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
    }
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
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
}