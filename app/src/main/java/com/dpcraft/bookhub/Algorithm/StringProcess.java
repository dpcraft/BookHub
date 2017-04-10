package com.dpcraft.bookhub.Algorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DPC on 2017/4/10.
 */
public class StringProcess {

    /*
     *Extract the Number including int and float from a string.
     */
    public static String extractNumFromString(String string){
        Pattern pattern = Pattern.compile("(\\d+[.]\\d+)|(\\d+)");
        Matcher matcher = pattern.matcher(string);
        String numInString;
        if(matcher.find()){
            numInString = matcher.group();
        }else {
            numInString = "";
        }

        return numInString;
    }

    /*
     *Format date into yyyy-mm-dd
     */
     public static String formatDate(String string){
         Pattern p = Pattern.compile( "\\d+" );
         String[] strings = {"2013-01-01","2013-9","2013-9-1"};
         Matcher m;
         String[]date = {"","",""};
         String year , month , day , formatedDate;
             m= p.matcher(string);
             int i = 0;
             while (m.find()) {
                 String str;
                 switch(m.group().length()){
                     case 0:
                         str = "01";
                         break;
                     case 1:
                         str = "0" + m.group();
                         break;
                     default :
                         str = m.group();
                         break;
                 }
                 date[i] = str;
                 i++;
             }
             year = date[0];
             month = date[1];
             if(date[2] == ""){
                 day = "01";
             }
             else{
                 day = date[2];
             }
             formatedDate = year + "-" + month + "-" + day;

        return  formatedDate;
     }
}
