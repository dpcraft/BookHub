package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dpcraft.bookhub.Algorithm.UnitConversion;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.ResponseFromServer;
import com.dpcraft.bookhub.DataClass.UploadBookInfo;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.PhotoUtil.ImagePicker;
import com.dpcraft.bookhub.PhotoUtil.cropper.CropImage;
import com.dpcraft.bookhub.PhotoUtil.cropper.CropImageView;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DPC on 2017/4/4.
 */
public class UploadActivity extends Activity {

    public static final int SUCCESS = 201;
    public static final int REQUEST_ERROR = 400;
    private Intent intent;
    private CustomToolbar mCustomToolbar;
    private ImageView bookCover;
    private TextInputLayout bookNameWrapper , authorWrapper , publishHouseWrapper , originPriceWrapper ,  publishDateWrapper ,
                            ISBNWrapper , priceWrapper , depositWrapper , introductionWrapper ;
    private Spinner bookTypeSpinner , dealTypeSpinner;
    private Button uploadButton;
    private  UploadBookInfo uploadBookInfo;
    private MyApplication myApplication;
    private ImagePicker imagePicker = new ImagePicker();
    private AlertDialog progressDialog;

    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            progressDialog.dismiss();
            switch ( msg.what){
                case SUCCESS :
                    Toast.makeText(UploadActivity.this , "上传成功" ,Toast.LENGTH_SHORT).show();
                   //ialog.showSignupSuccessDialog(UploadActivity.this , "上传成功");
                    UploadActivity.this.finish();
                    break;
                case REQUEST_ERROR:
                    Dialog.showDialog("上传失败", JSONUtil.parseJsonWithGson((String)msg.obj,ResponseFromServer.class).getMessage(),UploadActivity.this);

                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(final Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_upload);
        initWidget();
        mCustomToolbar.setTitle("发布书籍");
        initBookInfo();
        setSpinnerListener();
        bookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraOrGallery();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myApplication = (MyApplication)getApplication();
                bookCover.buildDrawingCache(true);
                bookCover.buildDrawingCache();
                Bitmap bitmap = bookCover.getDrawingCache();
                saveBitmapFile(bitmap);
                bookCover.setDrawingCacheEnabled(false);
                collectBookInfo();
                showProgressDialog();
                try {
                    NetUtils.uploadBook(uploadBookInfo, myApplication.getToken(), handler);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }
    private void saveBitmapFile(Bitmap bitmap){
        File temp = new File("/sdcard/BookHub/");
        if(!temp.exists()){
            temp.mkdir();
        }
        File file = new File("/sdcard/BookHub/bookCover.jpg");
        try{
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bufferedOutputStream );
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private void initWidget(){
        mCustomToolbar = (CustomToolbar)findViewById(R.id.ctb_upload);
        bookCover = (ImageView)findViewById(R.id.iv_book_cover);
        bookNameWrapper = (TextInputLayout)findViewById(R.id.book_name_wrapper);
        authorWrapper = (TextInputLayout)findViewById(R.id.author_wrapper);
        publishHouseWrapper = (TextInputLayout)findViewById(R.id.publish_house_wrapper);
        originPriceWrapper = (TextInputLayout)findViewById(R.id.origin_price_wrapper);
        publishDateWrapper = (TextInputLayout)findViewById(R.id.publish_date_wrapper);
        ISBNWrapper = (TextInputLayout)findViewById(R.id.ISBN_wrapper);
        priceWrapper = (TextInputLayout)findViewById(R.id.price_wrapper);
        depositWrapper = (TextInputLayout)findViewById(R.id.deposit_wrapper);
        introductionWrapper = (TextInputLayout)findViewById(R.id.introduction_wrapper);
        bookTypeSpinner = (Spinner)findViewById(R.id.sp_book_type);
        dealTypeSpinner = (Spinner)findViewById(R.id.sp_deal_type);
        uploadButton = (Button)findViewById(R.id.btn_upload);

    }
    private void initBookInfo(){
        intent=getIntent();
        uploadBookInfo = (UploadBookInfo)intent.getParcelableExtra(UploadBookInfo.class.getName());
        if(uploadBookInfo != null) {
            bookCover.setImageBitmap(uploadBookInfo.getBitmap());
            Log.i("bookcoverWidth===",uploadBookInfo.getBitmap().getWidth() + "");
            Log.i("bookcoverHeight===",uploadBookInfo.getBitmap().getHeight() + "");

            bookNameWrapper.getEditText().setText(uploadBookInfo.getTitle());
            authorWrapper.getEditText().setText(uploadBookInfo.getAuthor());
            publishHouseWrapper.getEditText().setText(uploadBookInfo.getPublishHouse());
            publishDateWrapper.getEditText().setText(uploadBookInfo.getPublishDate());
            ISBNWrapper.getEditText().setText(uploadBookInfo.getISBN());
            originPriceWrapper.getEditText().setText(uploadBookInfo.getOriginPrice());
        }
        else uploadBookInfo = new UploadBookInfo();
    }
    private void collectBookInfo(){
        uploadBookInfo.setTitle(bookNameWrapper.getEditText().getText().toString().trim());
        uploadBookInfo.setAuthor(authorWrapper.getEditText().getText().toString().trim());
        uploadBookInfo.setPublishHouse(publishHouseWrapper.getEditText().getText().toString().trim());
        uploadBookInfo.setOriginPrice(originPriceWrapper.getEditText().getText().toString().trim());
        uploadBookInfo.setPublishDate(publishDateWrapper.getEditText().getText().toString().trim());
        uploadBookInfo.setPrice(priceWrapper.getEditText().getText().toString().trim());
        uploadBookInfo.setmDeposit(depositWrapper.getEditText().getText().toString().trim());
        //version 没有设置
        uploadBookInfo.setISBN(ISBNWrapper.getEditText().getText().toString().trim());
       // uploadBookInfo.setBookType();
        uploadBookInfo.setmIntroduction(introductionWrapper.getEditText().getText().toString().trim());
    }

    private void  setSpinnerListener(){
        bookTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                int bookType;
                if(position == 0 || position == 9){
                    bookType = 0;
                }else {
                    bookType = position ;
                }
                uploadBookInfo.setBookType(bookType + "");
                Log.i("bookTypeSpinner=======",position + "");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        dealTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                if(position == 1 ) {
                    uploadBookInfo.setmIsSold("");
                    depositWrapper.setVisibility(View.VISIBLE);
                }
                else {
                    depositWrapper.setVisibility(View.GONE);
                    uploadBookInfo.setmIsSold("on");
                }
                Log.i("DealTypeSpinner=======",position + "");

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed(){
        Log.i("onBackPressed:", "onBackPressed: ");
        dismissProgressDialog();

    }

    private void startCameraOrGallery() {
        new AlertDialog.Builder(this).setTitle("上传书籍封面")
                .setItems(new String[] { "从相册中选取图片", "拍照" }, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        // 回调
                        ImagePicker.Callback callback = new ImagePicker.Callback() {
                            @Override public void onPickImage(Uri imageUri) {
                            }

                            @Override public void onCropImage(Uri imageUri) {
                                bookCover.setImageURI(imageUri);
//                                File temp = new File("/sdcard/BookHub/");
//                                if(!temp.exists()){
//                                    temp.mkdir();
//                                }
//                                File file = new File("/sdcard/BookHub/bookCover.jpg");
//                                try{
//                                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
//                                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,bufferedOutputStream);
//                                    bufferedOutputStream.flush();
//                                    bufferedOutputStream.close();
//                                }catch (IOException e){
//                                    e.printStackTrace();
//                                }

                            }
                            // 自定义裁剪配置
                            @Override public void cropConfig(CropImage.ActivityBuilder builder) {
                                builder
                                        // 是否启动多点触摸
                                        .setMultiTouchEnabled(false)
                                        // 设置网格显示模式
                                        .setGuidelines(CropImageView.Guidelines.OFF)
                                        // 圆形/矩形
                                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                                        // 调整裁剪后的图片最终大小
                                        .setRequestedSize(UnitConversion.dip2px( UploadActivity.this, 320) ,
                                                UnitConversion.dip2px( UploadActivity.this, 430))
                                        // 宽高比
                                        .setAspectRatio(32 , 43);
                            }
                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(UploadActivity.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(UploadActivity.this, callback);
                        }
                    }
                })
                .show();
    }

    public void showProgressDialog() {

        progressDialog = new AlertDialog.Builder(this).create();
        progressDialog.setCancelable(false);
        progressDialog.show();
        Window window = progressDialog.getWindow();
        window.setContentView(R.layout.dialog_progress);
        TextView textView = (TextView) window.findViewById(R.id.tv_success);
        textView.setText("上传中……");

    }

    public void dismissProgressDialog() {
        if (isFinishing()) {
            Log.i("isFinishing=========", "dismissProgressDialog: ");
            return;
        }
        progressDialog.dismiss();
        if (null != progressDialog /*&& progressDialog.isShowing()*/) {
            progressDialog.dismiss();
        }
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, UploadActivity.class);
        intent.putExtra("para", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
