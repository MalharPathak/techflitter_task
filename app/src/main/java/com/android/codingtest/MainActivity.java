package com.android.codingtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
private ViewPagerAdapter viewPagerAdapter;
private TabLayout tabLayout;
private ViewPager viewPager;
TextView create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Toolbar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


create=findViewById(R.id.create_user);
        //Tab
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        //ViewPager
        viewPagerAdapter.addFragment(new Users(),"Users");
        viewPagerAdapter.addFragment(new Metadata(),"Metadata");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //Set Tab Position with icon
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_user).setText("Users");
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_meta).setText("MetaData");

create.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(MainActivity.this,Create.class);
        startActivity(i);
    }
});
    }
}