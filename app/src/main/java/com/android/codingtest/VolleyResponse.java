package com.android.codingtest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyResponse {


    Context context;
    RequestQueue requestQueue;
    String jsonresponse;

    private Map<String, String> header, body;

    public VolleyResponse(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        header = new HashMap<>();
        body = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void addBody(String key, String value) {
        body.put(key, value);
    }

    public void executeRequest(int method, final String JsonURL, final VolleyCallback callback) {

        StringRequest stringRequest = new StringRequest(method, JsonURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonresponse = response;
                Log.e("RESPONSE", " res::" + jsonresponse);
                callback.getResponse(jsonresponse);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                return body;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return header;
            }
    };

        requestQueue.add(stringRequest);

    }

    public interface VolleyCallback
    {
        public void getResponse(String response);
    }
}