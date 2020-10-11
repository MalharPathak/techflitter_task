package com.android.codingtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.DisplayViewHolder>{

    private Context context;
    String id,concat;
    private String delUrl="https://gorest.co.in/public-api/users/";
    private ArrayList<DisplayModel> list;

    public DisplayAdapter(Context context, ArrayList<DisplayModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.display_model,parent,false);
        return new DisplayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayViewHolder holder, final int position) {
        final DisplayModel currentItem=list.get(position);
        String name=currentItem.getName();
        String email=currentItem.getEmail();
        String status=currentItem.getStatus();

        holder.mName.setText(name);
        holder.mEmail.setText(email);
        holder.mStatus.setText(status);
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=currentItem.getId();
                concat=delUrl+id;
                DeleteApi();
                list.remove(position);
                DisplayAdapter.this.notifyDataSetChanged();

            }
        });
    }

    private void DeleteApi() {
        final VolleyResponse delApi = new VolleyResponse(context);
        delApi.addHeader("Authorization","Bearer df770ba95557f63ed585a2d41d172e5a3e04053d1b6b32119f598c0fe47a14bc");

        delApi.executeRequest(Request.Method.DELETE, concat, new VolleyResponse.VolleyCallback() {
            @Override
            public void getResponse(String response) {
                try {
                    // Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    JSONObject resp= new JSONObject(response);
                    String code=resp.getString("code");
                    if(code.equals("204"))
                    {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context, "Error Delete", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DisplayViewHolder extends RecyclerView.ViewHolder{
        private TextView mName,mEmail,mStatus;
        private ImageView mDelete;
        public DisplayViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.txt_name);
            mEmail=itemView.findViewById(R.id.txt_email);
            mStatus=itemView.findViewById(R.id.txt_status);
            mDelete=itemView.findViewById(R.id.delete);
        }
    }


}
