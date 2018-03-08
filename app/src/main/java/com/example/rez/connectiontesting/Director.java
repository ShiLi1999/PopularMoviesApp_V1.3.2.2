package com.example.rez.connectiontesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Director extends AppCompatActivity implements CallBackMe{

    TextView firstone;
    TextView secondone;
    TextView directorN;
    ArrayList<String>MoiveCharacters = new ArrayList<>();
    ArrayList<String>MovieActorsName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director);

        firstone = (TextView)findViewById(R.id.charactor);
        secondone = (TextView)findViewById(R.id.actorName);
        directorN = (TextView)findViewById(R.id.directorName);

        Intent receivedpackage = getIntent();
        int result =  receivedpackage.getIntExtra("Fordirector",0);

       // firstone.setText(""+result);

        String url = "https://api.themoviedb.org/3/movie/"+result+"/credits?api_key=82df97fce0401314f8bee77448c39cd5";
        //This will retrieve the string json from the URL requested
        JsonRetriever.RetrieveFromURL(this, url, this); //First Param for Context, Last Param for Callback Function
        //First param is required for the library
        //Third param, allows to use any class that implements CallBackMe
    }


    @Override
    public void CallThis(String jsonText) {

        //Parse the Json here
        //Good examples: https://stackoverflow.com/questions/8091051/how-to-parse-json-string-in-android

        try {
            JSONObject json = new JSONObject(jsonText);
            JSONObject json2 = new JSONObject(jsonText);

            JSONArray novajson = json.getJSONArray("cast");
            JSONArray newjson = json2.getJSONArray("crew");


            for (int i = 0 ; i< novajson.length(); i++) {
                JSONObject jsonObject = novajson.getJSONObject(i);


                MoiveCharacters.add(jsonObject.getString("character"));
                MovieActorsName.add(jsonObject.getString("name"));

                firstone.append(MoiveCharacters.get(i)+"\n"+"\n");
                secondone.append(MovieActorsName.get(i)+"\n"+"\n");

            }

            for (int j = 0 ;j< newjson.length(); j++)
            {
                JSONObject json2Object = newjson.getJSONObject(j);

                if (json2Object.getString("department").equals("Directing"))
                {
                    directorN.append(json2Object.getString("name"));

                }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
