package com.example.rez.connectiontesting;

import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity implements CallBackMe{


    public ArrayList<String> movieTitle = new ArrayList<>();
    public ArrayList<String> moviePop = new ArrayList<>();
    public ArrayList<String> movieView = new ArrayList<>();
    public ArrayList<String> movieDate = new ArrayList<>();
    public ArrayList<String> moviePost = new ArrayList<>();
    public ArrayList<Integer> dirctorsid = new ArrayList<>();


    public int receiveSearch;
    public int searchid;
    public int receivedNew;


    TextView title = null;
    TextView pop = null;
    TextView overview = null;
    TextView release = null;
    ImageView poster = null;
    TextView seeCreadits = null;

    public String urlbase = "https://image.tmdb.org/t/p/w500/";

    public String pagenumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        title = (TextView) findViewById(R.id.Movietitle);
        pop = (TextView) findViewById(R.id.Moviepop);
        overview = (TextView) findViewById(R.id.MovieOverview);
        release = (TextView) findViewById(R.id.Moviedate);
        poster = (ImageView) findViewById(R.id.MoviePoster);
        seeCreadits = (TextView) findViewById(R.id.MovieCharactor);




        Intent receivedpackage = getIntent();

        receiveSearch =  receivedpackage.getIntExtra("firstsearch",-1);
        receivedNew = receivedpackage.getIntExtra("url",-1);



        pagenumber = receivedpackage.getStringExtra("page");
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=82df97fce0401314f8bee77448c39cd5&page="+pagenumber;


        //This will retrieve the string json from the URL requested
        JsonRetriever.RetrieveFromURL(this, url, this); //First Param for Context, Last Param for Callback Function
        //First param is required for the library
        //Third param, allows to use any class that implements CallBackMe
    }

    public void directorclick (View V)
    {
        Intent myIntent = new Intent(this,Director.class);
        myIntent.putExtra("Fordirector",searchid);
        startActivity(myIntent);
    }




    @Override
    public void CallThis(String jsonText) {

        //Parse the Json here
        //Good examples: https://stackoverflow.com/questions/8091051/how-to-parse-json-string-in-android

        try {
            JSONObject json = new JSONObject(jsonText);

            JSONArray novajson = json.getJSONArray("results");


            for (int i = 0 ; i< novajson.length(); i++) {
                JSONObject jsonObject = novajson.getJSONObject(i);


                movieTitle.add(jsonObject.getString("title").toLowerCase());
                moviePop.add(jsonObject.getString("popularity"));
                movieView.add(jsonObject.getString("overview"));
                movieDate.add(jsonObject.getString("release_date"));
                moviePost.add(jsonObject.getString("poster_path"));
                dirctorsid.add(jsonObject.getInt("id"));

                for (int j=0; j<movieTitle.size(); j++)

                {
                    if (receiveSearch == j ||receivedNew ==j)
                    {
                        title.setText(movieTitle.get(j));
                        pop.setText("Popularity: "+moviePop.get(j));
                        overview.setText("Overview: "+movieView.get(j));
                        release.setText("Release Date: "+movieDate.get(j));
                        searchid = dirctorsid.get(j);
                        seeCreadits.setText("View Creadits");

                        new DownloadImageTask(poster)
                                .execute(urlbase+moviePost.get(j));
                    }
                }

            }

        }

        catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
