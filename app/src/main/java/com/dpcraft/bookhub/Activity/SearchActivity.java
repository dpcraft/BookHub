package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.SearchModule.SearchView;

/**
 * Created by DPC on 2017/4/2.
 */
public class SearchActivity extends BaseActivity{
    private SearchView  searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = (SearchView)findViewById(R.id.search_layout);
        searchView.setKeyword(getIntent().getStringExtra("KEYWORD"));
    }
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("KEYWORD", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }

}
