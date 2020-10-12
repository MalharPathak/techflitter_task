package com.android.codingtest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Metadata extends Fragment {
View view;
    String DispUrl="https://gorest.co.in/public-api/users";
String user,page,current,limit_user;
TextView total_user,total_page,current_page,limit;
    public Metadata() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_metadata, container, false);
        total_user=view.findViewById(R.id.total_user);
        total_page=view.findViewById(R.id.total_pages);
        current_page=view.findViewById(R.id.current_page);
        limit=view.findViewById(R.id.user_limit);
        displayList();
        return view;
    }

    private void displayList() {
        final VolleyResponse dispApi = new VolleyResponse(getContext());
        dispApi.executeRequest(Request.Method.GET, DispUrl, new VolleyResponse.VolleyCallback() {
            @Override
            public void getResponse(String response) {
                try {
                    // Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    JSONObject resp= new JSONObject(response);
                    String code=resp.getString("code");
                    if(code.equals("200")) {
                    JSONObject meta=resp.getJSONObject("meta");
                    JSONObject pagination=meta.getJSONObject("pagination");
                    user=pagination.getString("total");
                    page=pagination.getString("pages");
                    current=pagination.getString("page");
                    limit_user=pagination.getString("limit");

                    total_user.setText("Total User:"+user);
                    total_page.setText("Total Pages: "+page);
                    current_page.setText("Current Page : "+current);
                    limit.setText("Page Limit: "+limit_user);

                    }else{
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}