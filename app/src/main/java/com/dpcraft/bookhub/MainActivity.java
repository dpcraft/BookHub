package com.dpcraft.bookhub;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
//import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MainActivity extends FragmentActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
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
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
       // floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_add);

        //FloatingActionMenu的使用
       final ImageView fabIcon = new ImageView(this); // Create an icon
        fabIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIcon)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.fab_shape))
                .build();

        //子按钮
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.mbt_shape)).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.fab_shape)).build();

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.fab_shape)).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                // ...
                .attachTo(actionButton)
                .build();

        actionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // 增加按钮中的+号图标顺时针旋转45度
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIcon.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // 增加按钮中的+号图标逆时针旋转45度
                // Rotate the icon of rightLowerButton 45 degrees
                // counter-clockwise
                fabIcon.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
                animation.start();
            }
        });
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
