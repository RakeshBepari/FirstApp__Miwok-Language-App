package com.example.miwoklanguageapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FamilyMembersFragment extends Fragment {

    MediaPlayer mMediaPlayer;

    AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if( focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                Log.d("onAudioFocusChange", "onAudioFocusChange: AUDIOFOCUS_LOSS_TRANSIENT ");
//                    if (mMediaPlayer != null)
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange== AudioManager.AUDIOFOCUS_GAIN) {
                Log.d("onAudioFocusChange", "onAudioFocusChange: AUDIOFOCUS_GAIN ");
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
//                    if (mMediaPlayer != null)
                mMediaPlayer.start();
            }
            else if (focusChange==AudioManager.AUDIOFOCUS_LOSS) {
                Log.d("onAudioFocusChange", "onAudioFocusChange: AUDIOFOCUS_LOSS ");
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * The listener gets triggered when the {@link MediaPlayer} has completed playing the audio file
     */
    MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter itemsAdapter=new WordAdapter(getActivity(),words,R.color.family_color);

        ListView listView= (ListView) rootView.findViewById(R.id.list);


        listView.setAdapter(itemsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.v("NumbersActivity", "Current word: " + words.get(position));

                // release the media player if it currently exists because we about to play a
                // different sound file. It can happen when the user clicks on multiple view
                // buttons rapidly
                releaseMediaPlayer();

                //request audio for playback
                int request= mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use Music Stream
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(request==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // we got the audio focus

                    //get the current word form the words arraylist by the help of get position and
                    // then with that word object get audio resource id
                    // Create and setup the {@link MediaPlayer} for the audio resource associated with the current word
                    mMediaPlayer = MediaPlayer.create(getActivity(), words.get(position).getmAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start(); // No need call prepare(); create() method does that for you
                    Log.d("Media Player Started", "onItemClick: PlaybackStarted");

                    //setup a listener on the media player, so that we can stop and release the
                    //media player once the sounds have finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });

        return rootView;
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            Log.d("AUDIOFOCUSGONE", "releaseMediaPlayer: Audiofocusgone");
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        //When the activity is stopped, stop the media player resources as we won't be
        //playing any more sounds.
        releaseMediaPlayer();
    }

}