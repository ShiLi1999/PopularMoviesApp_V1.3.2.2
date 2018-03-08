package com.example.rez.connectiontesting;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Rez on 2017-09-02.
 */

public class JsonRetriever {

    public static void RetrieveFromURL(final CallBackMe whoToCall, String url)
    {
        RetrieveFromURL((Context)whoToCall, url, whoToCall);
    }

    public static void RetrieveFromURL(Context context, String url,final CallBackMe whoToCall)
    {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String ss) {
                whoToCall.CallThis(ss);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Throw some error here.

            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }
}
