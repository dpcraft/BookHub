package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.UnitConversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by DPC on 2017/3/27.
 */
public class PictureActivity extends Activity{
    public static final int TAKE_PHOTO = 1;
    public static final int PHOTO_CLIP = 2;
    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_picture);
        Button takePhotoButton = (Button)findViewById(R.id.btn_take_photo);
        picture = (ImageView)findViewById(R.id.iv_picture);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(PictureActivity.this,"com.dpcraft.bookhub.fileprovider",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
       });
    }
    @Override
    protected void onActivityResult (int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    photoClip(imageUri);

                }
                break;
            case PHOTO_CLIP:
                //try{
                    //将照片显示出来
                    //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    Bitmap bitmap = data.getExtras().getParcelable("data");
                    picture.setImageBitmap(bitmap);
               // }catch (FileNotFoundException e){
               // }
                break;
            default:
                break;
        }
    }

    //图片裁剪

    private void photoClip(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop",true);
        //aspectX aspectY 宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //outputX outputY 是裁剪图片的宽高
        intent.putExtra("outputX", UnitConversion.dip2px(PictureActivity.this,150));
        intent.putExtra("outputY", UnitConversion.dip2px(PictureActivity.this,150));
        intent.putExtra("return-data",true);
        startActivityForResult(intent,PHOTO_CLIP);
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra("para", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
