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
public class NumbersFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> numbers = new ArrayList<>();
        numbers.add(new Word("lutti","one",R.drawable.number_one,R.raw.number_one));
        numbers.add(new Word("otiiko","two",R.drawable.number_two,R.raw.number_two));
        numbers.add(new Word("tolookosu","three",R.drawable.number_three,R.raw.number_three));
        numbers.add(new Word("oyyisa","four",R.drawable.number_four,R.raw.number_four));
        numbers.add(new Word("massokka","five",R.drawable.number_five,R.raw.number_five));
        numbers.add(new Word("temmokka","six",R.drawable.number_six,R.raw.number_six));
        numbers.add(new Word("kenekaku","seven",R.drawable.number_seven,R.raw.number_seven));
        numbers.add(new Word("kawinta","eight",R.drawable.number_eight,R.raw.number_eight));
        numbers.add(new Word("wo'e","nine",R.drawable.number_nine,R.raw.number_nine));
        numbers.add(new Word("na'aacha","ten",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),numbers,R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word num=numbers.get(position);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer=MediaPlayer.create(getActivity(),num.getAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(listener);
                }
            }
        });

        return rootView;
    }

    private void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
            audioManager.abandonAudioFocus(mAudioFocusChange);
        }
    }

    private MediaPlayer.OnCompletionListener listener =new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

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

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
