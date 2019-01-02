package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private MediaPlayer.OnCompletionListener listener =new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.word_list,container,false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> family = new ArrayList<Word>();
        family.add(new Word("әpә","father",R.drawable.family_father,R.raw.family_father));
        family.add(new Word("әṭa","mother",R.drawable.family_mother,R.raw.family_mother));
        family.add(new Word("angsi","son",R.drawable.family_son,R.raw.family_son));
        family.add(new Word("tune","daughter",R.drawable.family_daughter,R.raw.family_daughter));
        family.add(new Word("taachi","elder brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        family.add(new Word("chalitti","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        family.add(new Word("teṭe","elder sister",R.drawable.family_older_sister,R.raw.family_older_sister));
        family.add(new Word("kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        family.add(new Word("ama","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        family.add(new Word("paapa","grand father",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),family,R.color.category_family);

        ListView listView = rootview.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word fam=family.get(position);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer=MediaPlayer.create(getActivity(),fam.getAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(listener);
                }
            }
        });
        return rootview;
    }
    //creating an instance of AudioManager.OnAudioFocusChangeListener and implementing callback method onAudioFocusChange()
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChange =new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange){
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        }
    };

    private void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
            audioManager.abandonAudioFocus(mAudioFocusChange);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
