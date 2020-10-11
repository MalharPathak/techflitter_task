package com.android.codingtest;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> lstFragment=new ArrayList<>();
    private List<String> lstTitle=new ArrayList<>();

    //Constructor
    public ViewPagerAdapter(FragmentManager fm){
        super(fm);

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstTitle.size();
    }

    //Override Method
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    //Add Fragment
    public void addFragment(Fragment fragment,String title){
        lstFragment.add(fragment);
        lstTitle.add(title);
    }
}
