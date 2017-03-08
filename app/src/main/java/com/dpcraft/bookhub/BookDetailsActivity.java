package com.dpcraft.bookhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;


/**
 * Created by DPC on 2017/3/8.
 */
public class BookDetailsActivity extends Activity {
    private FloatingActionButton floatingActionButton;
    private boolean isliked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        initWidget();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isliked){
                    floatingActionButton.setImageResource(R.drawable.ic_heart_red);
                }else{
                    floatingActionButton.setImageResource(R.drawable.ic_heart_white);
                }
                isliked = !isliked;
            }
        });

    }


    public void initWidget(){
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

    }
    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,BookDetailsActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }
}
