package com.dpcraft.bookhub.Adapter;

/**
 * Created by DPC on 2017/2/9.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dpcraft.bookhub.Fragment.RequestFragment;
import com.dpcraft.bookhub.Fragment.SellFragment;


public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

   // final int PAGE_COUNT = 3;
   // private String tabTitles[] = new String[]{"买书租书","求书","读书笔记"};
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"买书租书","求书"};
    private Context context;

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
        /*else if(position == 1) {
            return RequestFragment.newInstance();
        }
        else{
            return CommentFragment.newInstance();
                //return PageFragment.newInstance(position + 1);
        }*/

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
