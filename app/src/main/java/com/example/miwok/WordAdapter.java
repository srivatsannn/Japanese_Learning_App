package com.example.miwok;

import android.app.Activity;
import android.app.LauncherActivity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<word> {
    private int mcolor;
    public WordAdapter(Activity context, ArrayList<word> words, int color){

        super(context,0,words);
        mcolor=color;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        word currentword = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.miwokt);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentword.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.normalt);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentword.getDefaultTranslation());

            ImageView icon = (ImageView) listItemView.findViewById(R.id.imaget);
        if(currentword.hasimage()){
            icon.setImageResource(currentword.getMimage());
            icon.setVisibility(View.VISIBLE);
        }
          else {
              icon.setVisibility(View.GONE);
          }
          View container= listItemView.findViewById(R.id.text_container);
          int color= ContextCompat.getColor(getContext(),mcolor);
          container.setBackgroundColor(color);


        return listItemView;
    }
}