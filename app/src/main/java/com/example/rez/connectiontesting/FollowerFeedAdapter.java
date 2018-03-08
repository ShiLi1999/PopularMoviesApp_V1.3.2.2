package com.example.rez.connectiontesting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jason on 2/22/2018.
 */

public class FollowerFeedAdapter extends ArrayAdapter<FollowerFeed> {

    public FollowerFeedAdapter(@NonNull Context context, @NonNull ArrayList<FollowerFeed> objects) {
        super(context, R.layout.message_act, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater myInflator = LayoutInflater.from(getContext());

        View customFeedView = myInflator.inflate(R.layout.message_act, parent,false);

        //get the widgets
        ImageView myImage = customFeedView.findViewById(R.id.picture);
        TextView myText = customFeedView.findViewById(R.id.texture);

        //get our values
        String pictureID = getItem(position).imageID;
        String wordID = getItem(position).message;

        //Set values
        myText.setText(wordID);
        new DownloadImageTask(myImage)
                .execute(pictureID);


        return  customFeedView;

    }
}

