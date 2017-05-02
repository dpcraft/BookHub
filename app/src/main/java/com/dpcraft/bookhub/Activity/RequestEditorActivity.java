package com.dpcraft.bookhub.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.RequestBookInfo;
import com.dpcraft.bookhub.DataClass.ResponseFromServer;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.RichTextEditor.RichEditText;
import com.dpcraft.bookhub.RichTextEditor.RichTextActions;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;

/**
 * Created by DPC on 2017/4/25.
 */
public class RequestEditorActivity extends BaseActivity{

    public static final int SUCCESS = 201;
    public static final int REQUEST_ERROR = 400;
    private MyApplication myApplication;
    private CustomToolbar mCustomToolbar;
    private EditText titleEditTextView;
    private WebView mWebView;
    private RichEditText mRichEditText;
    private RichTextActions richTextActions;
    private Button mButton;
    private AlertDialog progressDialog;
    private DialogInterface.OnKeyListener mOnKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {

            if(keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                dismissProgressDialog();
            }

            return false;
        }
    };

    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            dismissProgressDialog();
            switch ( msg.what){
                case SUCCESS :
                    Toast.makeText(RequestEditorActivity.this , "发布成功" ,Toast.LENGTH_SHORT).show();
                    //ialog.showSignupSuccessDialog(UploadActivity.this , "上传成功");
                    RequestEditorActivity.this.finish();
                    break;
                case REQUEST_ERROR:
                    dismissProgressDialog();
                    Dialog.showDialog("发布失败", JSONUtil.parseJsonWithGson((String)msg.obj,ResponseFromServer.class).getMessage(),
                            RequestEditorActivity.this);

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_editor);

        initWidget();
        mCustomToolbar.setTitle("求书");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isBodyValid() && isTitleValid()){
                    RequestBookInfo requestBookInfo = new RequestBookInfo();
                    String title = titleEditTextView.getText().toString().trim();
                    String body = mRichEditText.getHtml();
                    requestBookInfo.setRequestTitle(title);
                    requestBookInfo.setRequestBody(body);
                    myApplication = (MyApplication)getApplication();
                    showProgressDialog("发布中……");
                    try {
                        NetUtils.uploadRequestBook(requestBookInfo,myApplication.getToken(),handler);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


               // mWebView.loadDataWithBaseURL(null , mRichEditText.getHtml(),"text/html","UTF-8",null);


                //Log.i("html=========", mRichEditText.getHtml());
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
    private void initWidget(){
        titleEditTextView = (EditText)findViewById(R.id.et_title);
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
    public boolean isTitleValid(){
        if(titleEditTextView.getText().toString().trim().equals("")||
                titleEditTextView.getText().toString().trim().isEmpty()){
            Dialog.showDialog("格式错误", "标题不能为空", RequestEditorActivity.this);
            return false;
        }else if(titleEditTextView.getText().toString().trim().length() > 100){
            Dialog.showDialog("格式错误", "标题长度不能超过100字", RequestEditorActivity.this);
            return false;
        }
        return true;
    }

    public boolean isBodyValid(){
        if(mRichEditText.getHtml().trim().equals("")||
                mRichEditText.getHtml().trim().isEmpty()){
            Dialog.showDialog("格式错误", "标题不能为空", RequestEditorActivity.this);
            return false;
        }else if(mRichEditText.getHtml().trim().length() > 2000){
            Dialog.showDialog("格式错误", "标题长度不能超过2000字", RequestEditorActivity.this);
            return false;
        }
        return true;
    }


    public void showProgressDialog(String title) {

        progressDialog = new AlertDialog.Builder(this).create();
        progressDialog.setCancelable(false);
        progressDialog.show();
        Window window = progressDialog.getWindow();
        window.setContentView(R.layout.dialog_progress);
        TextView textView = (TextView) window.findViewById(R.id.tv_success);
        textView.setText(title);
        progressDialog.setOnKeyListener(mOnKeyListener);

    }
    public void dismissProgressDialog() {
        if (isFinishing()) {
            return;
        }
        progressDialog.dismiss();
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, RequestEditorActivity.class);
        intent.putExtra("para1", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
