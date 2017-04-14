package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dpcraft.bookhub.R;

/**
 * Created by DPC on 2017/4/15.
 */
public class AboutActivity extends Activity{
    private TextView title , content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        title = (TextView)findViewById(R.id.tv_about_title);
        content = (TextView)findViewById(R.id.tv_about_content);
        content.setText("亲，还记得那些年陪伴过你的专业课本、小说和漫画吗？你是否还知晓它们身处何方呢？" +
                "相信每个即将离开母校的人，都会面临这样一个问题，如何处理一大堆陪伴自己大学时光的书籍？" +
                "相信没有一个人愿意把它们随意扔掉，当废纸卖掉。如果能将其赠给需要这些书的人，为这些书寻找一" +
                "个新的家，我想你一定会愿意去做的。书集，一款致力于在校大学生书籍出租及二手书籍购买的APP," +
                "你值得拥有！ " + "\n" +
                "我们不卖书，我们是书的搬运工，也是一个书籍搬运的渠道！\n" );

    }
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, AboutActivity.class);
        intent.putExtra("para1", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
