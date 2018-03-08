package com.example.rez.connectiontesting;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Rez on 2017-09-02.
 * This is a sample for my students to retrieve Json Files from URL
 * Using the Github Api Json
 * Example: https://api.github.com/users/MadReza
 */
public class MainActivity extends AppCompatActivity implements CallBackMe {


    ImageView avatar = null;
    ImageView avatar1 = null;
    ImageView avatar2= null;
    ImageView avatar3 = null;
    EditText page = null;

    public String urlbase = "https://image.tmdb.org/t/p/w500/";
    public String youtube = "https://www.youtube.com/results?search_query=";
    public String totalPages = null;

    ArrayList<String> posterimg = new ArrayList<>();
    ArrayList<String> titlename = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        avatar = (ImageView) findViewById(R.id.avatar);
        avatar1 = (ImageView) findViewById(R.id.avatar1);
        avatar2 = (ImageView) findViewById(R.id.avatar2);
        avatar3 = (ImageView) findViewById(R.id.avatar3);
        page = (EditText) findViewById(R.id.pageSearch);


       String url = "https://api.themoviedb.org/3/movie/popular?api_key=82df97fce0401314f8bee77448c39cd5";


        //This will retrieve the string json from the URL requested
        JsonRetriever.RetrieveFromURL(this, url, this); //First Param for Context, Last Param for Callback Function
                                    //First param is required for the library
                                    //Third param, allows to use any class that implements CallBackMe
      //  JsonRetriever.RetrieveFromURL(this, url2, this);
    }

    public void FollowerClick (View V)
    {
        Intent myIntent = new Intent(this,Followers.class);
        startActivity(myIntent);
    }

    public void MainONclick (View V)
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(youtube+titlename.get(0)));
        startActivity(intent);

    }

    public void MainONclickSEC (View V)
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(youtube+titlename.get(1)));
        startActivity(intent);

    }

    public void MainONclickTHR (View V)
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(youtube+titlename.get(2)));
        startActivity(intent);

    }

    public void MainONclickFOU (View V)
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(youtube+titlename.get(3)));
        startActivity(intent);

    }

    public void SearchOnclick (View V)
    {
        try
        {
            Intent myIntent = new Intent(this,Followers.class);
            if (Double.parseDouble(page.getText().toString())<2)
            {
                myIntent.putExtra("pageNumber","");
                startActivity(myIntent);
            }

            else if (Double.parseDouble(page.getText().toString())>=2 && Double.parseDouble(page.getText().toString())<= Integer.parseInt(totalPages))
            {
                double d = Double.parseDouble(page.getText().toString());
                int i = (int)d;
                myIntent.putExtra("pageNumber",Integer.toString(i));
                startActivity(myIntent);
            }

            else if(Double.parseDouble(page.getText().toString())> Integer.parseInt(totalPages))
            {
                myIntent.putExtra("pageNumber",totalPages);
                startActivity(myIntent);
            }
        }

        catch (Exception e)
        {
            Intent myIntent = new Intent(this,Followers.class);
            myIntent.putExtra("pageNumber","");
            startActivity(myIntent);
        }

    }

    @Override
    public void CallThis(String jsonText) {

        //Parse the Json here
        //Good examples: https://stackoverflow.com/questions/8091051/how-to-parse-json-string-in-android

        try {
            JSONObject json = new JSONObject(jsonText);

            totalPages = json.getString("total_pages");

            JSONArray novajson = json.getJSONArray("results");

            for (int i = 0 ; i< novajson.length(); i++)

            {
                    JSONObject jsonObject = novajson.getJSONObject(i);

                    posterimg.add(jsonObject.getString("poster_path"));
                    titlename.add(jsonObject.getString("title"));


            }

            new DownloadImageTask(avatar)
                    .execute(urlbase+posterimg.get(0));

            new DownloadImageTask(avatar1)
                    .execute(urlbase+posterimg.get(1));

            new DownloadImageTask(avatar2)
                    .execute(urlbase+posterimg.get(2));

            new DownloadImageTask(avatar3)
                    .execute(urlbase+posterimg.get(3));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}


