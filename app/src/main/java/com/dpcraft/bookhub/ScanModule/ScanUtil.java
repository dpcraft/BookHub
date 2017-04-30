package com.dpcraft.bookhub.ScanModule;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dpcraft.bookhub.Algorithm.StringProcess;
import com.dpcraft.bookhub.DataClass.UploadBookInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jim on 13-7-10.
 */
public class ScanUtil {
    public static String Download(String urlstr)
    {
        String result="";
        try{
            URL url=new URL(urlstr);
            URLConnection connection =url.openConnection();
            String line;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = "error";
        }
        return  result;
    }

    public UploadBookInfo parseUploadBookInfo(String str)
    {
       UploadBookInfo info=new UploadBookInfo();
        try{
            JSONObject mess=new JSONObject(str);
            info.setTitle(mess.getString("title"));
//            info.setBitmap(DownloadBitmap(mess.getString("image")));
            JSONObject imageJSON =  mess.getJSONObject("images");
            info.setBitmap(DownloadBitmap(imageJSON.getString("large")));
            info.setAuthor(parseJSONArraytoString(mess.getJSONArray("author")));
            info.setPublishHouse(mess.getString("publisher"));
            info.setPublishDate(StringProcess.formatDate(mess.getString("pubdate")));
            info.setISBN(mess.getString("isbn13"));
            info.setOriginPrice(StringProcess.extractNumFromString(mess.getString("price")));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public Bitmap DownloadBitmap(String bmurl)
    {
        Bitmap bm=null;
        InputStream is =null;
        BufferedInputStream bis=null;
        try{
            URL  url=new URL(bmurl);
            URLConnection connection=url.openConnection();
            bis=new BufferedInputStream(connection.getInputStream());
            bm= BitmapFactory.decodeStream(bis);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(bis!=null)
                    bis.close();
                if (is!=null)
                    is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bm;
    }
    public String parseJSONArraytoString (JSONArray arr)
    {
        StringBuffer str =new StringBuffer();
        for(int i=0;i<arr.length();i++)
        {
            try{
                str=str.append(arr.getString(i)).append(" ");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return str.toString();
    }
}

