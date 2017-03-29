package com.dpcraft.bookhub;

import android.content.Context;
import android.content.Intent;
//import android.support.design.widget.FloatingActionButton;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.dpcraft.bookhub.Activity.PictureActivity;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.NetModule.HttpUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends FragmentActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private Button searchButton;
    private FloatingActionButtonPlus mFabPlus;
    private View headerLayout;
    private Button startLoginButton;
    private TextView textViewNavUsername;
    private CircleImageView circleImageViewNavUserIcon;
    private MyApplication myApplication;
    private SwipeRefreshLayout requestSwipeRefreshLayout;
    private SimpleFragmentPagerAdapter pagerAdapter;


   // private FloatingActionButton floatingActionButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        //透明状态栏
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       //透明导航栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initWidget();

        myApplication = (MyApplication)getApplication();
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
                            //Toast.makeText(MainActivity.this,"Click btn 2 = "+ position,Toast.LENGTH_SHORT).show();
                            PictureActivity.actionStart(MainActivity.this,"data1","data2");
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
                 drawerLayout.closeDrawer(Gravity.LEFT);
                LoginActivity.actionStart(MainActivity.this, "MainActivity", "data2");


//                myApplication.setLoginStatus(true);
                Log.i(" main activity islogin",myApplication.isLogin().toString());




            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.navigation_item_message:
                        //消息点击处理
                        break;
                    case R.id.navigation_item_userInfo:
                        //个人信息
                        if(myApplication.isLogin()){
                            UserInfoActivity.actionStart(MainActivity.this, "data1", "data2");
                        }else{
                            LoginActivity.actionStart(MainActivity.this, "UserInfoActivity", "data2");
                        }
                        break;
                    case R.id.navigation_item_like:
                        //有意

                        break;
                    case R.id.navigation_item_history:
                        //历史
                        break;
                    case R.id.navigation_item_about:
                        //关于

                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });




        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //搜索的dialog实现

        final SearchFragment searchFragment = SearchFragment.newInstance();
               searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                      //这里处理逻辑
                Toast.makeText(MainActivity.this, "您搜索了"+ keyword, Toast.LENGTH_SHORT).show();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFragment.show(getSupportFragmentManager(),SearchFragment.TAG);

            }
        });

        //初始数据获取

      // initBookList();



    }


    @Override
    protected void onResume(){
        super.onResume();
        if(myApplication.isLogin()){
            Log.i(" resume  islogin",myApplication.isLogin().toString());
            textViewNavUsername.setText(myApplication.getLoginResponseUserInfo().getNickName());
            textViewNavUsername.setVisibility(View.VISIBLE);
            if(myApplication.getUserIcon() == null) {
               circleImageViewNavUserIcon.setImageResource(R.drawable.default_user_icon);
            }else
            {
                circleImageViewNavUserIcon.setImageURI(myApplication.getUserIcon());
            }
            circleImageViewNavUserIcon.setVisibility(View.VISIBLE);
            startLoginButton.setVisibility(View.GONE);

        }else {

            Log.i(" resume  islogin",myApplication.isLogin().toString());
            textViewNavUsername.setVisibility(View.GONE);
            circleImageViewNavUserIcon.setVisibility(View.GONE);
            startLoginButton.setVisibility(View.VISIBLE);

        }
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



    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }


}
