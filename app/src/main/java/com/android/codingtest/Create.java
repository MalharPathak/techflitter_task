package com.android.codingtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Create extends AppCompatActivity {
    ImageView back;
    String createUrl="https://gorest.co.in/public-api/users";
    String name,email,gender,status;
    EditText et_name,et_email;
    RadioGroup radioGroup;
    RadioButton r_male,r_female;
    Spinner spinner;
    Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        back = findViewById(R.id.back);
        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        radioGroup=findViewById(R.id.radioGroup);
        r_male=findViewById(R.id.male);
        r_female=findViewById(R.id.female);
        create=findViewById(R.id.create);
        spinner=findViewById(R.id.spinner);

        //Set Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Create.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
//RadioButton
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=findViewById(checkedId);
                gender=radioButton.getText().toString();
            }
        });
        //Spinner
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String[] countries=getResources().getStringArray(R.array.status);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);
        spinner.setAdapter(adapter);

        //Button OnClick Listener
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser();
            }
        });


    }

    private void AddUser() {
        name=et_name.getText().toString();
        email=et_email.getText().toString().trim();
        status=spinner.getSelectedItem().toString();

        final VolleyResponse createApi = new VolleyResponse(Create.this);
        createApi.addHeader("Authorization","Bearer df770ba95557f63ed585a2d41d172e5a3e04053d1b6b32119f598c0fe47a14bc");
        createApi.addBody("name",name);
        createApi.addBody("email",email);
        createApi.addBody("gender",gender);
        createApi.addBody("status",status);

        createApi.executeRequest(Request.Method.POST, createUrl, new VolleyResponse.VolleyCallback() {
            @Override
            public void getResponse(String response) {
                try {
                     //Toast.makeText(Create.this, ""+response, Toast.LENGTH_SHORT).show();
                    JSONObject resp= new JSONObject(response);
                    String code=resp.getString("code");
                    if(code.equals("201")) {
                        Toast.makeText(Create.this, " User Created Successfully ", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Create.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}