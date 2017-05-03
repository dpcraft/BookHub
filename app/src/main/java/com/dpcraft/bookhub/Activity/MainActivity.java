package com.dpcraft.bookhub.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.design.widget.FloatingActionButton;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dpcraft.bookhub.Adapter.SimpleFragmentPagerAdapter;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.NetModule.NetState;
import com.dpcraft.bookhub.NetModule.NetWorkStateReceiver;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.ScanModule.CaptureActivity;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;


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
    private SimpleFragmentPagerAdapter pagerAdapter;
    private static final int REQUEST_QR_CODE = 1;
    private NetWorkStateReceiver mNetWorkStateReceiver;

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
                        if(myApplication.isLogin()){
                            RequestEditorActivity.actionStart(MainActivity.this, "", "");
                        }else {
                            LoginActivity.actionStart(MainActivity.this, "RequestEditorActivity", "data2");
                        }
                        break;
                        case 1:
                            if(myApplication.isLogin()){
                                final Intent i = new Intent(MainActivity.this, CaptureActivity.class);
                                startActivityForResult(i, REQUEST_QR_CODE);
                            }else{
                                LoginActivity.actionStart(MainActivity.this, "MainActivity", "data2");
                            }
//                            final Intent i = new Intent(MainActivity.this, CaptureActivity.class);
//                            startActivityForResult(i, REQUEST_QR_CODE);
                            break;
                        case 2:
                            //Toast.makeText(MainActivity.this,"Click btn 2 = "+ position,Toast.LENGTH_SHORT).show();
                            if(myApplication.isLogin()){
                                UploadActivity.actionStart(MainActivity.this,"data1","data2");
                            }else{
                                LoginActivity.actionStart(MainActivity.this, "UploadActivity", "data2");
                            }
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
                        MyRequestActivity.actionStart(MainActivity.this, "", "");
                        break;
                    case R.id.navigation_item_userInfo:
                        //个人信息
                        if(myApplication.isLogin()){
                            UserInfoActivity.actionStart(MainActivity.this, "data1", "data2");
                        }else{
                            LoginActivity.actionStart(MainActivity.this, "UserInfoActivity", "data2");
                        }
                        break;
                    case R.id.navigation_item_upload:
                        //发布
                        if(myApplication.isLogin()){
                            MyUploadActivity.actionStart(MainActivity.this, "data1", "data2");
                        }else{
                            LoginActivity.actionStart(MainActivity.this, "MyUploadActivity", "data2");
                        }
                        break;
                    case R.id.navigation_item_like:
                        //有意
                        if(myApplication.isLogin()){
                            MyIntentionActivity.actionStart(MainActivity.this, "data1", "data2");
                        }else{
                            LoginActivity.actionStart(MainActivity.this, "MyIntentionActivity", "data2");
                        }
                        break;

                    case R.id.navigation_item_about:
                        //关于
                        AboutActivity.actionStart(MainActivity.this, "data1", "data2");

                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });




        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //搜索的实现

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pagerAdapter.getCurrentPosition() == 0){
                    SearchActivity.actionStart(MainActivity.this,"","");
                }else{
                    SearchActivity.actionStart(MainActivity.this,"","true");
                }


            }
        });

        //初始数据获取

      // initBookList();



    }


    @Override
    protected void onResume(){
        super.onResume();
        NetState.isConnection(MainActivity.this);
        if(mNetWorkStateReceiver == null){
            mNetWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkStateReceiver, intentFilter);


        Log.i("MAIN网络连接检测", "注册事件");
        if(myApplication.isLogin()){
            Log.i(" resume  islogin",myApplication.isLogin().toString());
            textViewNavUsername.setText(myApplication.getLoginResponseUserInfo().getNickName());
            textViewNavUsername.setVisibility(View.VISIBLE);
            Glide.with(this).load(Server.getServerAddress() + "user/photo?token=" + myApplication.getToken())
                   .error(R.drawable.default_user_icon).into(circleImageViewNavUserIcon);
            Log.i("IconUrl=======",Server.getServerAddress() + "user/photo?token=" +myApplication.getToken());
//            if(myApplication.getUserIcon() == null) {
//               circleImageViewNavUserIcon.setImageResource(R.drawable.default_user_icon);
//            }else
//            {
//                circleImageViewNavUserIcon.setImageURI(myApplication.getUserIcon());
//            }
            circleImageViewNavUserIcon.setVisibility(View.VISIBLE);
            startLoginButton.setVisibility(View.GONE);

        }else {

            Log.i(" resume  islogin",myApplication.isLogin().toString());
            textViewNavUsername.setVisibility(View.GONE);
            circleImageViewNavUserIcon.setVisibility(View.GONE);
            startLoginButton.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onPause(){
        unregisterReceiver(mNetWorkStateReceiver);
        Log.i("MAIN网络连接检测", "注销事件");
        super.onPause();
    }

    @Override
    public void onBackPressed(){
        showExitDialog();

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            String result = data.getStringExtra("result");
            UploadActivity.actionStart(MainActivity.this, result, "");
        }
        else{
            UploadActivity.actionStart(MainActivity.this,"", "");
        }
    }


    public  void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("客官留步，还有很多书等您收留呢");
        builder.setNegativeButton("残忍离去", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton("再看看", null);
        builder.show();
    }


    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }


}
