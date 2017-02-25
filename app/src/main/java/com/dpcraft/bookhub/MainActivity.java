package com.dpcraft.bookhub;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
//import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

public class MainActivity extends FragmentActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private Button searchButton;
    private FloatingActionButtonPlus mFabPlus;

   // private FloatingActionButton floatingActionButton;
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


        //FAbPlus 监听
        mFabPlus = (FloatingActionButtonPlus)findViewById(R.id.FabPlus) ;
        mFabPlus.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                    switch (position){
                    case 0:
                        Toast.makeText(MainActivity.this,"Click btn 0 = "+ position,Toast.LENGTH_SHORT).show();
                        break;
                        case 1:
                            Toast.makeText(MainActivity.this,"Click btn 1 = "+ position,Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(MainActivity.this,"Click btn 2 = "+ position,Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(MainActivity.this,"Click btn 3 = "+ position,Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;

                }
            }
        });

        //登录按钮监听
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.navigation_header);
        Button startLoginButton = (Button)headerLayout.findViewById(R.id.bt_start_login);
        startLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 LoginActivity.actionStart(MainActivity.this, "data1", "data2");

            }
        });





        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //搜索的dialog实现
        searchButton = (Button)findViewById(R.id.btn_search);
        final SearchFragment searchFragment = SearchFragment.newInstance();
               /* searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                      //这里处理逻辑
                Toast.makeText(MainActivity.this, keyword, Toast.LENGTH_SHORT).show();
            }
        });*/
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFragment.show(getSupportFragmentManager(),SearchFragment.TAG);

            }
        });



    }
    @Override
    protected void onStop(){
        super.onStop();
        drawerLayout.closeDrawer(Gravity.LEFT);

    }

    //改变menu字体颜色
    /*public static void setActivityMenuColor(final Activity activity){
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
    }*/

}
