package com.dpcraft.bookhub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dpcraft.bookhub.DataClass.GetRequestDetailsResponse;
import com.dpcraft.bookhub.DataClass.ResponseFromServer;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DPC on 2017/5/2.
 */
public class RequestDetailsActivity extends BaseActivity{
    private String requestId;
    private CustomToolbar mCustomToolbar;
    private CircleImageView userIcon;
    private TextView userName ,requestTitle, date;
    private WebView requestBody;

    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 201:
                    Log.i("json",msg.obj.toString());
                    GetRequestDetailsResponse getRequestDetailsResponse = JSONUtil.parseJsonWithGson(msg.obj.toString(), GetRequestDetailsResponse.class);
                    mCustomToolbar.setTitle(getRequestDetailsResponse.getData().getRequestBookInfo().getRequestTitle());
                    userName.setText(getRequestDetailsResponse.getData().getUserInfo().getNickName());
                    requestTitle.setText(getRequestDetailsResponse.getData().getRequestBookInfo().getRequestTitle());
                    date.setText(getRequestDetailsResponse.getData().getRequestBookInfo().getDate());
                    requestBody.loadDataWithBaseURL(null , getRequestDetailsResponse.getData().getRequestBookInfo().getRequestBody(),"text/html","UTF-8",null);
                    break;
                case 400:
                    Dialog.showDialog("dialog", JSONUtil.parseJsonWithGson(msg.obj.toString(),ResponseFromServer.class).getMessage(),RequestDetailsActivity.this);
                    break;
                default:
                    break;


            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        initWidget();
        Intent intent = getIntent();
        requestId = intent.getStringExtra("requestId");
        initRequestDetails();
        //imageUrl = Server.getServerAddress() + "book/image?bookid=" + bookId;
    }

    private void initWidget(){
        mCustomToolbar = (CustomToolbar)findViewById(R.id.ctb_request);
        userIcon = (CircleImageView)findViewById(R.id.civ_user_icon);
        userName = (TextView)findViewById(R.id.tv_user_name);
        date = (TextView)findViewById(R.id.tv_date);
        requestTitle = (TextView)findViewById(R.id.tv_request_title);
        requestBody = (WebView)findViewById(R.id.wv_request_body);

    }
    private void initRequestDetails(){

        NetUtils.getRequestDetails(Server.getServerAddress() + "bw/get?token=&bwid=" + requestId, handler);
        String imageUrl = Server.getServerAddress() + "bw/image?bwid=" + requestId + "&reduce=";
        Glide.with(this).load(imageUrl).error(R.drawable.default_user_icon).into(userIcon);
    }

    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,RequestDetailsActivity.class);
        intent.putExtra("requestId",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }
}
