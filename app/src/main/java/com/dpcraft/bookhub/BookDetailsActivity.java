package com.dpcraft.bookhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dpcraft.bookhub.Adapter.BookDetailsAdapter;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.BookDetails;
import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.DataClass.GetBookDetailsResponse;
import com.dpcraft.bookhub.DataClass.GetBookResponse;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.NetModule.Server;


/**
 * Created by DPC on 2017/3/8.
 */
public class BookDetailsActivity extends Activity {
    private MyApplication myApplication;
    private FloatingActionButton floatingActionButton;
    private Toolbar bookNameToolbar;
    private boolean isLiked;
    private BookDetails mbookDetails;
    private BookDetailsAdapter mbookDetailsAdapter;
    private RecyclerView bookDetailsRecyclerView;
    private ImageView bookCover;
    private String bookId , imageUrl;
    private BookGetRequestInformation bookGetRequestInformation;

    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == 201){

                Log.i("json",msg.obj.toString());
                GetBookDetailsResponse getBookDetailsResponse = JSONUtil.parseJsonWithGson( msg.obj.toString(),GetBookDetailsResponse.class);
                mbookDetails = getBookDetailsResponse.getData();
                bookNameToolbar.setTitle(mbookDetails.getName());
                mbookDetailsAdapter = new BookDetailsAdapter(mbookDetails,BookDetailsActivity.this);
                bookDetailsRecyclerView.setAdapter(mbookDetailsAdapter);

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        initWidget();


        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        imageUrl = Server.getServerAddress() + "book/image?bookid=" + bookId;
        Glide.with(this).load(imageUrl).placeholder(R.drawable.image_belonged).error(R.drawable.load_error).into(bookCover);
        Log.i("detailImageUrl======",imageUrl);
        Log.i("detailbookId======",bookId );
        initBookDetails();



        bookNameToolbar.setNavigationIcon(R.drawable.ic_back_white);
        bookNameToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //绑定适配器
        bookDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


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
        bookCover = (ImageView)findViewById(R.id.iv_book_cover_details);

    }
    private BookDetails initBookDetails2(){
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
    public void initBookDetails() {
        myApplication = (MyApplication)getApplication();
        bookGetRequestInformation = new BookGetRequestInformation();
        bookGetRequestInformation.setBookId(bookId);
        bookGetRequestInformation.setToken(myApplication.getToken());
        Log.i("generateURL()", bookGetRequestInformation.generateBookDetailURL());
        try {
            NetUtils.getBookDetails(bookGetRequestInformation, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,BookDetailsActivity.class);
        intent.putExtra("bookId",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }
}
