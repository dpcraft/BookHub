package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dpcraft.bookhub.Adapter.MyUploadBookDetailsAdapter;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.BookDetails;
import com.dpcraft.bookhub.DataClass.BookDetailsIncludeUser;
import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.DataClass.GetBookDetailsIncludeUserResponse;
import com.dpcraft.bookhub.DataClass.ResponseFromServer;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.Dialog;


/**
 * Created by DPC on 2017/3/8.
 */
public class MyUploadBookDetailsActivity extends BaseActivity {
    private MyApplication myApplication;
    private FloatingActionButton floatingActionButton;
    private Toolbar bookNameToolbar;
    private boolean isSold;
    private BookDetailsIncludeUser bookDetailsIncludeUser;
    private MyUploadBookDetailsAdapter myUploadBookDetailsAdapter;
    private BookDetails bookDetails;
    private RecyclerView bookDetailsRecyclerView;
    private ImageView bookCover;
    private String bookId , imageUrl;
    private BookGetRequestInformation bookGetRequestInformation;


    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 201:
                    Log.i("json",msg.obj.toString());
                    GetBookDetailsIncludeUserResponse getBookDetailsIncludeUserResponse = JSONUtil.parseJsonWithGson( msg.obj.toString(), GetBookDetailsIncludeUserResponse.class);
                    bookDetailsIncludeUser = getBookDetailsIncludeUserResponse.getData();
                    bookDetails = bookDetailsIncludeUser.getBook();
                    bookNameToolbar.setTitle(bookDetails.getName());
                    myUploadBookDetailsAdapter = new MyUploadBookDetailsAdapter(bookDetailsIncludeUser,MyUploadBookDetailsActivity.this);
                    bookDetailsRecyclerView.setAdapter( myUploadBookDetailsAdapter);
                    if(bookDetails.getSell()){
                        isSold = true;
                     }
                    else{
                    isSold = false;
                     }
                    //if(myApplication.isLogin() && mbookDetails.getUserId() == myApplication.getLoginResponseUserInfo().getNickName())
                    break;
                case 002:
                    Dialog.showDialog("dialog", JSONUtil.parseJsonWithGson(msg.obj.toString(),ResponseFromServer.class).getMessage(),MyUploadBookDetailsActivity.this);
                    break;
                case 301:
                    Toast.makeText(MyUploadBookDetailsActivity.this , JSONUtil.parseJsonWithGson(msg.obj.toString(),ResponseFromServer.class).getMessage() , Toast.LENGTH_SHORT).show();
                    finish();
                    //Dialog.showDialog("dialog", JSONUtil.parseJsonWithGson(msg.obj.toString(),ResponseFromServer.class).getMessage(),MyUploadBookDetailsActivity.this);
                    break;
                default:
                    break;


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

        Log.i("detailbookId======",bookId );
        initBookDetails();
        imageUrl = Server.getServerAddress() + "book/image?bookid=" + bookId;
        Glide.with(this).load(imageUrl).placeholder(R.drawable.image_belonged).error(R.drawable.load_error).into(bookCover);
        bookNameToolbar.setNavigationIcon(R.drawable.ic_back_white);
        bookNameToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bookNameToolbar.inflateMenu(R.menu.menu_book_details);
        bookNameToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if(menuItemId == R.id.action_delete){
                   showDeleteDialog();
                }else if(menuItemId == R.id.action_edit){
                    //跳转到编辑页面
                }
                return false;
            }
        });
        //绑定适配器
        bookDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String intentionUrl = Server.getServerAddress() + "book/deal";
                if(!isSold){
                    floatingActionButton.setImageResource(R.drawable.ic_intention_true);
                    NetUtils.modifyIntention( intentionUrl , myApplication.getToken() , bookDetails.getId() , false , isSold, handler);
                }else{
                    floatingActionButton.setImageResource(R.drawable.ic_intention_false);
                    NetUtils.modifyIntention( intentionUrl , myApplication.getToken() , bookDetails.getId() , false , isSold, handler);
                }
                isSold = !isSold;
            }
        });

    }


    private void initWidget(){
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        bookDetailsRecyclerView = (RecyclerView)findViewById(R.id.rv_bookdetais);
        bookNameToolbar = (Toolbar)findViewById(R.id.tb_bookname);
        bookCover = (ImageView)findViewById(R.id.iv_book_cover_details);

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
    public  void showDeleteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注意");
        builder.setMessage("确定删除本书？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NetUtils.deleteBook(myApplication.getToken() , bookId , handler);
                finish();
            }
        });
        builder.show();
    }


    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,MyUploadBookDetailsActivity.class);
        intent.putExtra("bookId",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }
}
