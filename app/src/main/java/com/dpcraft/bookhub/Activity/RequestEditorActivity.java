package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.RichTextEditor.RichEditText;
import com.dpcraft.bookhub.RichTextEditor.RichTextActions;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;

/**
 * Created by DPC on 2017/4/25.
 */
public class RequestEditorActivity extends BaseActivity{

    private CustomToolbar mCustomToolbar;
    private TextView mTextView;
    private WebView mWebView;
    private RichEditText mRichEditText;
    private RichTextActions richTextActions;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_editor);

        initWiget();
        mCustomToolbar.setTitle("求书");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRichEditText.getHtml();
                //mTextView.setText(Html.fromHtml(mRichEditText.getHtml()));
                mWebView.loadDataWithBaseURL(null , mRichEditText.getHtml(),"text/html","UTF-8",null);

                Dialog.showProgressDialog(RequestEditorActivity.this , "解析中……");
                Log.i("html=========", mRichEditText.getHtml());
            }
        });


        mRichEditText.setRichTextActionsView(richTextActions);
        mRichEditText.setPreviewText(getString(R.string.color_picker_text_preview));
        mRichEditText.setHint(getString(R.string.hint));

        if (savedInstanceState != null) {
            mRichEditText.restoreState(savedInstanceState);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplication().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
    }
    private void initWiget(){
       // mTextView = (TextView)findViewById(R.id.tv_show);
        mCustomToolbar = (CustomToolbar)findViewById(R.id.ctb_request);
        mWebView = (WebView)findViewById(R.id.wv_show);
        mRichEditText = (RichEditText) findViewById(R.id.rich_edit_text);
        richTextActions = (RichTextActions) findViewById(R.id.rich_text_actions);
        mButton = (Button) findViewById(R.id.btn_complete);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (mRichEditText != null) {
            mRichEditText.saveState(outState);
        }
    }




    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, RequestEditorActivity.class);
        intent.putExtra("para1", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
