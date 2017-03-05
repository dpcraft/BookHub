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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends FragmentActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private Button searchButton;
    private FloatingActionButtonPlus mFabPlus;
    private View headerLayout;
    private Button startLoginButton;
    private TextView textViewNavUsername;
    private Boolean isLogin = false;
    private CircleImageView circleImageViewNavUserIcon;

    private SwipeRefreshLayout requestSwipeRefreshLayout;

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
        initWidget();


        //setActivityMenuColor(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        //FAbPlus 监听

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

        startLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 LoginActivity.actionStart(MainActivity.this, "data1", "data2");
                isLogin = true;
                Log.i("islogin",isLogin.toString());

            }
        });




        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //搜索的dialog实现

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
    protected void onResume(){
        super.onResume();
        if(isLogin){
            textViewNavUsername.setText("丫丫");
            textViewNavUsername.setVisibility(View.VISIBLE);
            circleImageViewNavUserIcon.setImageResource(R.drawable.yy);
            circleImageViewNavUserIcon.setVisibility(View.VISIBLE);
            startLoginButton.setVisibility(View.GONE);

        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        drawerLayout.closeDrawer(Gravity.LEFT);

    }
    public void initWidget(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFabPlus = (FloatingActionButtonPlus)findViewById(R.id.FabPlus) ;
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.navigation_header);
        startLoginButton = (Button)headerLayout.findViewById(R.id.bt_start_login);
        textViewNavUsername = (TextView)headerLayout.findViewById(R.id.tv_nav_username);
        circleImageViewNavUserIcon = (CircleImageView) headerLayout.findViewById(R.id.civ_nav_usericon);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        searchButton = (Button)findViewById(R.id.btn_search);

    }



}
