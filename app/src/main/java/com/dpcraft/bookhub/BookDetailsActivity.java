package com.dpcraft.bookhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dpcraft.bookhub.Adapter.BookDetailsAdapter;
import com.dpcraft.bookhub.DataClass.BookDetails;


/**
 * Created by DPC on 2017/3/8.
 */
public class BookDetailsActivity extends Activity {
    private FloatingActionButton floatingActionButton;
    private Toolbar bookNameToolbar;
    private boolean isLiked;
    private BookDetails mbookDetails;
    private BookDetailsAdapter mbookDetailsAdapter;
    private RecyclerView bookDetailsRecyclerView;
    private BookDetails mbookDetais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        initWidget();
        initBookDetails();
        bookNameToolbar.setTitle(mbookDetails.getName());
        //绑定适配器
        bookDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mbookDetailsAdapter = new BookDetailsAdapter(mbookDetails,this);
        bookDetailsRecyclerView.setAdapter(mbookDetailsAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLiked){
                    floatingActionButton.setImageResource(R.drawable.ic_heart_red);
                }else{
                    floatingActionButton.setImageResource(R.drawable.ic_heart_white);
                }
                isLiked = !isLiked;
            }
        });

    }


    private void initWidget(){
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        bookDetailsRecyclerView = (RecyclerView)findViewById(R.id.rv_bookdetais);
        bookNameToolbar = (Toolbar)findViewById(R.id.tb_bookname);

    }
    private BookDetails initBookDetails(){
        mbookDetails = new BookDetails();
        mbookDetails.setPrice("9.99");
        mbookDetails.setType("文学");
        mbookDetails.setPublishHouse("湖南文艺出版社");
        mbookDetails.setAuthor("张嘉佳");
        mbookDetails.setName("从你的全世界路过");
      //  mbookDetails.setPublishDate("2013-11-1");
        mbookDetails.setOriginPrice("36.00");
        mbookDetails.setISBN("9787540458027");
        mbookDetails.setSell(true);
        return mbookDetails;
    }


    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,BookDetailsActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }
}
