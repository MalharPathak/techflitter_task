package com.android.codingtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Users extends Fragment {
View view;
DisplayAdapter displayAdapter;
ArrayList<DisplayModel> list;
RecyclerView recyclerView;
DisplayModel displayModel;
RadioButton name,email,name1;
RadioGroup radioGroup;
String DispUrl="https://gorest.co.in/public-api/users";
    public Users() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_users, container, false);

        radioGroup=view.findViewById(R.id.radioGrp);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        name=view.findViewById(R.id.radioName);
        email=view.findViewById(R.id.radioEmail);
        name1=view.findViewById(R.id.radioName1);
        list = new ArrayList<>();
        displayList();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=view.findViewById(checkedId);

                String sortBy=radioButton.getText().toString();

                if(sortBy.equals("Z-A(Name)"))
                {
                    Collections.sort(list, new Comparator<DisplayModel>() {
                        @Override
                        public int compare(DisplayModel o1, DisplayModel o2) {
                            int a= o2.getName().compareTo(o1.getName());
                            displayAdapter.notifyDataSetChanged();
                            return a;

                        }
                    });
                }else if(sortBy.equals("A-Z(Name)"))
                {
                    Collections.sort(list, new Comparator<DisplayModel>() {
                        @Override
                        public int compare(DisplayModel o1, DisplayModel o2) {
                            int a= o1.getName().compareTo(o2.getName());
                            displayAdapter.notifyDataSetChanged();
                            return a;
                        }
                    });
                }else if(sortBy.equals("Email")){
                    Collections.sort(list, new Comparator<DisplayModel>() {
                        @Override
                        public int compare(DisplayModel o1, DisplayModel o2) {
                            int a= o1.getEmail().compareTo(o2.getEmail());
                            displayAdapter.notifyDataSetChanged();
                            return a;
                        }
                    });
                }
            }
        });

        return view;
    }
    private void displayList() {
        final VolleyResponse dispApi = new VolleyResponse(getContext());
//        dispApi.addHeader("Token","Bearer Token df770ba95557f63ed585a2d41d172e5a3e04053d1b6b32119f598c0fe47a14bc");

        dispApi.executeRequest(Request.Method.GET, DispUrl, new VolleyResponse.VolleyCallback() {
            @Override
            public void getResponse(String response) {
                try {
                   // Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    JSONObject resp= new JSONObject(response);
                    String code=resp.getString("code");
                    if(code.equals("200")) {

                        JSONArray arr = resp.getJSONArray("data");

                        for (int i = 0; i < arr.length(); i++) {
                            displayModel = new DisplayModel();
                        JSONObject listObj = arr.getJSONObject(i);
                            String id=listObj.getString("id");
                            String name=listObj.getString("name");
                            String email=listObj.getString("email");
                            String status=listObj.getString("status");
                            displayModel.setName(name);
                            displayModel.setEmail(email);
                            displayModel.setStatus(status);
                            displayModel.setId(id);
                            list.add(displayModel);
                        }
                        displayAdapter=new DisplayAdapter(getContext(),list);
                        recyclerView.setAdapter(displayAdapter);
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