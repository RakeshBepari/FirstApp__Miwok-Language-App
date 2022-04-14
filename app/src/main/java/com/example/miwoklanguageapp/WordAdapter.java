package com.example.miwoklanguageapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {


    private int mColorResourceId;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param words A List of Word objects to display in a list
     */
    public WordAdapter(Activity context, ArrayList<Word> words,int mColorResourceId){


        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0,words);

        this.mColorResourceId =mColorResourceId;

    }


    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView=convertView;
        /**
         * There are two layout files for the list item, one is list_item and another is list_item_video_code
         * Both layout files does the same thing in different ways one is my own way another is from the video
         * When implementing the video way Change the layout in the code inside this if statement to list_item_video_code
         * and also change the Linear layout code snippet to Relative layout if in xml it is so check the file and amend in the lines below
         */
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false );
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord=getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwokTextView
        TextView miwokTextView=(TextView)listItemView.findViewById(R.id.miwokTextView);
        // Get the miwok Translation form the current Word object and
        // set this text on the miwokTranslation TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID defaultTextView
         TextView defaultTextView=(TextView)listItemView.findViewById(R.id.defaultTextView);
        // Get the default translation from the Word object and
        // set this text on the default Translation TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the ID image
        ImageView imageView=(ImageView)listItemView.findViewById(R.id.image);
        // Get the image resource ID from the current Word object and
        // set the image to imageView
        if(currentWord.hasImage()) {
            //Set the imageView to the image resource specified in the current Word
            imageView.setImageResource(currentWord.getmImageResourceId());

            //Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            //Otherwise hide the imageView
            imageView.setVisibility(View.GONE);
        }

        //Set the theme of the app for the list item
        /**
         * if the list_item_video_code layout is used change the Linear layout to Relative Layout if it is so for that check the xml file
         */
        LinearLayout textContainer= (LinearLayout) listItemView.findViewById(R.id.textContainer);
        //Find the color that the resource id maps to
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        //Set the background color of the text Container View
        textContainer.setBackgroundColor(color);


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;


    }
}
