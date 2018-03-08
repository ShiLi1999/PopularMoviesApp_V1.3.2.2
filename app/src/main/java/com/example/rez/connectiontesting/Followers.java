package com.example.rez.connectiontesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Followers extends AppCompatActivity implements CallBackMe{


    EditText answer;
    TextView bookmark;
    public ArrayList<String> movieList = new ArrayList<>();
    public ArrayList<String> URLlist = new ArrayList<>();



    int firstsearch = -1;


    public String urlbase = "https://image.tmdb.org/t/p/w500/";

    public String pagenumber = null;
    public String totalPages = null;

    public double mark;
    public int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);



        answer = (EditText)findViewById(R.id.search);
        bookmark = (TextView)findViewById(R.id.bookmark);




        Intent receivedpackage = getIntent();
        pagenumber = receivedpackage.getStringExtra("pageNumber");




        String url = "https://api.themoviedb.org/3/movie/popular?api_key=82df97fce0401314f8bee77448c39cd5&page="+pagenumber;


        //This will retrieve the string json from the URL requested
        JsonRetriever.RetrieveFromURL(this, url, this); //First Param for Context, Last Param for Callback Function
        //First param is required for the library
        //Third param, allows to use any class that implements CallBackMe



    }

    public void SearchPage (View V)
    {

     //   Intent receivedpackage = getIntent();
        Intent myIntent = new Intent(this,SearchResult.class);

       // Intent yourIntent = new Intent(this,NotFound.class);

        for (int j = 0; j< movieList.size(); j++)
        {

            if (movieList.contains(answer.getText().toString().toLowerCase()))
            {
                firstsearch = movieList.indexOf(answer.getText().toString().toLowerCase());

                myIntent.putExtra("firstsearch",firstsearch);
                myIntent.putExtra("page",pagenumber);
                //Toast.makeText(this, "FOUND", Toast.LENGTH_SHORT).show();
            }

        }
        startActivity(myIntent);
        firstsearch = -1;
    }


    @Override
    public void CallThis(String jsonText) {

        //Parse the Json here
        //Good examples: https://stackoverflow.com/questions/8091051/how-to-parse-json-string-in-android

        try {
            JSONObject json = new JSONObject(jsonText);
            totalPages = json.getString("total_pages");

            JSONArray novajson = json.getJSONArray("results");
            ArrayList<FollowerFeed> myfeed = new ArrayList<>();

            try
            {
                mark = Double.parseDouble(pagenumber);
                i = (int)mark;
            }
            catch (Exception e)
            {
                mark = 1;
                i = (int)mark;
            }

            bookmark.setText("Showing result page: "+i);


            for (int i = 0 ; i< novajson.length(); i++) {
                JSONObject jsonObject = novajson.getJSONObject(i);

                URLlist.add(jsonObject.getString("id"));
                movieList.add(jsonObject.getString("title").toLowerCase());

                FollowerFeed f = new FollowerFeed(jsonObject.getString("title"), urlbase + jsonObject.getString("poster_path"), jsonObject.getString("id"));
                myfeed.add(f);


                ListView lv = (ListView) findViewById(R.id.moviestuff);

                FollowerFeedAdapter myadapter = new FollowerFeedAdapter(this, myfeed);
                lv.setAdapter(myadapter);

                lv.setOnItemClickListener(
                        new AdapterView.OnItemClickListener(){

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                FollowerFeed myFeed= (FollowerFeed) parent.getItemAtPosition(position);
                                //INTENT
                                ClickSearch(myFeed);
                            }
                        }
                );
            }


        }

        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void ClickSearch(FollowerFeed gotClicked)
    {
        Intent myIntent= new Intent(this,SearchResult.class);
        myIntent.putExtra("page",pagenumber);
        for (int i=0 ; i< URLlist.size();i++)
        {
            if (URLlist.contains(gotClicked.URL))
            {
                myIntent.putExtra("url", URLlist.indexOf(gotClicked.URL));
            }
        }

        startActivity(myIntent);
    }

    public void Nextpage (View V)
    {
        try
        {
            Intent myIntent= new Intent(this,Followers.class);
            Intent backIntent = new Intent(this,MainActivity.class);
            double d = Double.parseDouble(pagenumber);
            double d2= d+1;
            double lastpage = Double.parseDouble(totalPages);

            if (d2 > lastpage)
            {
                startActivity(backIntent);

            }
            else
            {
                String nextNumber = Double.toString(d2);
                myIntent.putExtra("pageNumber",nextNumber);
                startActivity(myIntent);
            }

        }

        catch (Exception e)
        {
            Intent myIntent= new Intent(this,Followers.class);
            myIntent.putExtra("pageNumber","2");
            startActivity(myIntent);
        }

    }

    public void BacktoMenu (View V)
    {
        Intent backIntent = new Intent(this,MainActivity.class);
        startActivity(backIntent);
    }



}
