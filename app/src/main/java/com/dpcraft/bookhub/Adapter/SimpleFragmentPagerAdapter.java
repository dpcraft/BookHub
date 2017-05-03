package com.dpcraft.bookhub.Adapter;

/**
 * Created by DPC on 2017/2/9.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.dpcraft.bookhub.Fragment.RequestFragment;
import com.dpcraft.bookhub.Fragment.SellFragment;

import java.util.Objects;


public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

   // final int PAGE_COUNT = 3;
   // private String tabTitles[] = new String[]{"买书租书","求书","读书笔记"};
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"买书租书","求书"};
    private Context context;



    private int currentPosition;

    public SimpleFragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return SellFragment.newInstance();
        }
        else {
            return RequestFragment.newInstance();
        }


    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object){
        setCurrentPosition(position);
        super.setPrimaryItem(container, position, object);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

}
