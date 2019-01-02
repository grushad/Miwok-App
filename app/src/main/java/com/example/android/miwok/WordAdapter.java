package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorId;

    public WordAdapter(Activity context, ArrayList<Word> wordArrayList,int bgColor){
        super(context,0,wordArrayList);
        colorId=bgColor;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //get the item at current position in the listview
        Word currentWord = getItem(position);

        //find the textview with id miwok_word and assign it to nameTextView
        TextView nameTextView = listItemView.findViewById(R.id.miwok_word);
        //get the miwok translation of the word using getMiwok method and set it as the text of the textview
        nameTextView.setText(currentWord.getMiwok());

        TextView numberTextView =  listItemView.findViewById(R.id.english_word);
        numberTextView.setText(currentWord.getEnglish());

        ImageView imageView= listItemView.findViewById(R.id.image);

        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getResource());
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(),colorId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        return listItemView;
    }


}
