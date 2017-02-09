package com.dpcraft.bookhub;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setActivityMenuColor(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        /*
        tabLayout.addTab(tabLayout.newTab().setText("买书租书"));
        tabLayout.addTab(tabLayout.newTab().setText("求书"));
        tabLayout.addTab(tabLayout.newTab().setText("读书心得"));
        */
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



    }
    //改变menu字体颜色
    public static void setActivityMenuColor(final Activity activity){
        activity.getLayoutInflater().setFactory(new android.view.LayoutInflater.Factory(){
            public View onCreateView(String name, Context context, AttributeSet attrs){
                if(name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
                        ||name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")){
                    try{
                        LayoutInflater f = activity.getLayoutInflater();
                        final View view = f.createView(name,null,attrs);
                        if(view instanceof TextView){
                            new Handler().post(new Runnable() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void run() {
                                    ((TextView)view).setTextColor(activity.getResources().getColor(R.color.blue_500));
                                }
                            });
                        }
                        return view;
                    }catch (InflateException e){
                        e.printStackTrace();
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
    }

}
