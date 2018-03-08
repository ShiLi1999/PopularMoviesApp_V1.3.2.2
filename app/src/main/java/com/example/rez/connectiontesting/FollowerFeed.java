package com.example.rez.connectiontesting;

/**
 * Created by Jason on 2/22/2018.
 */

public class FollowerFeed {

    public String message;
    public String imageID;
    public String URL;

    public FollowerFeed(String message, String imageID, String URL)
    {
        this.message = message;
        this.imageID = imageID;
        this.URL = URL;
    }
}
