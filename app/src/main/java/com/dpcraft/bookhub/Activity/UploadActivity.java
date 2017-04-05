package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dpcraft.bookhub.DataClass.UploadBookInfo;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;

/**
 * Created by DPC on 2017/4/4.
 */
public class UploadActivity extends Activity {
    private Intent intent;
    private CustomToolbar mCustomToolbar;
    private ImageView bookCover;
    private TextInputLayout bookNameWrapper , authorWrapper , publishHouseWrapper , originPriceWrapper ,  publishDateWrapper ,
                            ISBNWrapper , priceWrapper , depositWrapper , introductionWrapper ;
    private Spinner bookTypeSpinner , dealTypeSpinner;
    private Button uploadButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_upload);
        initWidget();
        mCustomToolbar.setTitle("发布书籍");
        initBookInfo();
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
        UploadBookInfo uploadBookInfo = (UploadBookInfo)intent.getParcelableExtra(UploadBookInfo.class.getName());
        if(uploadBookInfo != null) {
            bookCover.setImageBitmap(uploadBookInfo.getBitmap());
            bookNameWrapper.getEditText().setText(uploadBookInfo.getTitle());
            authorWrapper.getEditText().setText(uploadBookInfo.getAuthor());
            publishHouseWrapper.getEditText().setText(uploadBookInfo.getPublisher());
            publishDateWrapper.getEditText().setText(uploadBookInfo.getPublishDate());
            ISBNWrapper.getEditText().setText(uploadBookInfo.getISBN());
            originPriceWrapper.getEditText().setText(uploadBookInfo.getPrice());
        }
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, UploadActivity.class);
        intent.putExtra("para", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
